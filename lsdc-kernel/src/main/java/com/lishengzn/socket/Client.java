package com.lishengzn.socket;

import java.io.*;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.alibaba.druid.util.StringUtils;
import com.lishengzn.constant.TopicAddressConstants;
import com.lishengzn.service.WebSocketMsgService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.lishengzn.dto.CommandDto;
import com.lishengzn.dto.VehicleDto;
import com.lishengzn.entity.Coordinate;
import com.lishengzn.entity.Vehicle;
import com.lishengzn.enums.NaviStateEnum;
import com.lishengzn.exception.SimpleException;
import com.lishengzn.task.CyclicTask;
import com.lishengzn.task.RetrievalAGVInfoTask;
import com.lishengzn.util.BeanConvertUtil;
import com.lishengzn.util.CacheManager;
import com.lishengzn.util.SocketUtil;
import com.lishengzn.util.TranslateUtil;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;


public class Client {
	private static final Logger LOG = LoggerFactory.getLogger(Client.class);
	private Socket socket;
	private String ip;
	private int port;
	private volatile Vehicle vehicle;
	private volatile boolean terminate;
	private WebSocketMsgService webSocketMsgService;
	private ExecutorService fixedThreadPool = Executors.newFixedThreadPool(10);  
	private double cellLength;
	private ResourceBundle kernelBaseLinebundle;
	
	@SuppressWarnings("unchecked")
	public Client(String ip, int port, WebSocketMsgService webSocketMsgService) throws IOException {
		this.ip = ip;
		this.port = port;
		try {
			this.socket = new Socket(ip, port);
		} catch (IOException e) {
			LOG.error("",e);
			throw new SimpleException("无法连接到车辆，请确认车辆已开启！");
		}
		this.vehicle=new Vehicle(new Coordinate(0,0));
		vehicle.setNaviState(NaviStateEnum.IDLE.getValue());
		listen2Server();
		if(CacheManager.cache.get(CacheManager.clientPoolKey)==null){
			CacheManager.cache.put(CacheManager.clientPoolKey, new ConcurrentHashMap<String,Client>());
		}
		((Map<String,Client>) CacheManager.cache.get(CacheManager.clientPoolKey)).put(ip,this);

		String configPath =Client.class.getClassLoader().getResource("config").getPath();
		BufferedInputStream inputStream= new BufferedInputStream(new FileInputStream(new File(configPath,"kernel-baseline.properties")));
		kernelBaseLinebundle=new PropertyResourceBundle(inputStream);
		String cellLengthStr =kernelBaseLinebundle.getString("trackCellLength");
		cellLength=Double.valueOf(cellLengthStr);
		// 周期抽取小车信息
		runRegrievalTask(this);
		LOG.info("connect to " + ip + ":" + port + "  successful");
		//
		WebApplicationContext wac =ContextLoader.getCurrentWebApplicationContext();
		this.webSocketMsgService = webSocketMsgService;

	}
	private  void runRegrievalTask(Client client){
		CyclicTask task_high = new RetrievalAGVInfoTask(Integer.valueOf(kernelBaseLinebundle.getString("RetrievalAGVMsgTask_high_tSleep")), client);
		fixedThreadPool.execute(task_high);

	}
	public void listen2Server() {
		fixedThreadPool.execute(this::handleServerMsg);
	}
	

	
	public void close(){
		try {
			terminate=true;
			if(socket!=null){
				socket.close();
			}
			fixedThreadPool.shutdown();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	/**处理服务端发来的信息
	 * 
	 */
	private void handleServerMsg() {
		InputStream is = null;
			try {
				is = socket.getInputStream();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			String result ="";
			try {
				while (!terminate) {
					if(!StringUtils.isEmpty(result=SocketUtil.readLineSimpleProtocol(is))){
						try {
							LOG.info(result.replace("\r\n","==CRLF=="));
							result=result.replaceAll("(\\[.*\\])?","").trim();
							if(result.startsWith("Position") || result.startsWith("AGV Position") ){// 读取当前位置
								String xy=result.split("X -")[1];
								String x=xy.split("Y -")[0].trim();
								String y=xy.split("Y -")[1].trim();
								Coordinate coor = new Coordinate(Math.round(Double.valueOf(x)),Math.round(Double.valueOf(y)));
								vehicle.setPosition(coor);
							}
							else if(result.startsWith("X: Speed:")){
								String speed_x=result.replace("X: Speed:","").trim();
								vehicle.setVelocity_x(Double.valueOf(speed_x));
							}
							else if(result.startsWith("Y: Speed:")){
								String speed_y=result.replace("Y: Speed:","").trim();
								vehicle.setVelocity_y(Double.valueOf(speed_y));
							}
							else if(result.startsWith("AGV Target Position")){// 目标位置
								LOG.info(result);
								String xy=result.split("X -")[1].trim();
								String x=xy.split("Y -")[0].trim();
								String y=xy.split("Y -")[1].trim();
								Coordinate coor = new Coordinate(Math.round(Double.valueOf(x)),Math.round(Double.valueOf(y)));
								vehicle.setTargetPosition(coor);
							}
							else if(result.startsWith("Battery:")){
								String battery=result.replace("Battery:","").replace("Ah","").trim();
								vehicle.setBatteryCapacity(Double.valueOf(battery));
							}
							else if(result.startsWith("Arriver")){
								synchronized (continuousCmdSyncObj){
									LOG.info(result);
									continuousCmdSyncObj.notifyAll();
								}
							}
							else if(result.startsWith("Pack")){
								synchronized (continuousCmdSyncObj){
									LOG.info(result);
									continuousCmdSyncObj.notifyAll();
								}
							}

							VehicleDto vehicleDto =BeanConvertUtil.beanConvert(vehicle, VehicleDto.class);
							vehicleDto.setVehicleIp(this.ip);
							TranslateUtil.translateEntity(vehicleDto);
							CommandDto cmd = new CommandDto(CommandDto.TYPE.setVehicleInfo.name(), vehicleDto);
							webSocketMsgService.sendToAll(TopicAddressConstants.setVehicleInfo,JSONObject.toJSONString(cmd));
						} catch (SimpleException e) {
							CommandDto cmd = new CommandDto(CommandDto.TYPE.error.name(), e.getMessage());
							webSocketMsgService.sendToAll(TopicAddressConstants.error,JSONObject.toJSONString(cmd));
						}catch (Exception e) {
							LOG.error("解析小车答应信息异常!   "+result,e);
						}
					}
				}
				LOG.info("handleServerMsg ===end");
			} catch (IOException e) {
				LOG.error("指令接收异常!",e);
				close();
			}
	}
	

	public void sendSimProToServer(String command) {
		OutputStream os = null;
		try {
			if(",0800,0601,0600,".indexOf(","+command.toUpperCase()+",")<0){
				LOG.info("向小车发送指令：{}",JSONObject.toJSON(command));
			}
			os = socket.getOutputStream();
			SocketUtil.sendSimpleProtocol(socket.getOutputStream(),command);

		} catch (Exception e) {
			LOG.error("指令发送异常",e);
			close();
		}
	}
	private Object continuousCmdSyncObj = new Object();
	CyclicTask simpProCmdTask=null;
	private volatile boolean executing;
	public void sendContinuousSimpProCmd(List<String> commands,boolean cyclicExecute) {
		if(simpProCmdTask!=null || executing){
			throw new SimpleException("车辆正在执行任务中！");
		}
		Boolean b =cyclicExecute;
		simpProCmdTask = new CyclicTask(50) {
			@Override
			protected void runActualTask() {
				synchronized (continuousCmdSyncObj){
					executing=true;
					for(int i=0;i<commands.size();i++){
						String cmd = commands.get(i);
						if(StringUtils.isEmpty(cmd)){
							continue;
						}

						sendSimProToServer(cmd);
						try {
							continuousCmdSyncObj.wait();
						} catch (InterruptedException e) {
							LOG.error("",e);
							terminate();
							throw new SimpleException("导航任务发送异常！");
						}
					}
					executing=false;
				}
				if(!b){
					cancleCyclicTask();
				}
			}

		};
		fixedThreadPool.execute(simpProCmdTask);
	}

	public void cancleCyclicTask(){
		if(simpProCmdTask!=null){
			simpProCmdTask.terminate();
			simpProCmdTask=null;
		}
	}

	public String getIp() {
		return ip;
	}

	public int getPort() {
		return port;
	}
	public boolean isTerminate() {
		return terminate;
	}

}

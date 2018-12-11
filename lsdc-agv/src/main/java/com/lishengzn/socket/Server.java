package com.lishengzn.socket;

import com.alibaba.fastjson.JSONObject;
import com.lishengzn.entity.Coordinate;
import com.lishengzn.entity.Vehicle;
import com.lishengzn.entity.map.Node;
import com.lishengzn.entity.navi.*;
import com.lishengzn.entity.operation.OperationTask;
import com.lishengzn.entity.operation.SendOperationTask;
import com.lishengzn.entity.read.ReadItem;
import com.lishengzn.entity.read.ReadItem_Request;
import com.lishengzn.entity.read.ReadItem_Response;
import com.lishengzn.entity.read.ReadVariable;
import com.lishengzn.entity.read.content.*;
import com.lishengzn.entity.write.ControlChargeContent;
import com.lishengzn.entity.write.PackageSize;
import com.lishengzn.entity.write.WriteItem;
import com.lishengzn.enums.NaviStateEnum;
import com.lishengzn.exception.SimpleException;
import com.lishengzn.packet.PacketModel;
import com.lishengzn.util.LSConstants;
import com.lishengzn.queque.LimitBLockingQueue;
import com.lishengzn.util.ReadMapUtil;
import com.lishengzn.util.SocketUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;

public class Server {

	private static final Logger LOG = LoggerFactory.getLogger(Server.class);
	private ServerSocket serverSocket = null;
	public  static volatile Vehicle vehicle01 = new Vehicle(new Coordinate(0, 0));

	public static Server getInstance() {
		return ServerInstance.serverInstance;
	}
	private ExecutorService threadPool = Executors.newCachedThreadPool();
	private static class ServerInstance {
		final static Server serverInstance = getServer();

		private static Server getServer() {
			try {
				return new Server(2000);
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		}
	}

	/**
	 * @param port
	 * @throws IOException
	 */
	private Server(int port) throws IOException {
		this.serverSocket = new ServerSocket(port);
		LOG.info("在端口" + port + "开启服务");
	}

	public Socket getSocket() throws IOException {
		return serverSocket.accept();
	}

	public void doSocket() throws Exception {

		while(true){
			Socket socket = getSocket();
			LOG.info("客户端连接：" + socket.getInetAddress().getHostAddress());
			ClientHandler clientHandler = new ClientHandler(socket);
			threadPool.execute(clientHandler);
			initializeVehicle();
		}
	}
	
	private void initializeVehicle(){
		vehicle01.setNaviState(NaviStateEnum.IDLE.getValue());
		vehicle01.setOperationState(NaviStateEnum.IDLE.getValue());
		vehicle01.setBatteryResidues(100);
	}
	
	public static void main(String[] args) throws Exception {
		Server socketServer = Server.getInstance();
		socketServer.doSocket();
	}
}

class ClientHandler implements Runnable {
	private static final Logger LOG = LoggerFactory.getLogger(ClientHandler.class);
	private volatile boolean terminate;
	private Socket socket;
	long lastHeartBeatTime;
	private ExecutorService fixedThreadPool = Executors.newFixedThreadPool(8);
	/**
	 * 小车接下来要经过的点
	 */
	private Queue<Coordinate> vehiclePassingCoorQueue = new LinkedBlockingQueue<Coordinate>(); 
	/**
	 * 保留固定长度（20）的导航轨迹，供查询
	 */
	private LimitBLockingQueue<NaviTrail> naviTrailsQueue4Query = new LimitBLockingQueue<NaviTrail>(20);

	public ClientHandler(Socket socket) {
		super();
		terminate = false;
		this.socket = socket;
	}

	@Override
	public void run() {
		try {
			handleSocket();
		} catch (IOException e) {
			close();
			e.printStackTrace();
		}
	}

	public void handleSocket() throws IOException {
		lastHeartBeatTime = System.currentTimeMillis();// 启动监听之前先把lastHeartBeatTime初始化
		new Thread(() -> listen2Server(socket)).start();
		Scanner scanner = new Scanner(System.in);
		while(!terminate){
			if(scanner.hasNextLine()){
				String str = scanner.nextLine()+"\r\n";
				LOG.info("发送简单指令：{}",str);
				socket.getOutputStream().write(str.getBytes());
			}
		}
		/*
		new Thread(() -> simulationVehicle01Move(socket)).start();
		new Thread(() -> up_state()).start();
		new Thread(() -> simulationOther()).start();*/

	}
	public void sendMsgToClient(PacketModel packetModel) throws IOException {
		OutputStream os = null;
		try {
			os = socket.getOutputStream();
			SocketUtil.sendPacketData(os, packetModel);

		} catch (IOException e) {
			LOG.error("指令发送异常");
			close();
			throw e;
		}

	}
	/**
	 * 主动上报状态
	 */
	private void up_state(int stateType) {
		try {
			UpdateStateContent updateState = new UpdateStateContent();
			updateState.setStateType(stateType);
			if(stateType==LSConstants.STATE_TYPE_NAVI){
				updateState.setState(Server.vehicle01.getNaviState());
			}else{
				updateState.setState(Server.vehicle01.getOperationState());
			}
			PacketModel packetModel = new PacketModel();
			packetModel.setPacketType(LSConstants.PACKET_TYPE_UP_STATE);
			
			packetModel.setData_bytes(updateState.toBytes());
			packetModel.setErrorCode(LSConstants.ERROR_CODE_SUCCESS);
			LOG.info("上报状态:{}",JSONObject.toJSONString(updateState));
			sendMsgToClient(packetModel);
		} catch (IOException e) {
			LOG.error("上报状态，指令发送异常！",e);
		} 
	}

	
	private void listen2Server(Socket socket) {
		InputStream is = null;
		String result ="";
		try {
			is = socket.getInputStream();
			while (!terminate) {
				if(((result=SocketUtil.readSimpleProtocol(is)) != null) && result.trim().length()>0){
					if(",050E,0601,0600,".indexOf(","+result.toUpperCase()+",")<0){
						LOG.info("收到简单指令：{}",result);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			close();
		}
	}
	private void doReadVariable(PacketModel packetModel){
		int packetType = SocketUtil.getResponsePacketType(packetModel.getPacketType());
		ReadVariable<ReadItem_Request> readVariable =new ReadVariable<ReadItem_Request>(ReadItem_Request.class).fromBytes(packetModel.getData_bytes());
		List<ReadItem_Request> itemList =readVariable.getItems();
		List<ReadItem_Response> itemList_response =new ArrayList<ReadItem_Response>();
		for (int i = 0; i < itemList.size(); i++) {
			ReadItem_Request item_request=itemList.get(i);
			ReadItem_Response item_response=null;
			if (item_request.getVarID() == LSConstants.VARID_POSITOIN) {// 位置
				item_response = responsePosition(item_request);
			} else if (item_request.getVarID() == LSConstants.VARID_VELOCITY) {// 速度
				item_response = responseVelocity(item_request);
			} else if (item_request.getVarID() == LSConstants.VARID_CHARGESTATE) {// 充电状态
				item_response = responseChargeState(item_request);
			}else if (item_request.getVarID() == LSConstants.VARID_CHECKPACKAGE) {// 检测是否有包裹
				item_response = responseCheckPackage(item_request);
			}else if (item_request.getVarID() == LSConstants.VARID_FLIP_STATE) {// 翻盖状态
				item_response = responseFlipState(item_request);
			}else if (item_request.getVarID() == LSConstants.VARID_JACKING_DISTANCE) {//顶升距离
				item_response = responseJackingDistance(item_request);
			}else if (item_request.getVarID() == LSConstants.VARID_BELT_ROTATION_STATE) {// 皮带转动状态
				item_response = responseBeltRotationState(item_request);
			}else if (item_request.getVarID() == LSConstants.VARID_OPERATION_STATE) {// 操作状态
				item_response = responseOperationState(item_request);
			}else if (item_request.getVarID() == LSConstants.VARID_BATTERYCAPACITY) {// 电池容量
				item_response = responseBatteryCapacity(item_request);
			}
			
			else {
				item_response  = new ReadItem_Response();
			}
			
			itemList_response.add(item_response);
		}
		
		ReadVariable<ReadItem_Response> readVariable_response =new ReadVariable<ReadItem_Response>(itemList_response.size(),itemList_response,ReadItem_Response.class);
//		PacketModel packetModel = new PacketModel();
		packetModel.setPacketType(packetType);
		packetModel.setData_bytes(readVariable_response.toBytes());
		packetModel.setErrorCode(LSConstants.ERROR_CODE_SUCCESS);
		try {
			sendMsgToClient(packetModel);
		} catch (IOException e) {
			LOG.error("应答读取变量，指令发送异常！",e);
		}
	}
	
	/**按照导航任务行进
	 * @param packetModel
	 */
	// 小车距离下一个点的长度（每个格子的长度）为固定值600mm
	private double cellLength=600;
	// 这里暂定每50毫秒更新一下vehicle01的位置信息
	private int interval=50;
	
	/**
	 * 放弃执行中的任务
	 */
	private volatile boolean abortTask;
	
	/**执行导航任务
	 * @param packetModel
	 */
	private void doCarrayOutNaviTask(PacketModel packetModel){
		SendNaviTask naviTask = new SendNaviTask().fromBytes(packetModel.getData_bytes());
		abortTask=false;
		try {
			LOG.info("接收到导航任务：{}",JSONObject.toJSONString(naviTask));
			// 向调度应答导航任务
			NaviTask naviTask_response = new NaviTask(naviTask.getTaskID());
			packetModel.setPacketType(SocketUtil.getResponsePacketType(packetModel.getPacketType()));
			packetModel.setData_bytes(naviTask_response.toBytes());
			sendMsgToClient(packetModel);
		} catch (IOException e) {
			LOG.error("应答发送导航任务，指令发送异常！",e);
		}
		try {
			if(Server.vehicle01.getNaviState()!=NaviStateEnum.IDLE.getValue()){
				LOG.error("导航任务正在进行中，不可再接受导航任务！");
				Server.vehicle01.setNaviState(NaviStateEnum.ERROR.getValue());
				up_state(LSConstants.STATE_TYPE_NAVI);
			}
			if(!checkContinuity(naviTask.getTrails())){
				LOG.error("导航任务给定的边不连续！");
				throw new SimpleException("导航任务给定的边不连续！");
			}
			// 根据边ID 取得小车要经过的所有点的坐标，即每条边的起点坐标
			List<Coordinate> coors =naviTask.getTrails().stream().map((trail)->{
				Node startNode =ReadMapUtil.getStartNodeById(trail.getPathID());
				return startNode.getPosition();
			}).collect(Collectors.toList());
			vehiclePassingCoorQueue.clear();
			vehiclePassingCoorQueue.addAll(coors);
			naviTrailsQueue4Query.offerAll(naviTask.getTrails());
			// 设置车辆的导航状态,运行中
			Server.vehicle01.setNaviState(NaviStateEnum.RUNNING.getValue());
			moveWithNaviTrails();
			// 如果是完整导航，设置车辆的导航状态
			if(naviTask.isCompleteNavi()){
				Server.vehicle01.setNaviState(NaviStateEnum.IDLE.getValue());
				up_state(LSConstants.STATE_TYPE_NAVI);
			}
		} catch (Exception e) {
			LOG.error("车辆移动过程中发生异常",e);
			Server.vehicle01.setNaviState(NaviStateEnum.ERROR.getValue());
			up_state(LSConstants.STATE_TYPE_NAVI);
		}
		
	}
	
	/**
	 * 按导航任务前进
	 */
	private void moveWithNaviTrails() {
		
		// 暂定小车以500mm/s的 速度前行
		LOG.info("开始行进");
		Server.vehicle01.setVelocity(500.0);
		Coordinate nextCoor=null;
		while(Server.vehicle01.getNaviState()==NaviStateEnum.RUNNING.getValue() ){
			if((nextCoor=vehiclePassingCoorQueue.peek())!=null){
				if(!moveTo(nextCoor)){
					LOG.info("中止行进");
					vehiclePassingCoorQueue.clear();
					return;
				}
				vehiclePassingCoorQueue.poll();
			}else{
				Server.vehicle01.setVelocity_x(0);
				Server.vehicle01.setVelocity_y(0);
			}
		}
		Server.vehicle01.setVelocity(0);
		Server.vehicle01.setVelocity_x(0);
		Server.vehicle01.setVelocity_y(0);
		LOG.info("结束行进");
	}
	
	/**前进到目标点
	 * @param nextCoor
	 * @return
	 */
	private boolean moveTo(Coordinate nextCoor){
		if(!Server.vehicle01.getPosition().equals(nextCoor)){
			double lengthToNextCoor = Math.abs(nextCoor.getPosition_x()-Server.vehicle01.getPosition().getPosition_x())+
					Math.abs(nextCoor.getPosition_y()-Server.vehicle01.getPosition().getPosition_y());
			double t1 = lengthToNextCoor/Server.vehicle01.getVelocity()*1000;// 小车走到下一个格子需要的时间(毫秒)
			// 按设定的速度，小车走到下一个格子，会更新多少次位置
			double times =t1/interval;
			// 其实 stepDistance_x的值基本固定，之所以这里还要计算，是因为，计算出来的值有正负号
			double stepDistance_x=Math.round((nextCoor.getPosition_x()-Server.vehicle01.getPosition().getPosition_x())/times);
			double stepDistance_y=Math.round((nextCoor.getPosition_y()-Server.vehicle01.getPosition().getPosition_y())/times);
			if(stepDistance_x!=0){
				Server.vehicle01.setVelocity_x(Server.vehicle01.getVelocity());
				Server.vehicle01.setVelocity_y(0);
			}else if (stepDistance_y!=0){
				Server.vehicle01.setVelocity_x(0);
				Server.vehicle01.setVelocity_y(Server.vehicle01.getVelocity());
			}else{
				Server.vehicle01.setVelocity_x(0);
				Server.vehicle01.setVelocity_y(0);
			}
			// 这里用Math.ceil(times)：当times小数位不为0时，最后的“半次”循环也要保证能按一次来走.(但是最后“半次”中，步进的距离不能再用stepDistance_x和stepDistance_y)
			for(int i=0;i<Math.ceil(times);i++){
				if(abortTask){// 放弃正在执行的任务
					return false;
				}
				try {
					Thread.sleep(interval);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				// 计算出来小车当前的位置离目标坐标,X方向上还有多少距离lengthToNextCoor_x，如果距离小于stepDistance_x，则接下来X方向步进lengthToNextCoor_x
				double lengthToNextCoor_x = nextCoor.getPosition_x()-Server.vehicle01.getPosition().getPosition_x();
				// 计算出来小车当前的位置离目标坐标,Y方向上还有多少距离lengthToNextCoor_x，如果距离小于stepDistance_y，则接下来Y方向步进lengthToNextCoor_y
				double lengthToNextCoor_y = nextCoor.getPosition_y()-Server.vehicle01.getPosition().getPosition_y();
				
				double stepDistance_x_tmp=Math.abs(stepDistance_x)>Math.abs(lengthToNextCoor_x)?lengthToNextCoor_x:stepDistance_x;
				double stepDistance_y_tmp=Math.abs(stepDistance_y)>Math.abs(lengthToNextCoor_y)?lengthToNextCoor_y:stepDistance_y;
				Server.vehicle01.getPosition().setPosition_x(Math.round(Server.vehicle01.getPosition().getPosition_x()+stepDistance_x_tmp));
				Server.vehicle01.getPosition().setPosition_y(Math.round(Server.vehicle01.getPosition().getPosition_y()+stepDistance_y_tmp));
				Server.vehicle01.setMileage(Server.vehicle01.getMileage()+new Double(Math.abs(stepDistance_x_tmp+stepDistance_y_tmp)).longValue());
				
			}
			LOG.info("执行导航任务，当前位置：{}",JSONObject.toJSONString(Server.vehicle01.getPosition()));
		}
		return true;
	}

	/**校验导航轨迹是否连续
	 * @param trails
	 * @return
	 */
	private boolean checkContinuity(List<NaviTrail> trails) {
		for(int i=0;i<trails.size()-1;i++){
			Coordinate currStartNode = ReadMapUtil.getStartNodeById(trails.get(i).getPathID()).getPosition();
			Coordinate nextStartNode = ReadMapUtil.getStartNodeById(trails.get(i+1).getPathID()).getPosition();
			if(Math.abs(currStartNode.getPosition_x()+currStartNode.getPosition_y()-nextStartNode.getPosition_x()-nextStartNode.getPosition_y())!=cellLength){
				// 如果相邻两条边的起点坐标之差不等于cellLength，则说明边不连续
				return false;
			}
		}
		return true;
	}

	/**取消导航
	 * @param packetModel
	 */
	private void doCancleNaviTask(PacketModel packetModel){
		abortTask=true;
		Server.vehicle01.setNaviState(NaviStateEnum.IDLE.getValue());
		up_state(LSConstants.STATE_TYPE_NAVI);
		Server.vehicle01.setVelocity_x(0);
		Server.vehicle01.setVelocity_y(0);
		packetModel.setPacketType(SocketUtil.getResponsePacketType(packetModel.getPacketType()));
		try {
			sendMsgToClient(packetModel);
		} catch (IOException e) {
			LOG.error("应答取消导航任务，指令发送异常！",e);
		}
	}
	/**暂停导航
	 * @param packetModel
	 */
	private void doPauseNaviTask(PacketModel packetModel){
		Server.vehicle01.setNaviState(NaviStateEnum.HANG.getValue());
		up_state(LSConstants.STATE_TYPE_NAVI);
		Server.vehicle01.setVelocity(0);
		packetModel.setPacketType(SocketUtil.getResponsePacketType(packetModel.getPacketType()));
		try {
			sendMsgToClient(packetModel);
		} catch (IOException e) {
			LOG.error("应答暂停导航任务，指令发送异常！",e);
		}
	}
	
	/**恢复导航
	 * @param packetModel
	 */
	private void doRecoverNaviTask(PacketModel packetModel){
		Server.vehicle01.setNaviState(NaviStateEnum.RUNNING.getValue());
		up_state(LSConstants.STATE_TYPE_NAVI);
		Server.vehicle01.setVelocity(500.0);
		packetModel.setPacketType(SocketUtil.getResponsePacketType(packetModel.getPacketType()));
		try {
			sendMsgToClient(packetModel);
		} catch (IOException e) {
			LOG.error("应答暂停导航任务，指令发送异常！",e);
		}
		moveWithNaviTrails();
	}
	
	/**追加导航
	 * @param packetModel
	 */
	private void doAppendNaviTask(PacketModel packetModel){
		if(Server.vehicle01.getNaviState()!=NaviStateEnum.RUNNING.getValue()){
			LOG.error("未执行导航任务，不可再接受追加导航任务！");
			Server.vehicle01.setNaviState(NaviStateEnum.ERROR.getValue());
			up_state(LSConstants.STATE_TYPE_NAVI);
		}
		AppendNaviTask appendNaviTask = new AppendNaviTask().fromBytes(packetModel.getData_bytes());
		try {
			LOG.info("接收到追加导航任务：{}",JSONObject.toJSONString(appendNaviTask));
			// 向调度应答导航任务
			NaviTask naviTask_response = new NaviTask(appendNaviTask.getTaskID());
			packetModel.setPacketType(SocketUtil.getResponsePacketType(packetModel.getPacketType()));
			packetModel.setData_bytes(naviTask_response.toBytes());
			sendMsgToClient(packetModel);
		} catch (IOException e) {
			LOG.error("应答追加导航任务，指令发送异常！",e);
		}
		
		/*try {
			Thread.sleep(2000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}*/
		List<Coordinate> coors =appendNaviTask.getTrails().stream().map((trail)->{
			Node startNode =ReadMapUtil.getStartNodeById(trail.getPathID());
			return startNode.getPosition();
		}).collect(Collectors.toList());
		vehiclePassingCoorQueue.addAll(coors);
		naviTrailsQueue4Query.offerAll(appendNaviTask.getTrails());
		// 如果此次追加，是最终的导航
		if(appendNaviTask.isDestTrail()){
			while(!(vehiclePassingCoorQueue.isEmpty() && (Server.vehicle01.getVelocity_x()+Server.vehicle01.getVelocity_y())==0.0));
			Server.vehicle01.setNaviState(NaviStateEnum.IDLE.getValue());
			up_state(LSConstants.STATE_TYPE_NAVI);
		}
		
	}
	/**查询导航轨迹
	 * @param packetModel
	 */
	private void doQueryNaviTrails(PacketModel packetModel){
		QueryNaviTrailsRequest queryNaviTrailsRequest = new QueryNaviTrailsRequest().fromBytes(packetModel.getData_bytes()); 
		LOG.info("查询导航轨迹：{}",JSONObject.toJSONString(queryNaviTrailsRequest));
		List<NaviTrail> naviTrails= naviTrailsQueue4Query.getLastElements(queryNaviTrailsRequest.getNaviTrailNum());
		QueryNaviTrailsResponse queryNaviTrailsResponse = new QueryNaviTrailsResponse(queryNaviTrailsRequest.getTaskID(),naviTrails.size(),naviTrails);
		LOG.info("查询导航轨迹结果：{}",JSONObject.toJSONString(queryNaviTrailsResponse));
		packetModel.setData_bytes(queryNaviTrailsResponse.toBytes());
		packetModel.setPacketType(SocketUtil.getResponsePacketType(packetModel.getPacketType()));
		try {
			sendMsgToClient(packetModel);
		} catch (IOException e) {
			LOG.error("应答查询导航轨迹异常！",e);
		}
	}
	
	
	/**执行操作任务
	 * @param packetModel
	 */
	private void doCarrayOutOperationTask(PacketModel packetModel){
		SendOperationTask sendOperationTask =new SendOperationTask().fromBytes(packetModel.getData_bytes());
		LOG.info("接收到操作任务：{}",JSONObject.toJSONString(sendOperationTask));
		OperationTask operationTask = new OperationTask();
		operationTask.setTaskID(sendOperationTask.getTaskID());
		packetModel.setData_bytes(operationTask.toBytes());
		packetModel.setPacketType(SocketUtil.getResponsePacketType(packetModel.getPacketType()));
		try {
			sendMsgToClient(packetModel);
		} catch (IOException e) {
			LOG.error("应答操作任务异常！",e);
		}
		if(sendOperationTask.getOperationCode()==LSConstants.OPERATIONCODE_BELT_ROTATION_ON){
			// 皮带转动
			if(sendOperationTask.getOperationParamNum()!=2){
				throw new RuntimeException("皮带转动，操作数有误");
			}
			LOG.info("沿方向{}转动第{}根皮带",sendOperationTask.getOperationParams().get(1),sendOperationTask.getOperationParams().get(0));
			Server.vehicle01.setOperationState(NaviStateEnum.RUNNING.getValue());
			up_state(LSConstants.STATE_TYPE_OPRT);
		}
		else if(sendOperationTask.getOperationCode()==LSConstants.OPERATIONCODE_BELT_ROTATION_STOP){
			// 皮带停止转动
			if(sendOperationTask.getOperationParamNum()!=1){
				throw new RuntimeException("皮带停止转动，操作数有误");
			}
			LOG.info("停止转动第{}根皮带",sendOperationTask.getOperationParams().get(0));
			Server.vehicle01.setOperationState(NaviStateEnum.IDLE.getValue());
			up_state(LSConstants.STATE_TYPE_OPRT);
		}
		else if(sendOperationTask.getOperationCode()==LSConstants.OPERATIONCODE_BELT_ROTATION_ONANDSTOP){
			// 皮带转动后停止
			if(sendOperationTask.getOperationParamNum()!=3){
				throw new RuntimeException("皮带转动后停止，操作数有误");
			}
			LOG.info("沿方向{}转动第{}根皮带{}毫秒",sendOperationTask.getOperationParams().get(1),sendOperationTask.getOperationParams().get(0),sendOperationTask.getOperationParams().get(2));
			Server.vehicle01.setOperationState(NaviStateEnum.RUNNING.getValue());
			up_state(LSConstants.STATE_TYPE_OPRT);
			try {
				Thread.sleep(sendOperationTask.getOperationParams().get(2));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Server.vehicle01.setOperationState(NaviStateEnum.IDLE.getValue());
			up_state(LSConstants.STATE_TYPE_OPRT);
		}
		else if(sendOperationTask.getOperationCode()==LSConstants.OPERATIONCODE_CONTAINER_DIRECTION){
			// 变更货柜朝向
			if(sendOperationTask.getOperationParamNum()!=1){
				throw new RuntimeException("变更货柜朝向，操作数有误");
			}
			LOG.info("变更货柜朝向,最终朝向：{}",sendOperationTask.getOperationParams().get(0));
			Server.vehicle01.setOperationState(NaviStateEnum.RUNNING.getValue());
			up_state(LSConstants.STATE_TYPE_OPRT);
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Server.vehicle01.setOperationState(NaviStateEnum.IDLE.getValue());
			up_state(LSConstants.STATE_TYPE_OPRT);
		}
		LOG.info("操作任务执行完毕");
	}
	
	/**取消操作任务
	 * @param packetModel
	 */
	private void doCancleOperationTask(PacketModel packetModel){
		OperationTask operationTask = new OperationTask().fromBytes(packetModel.getData_bytes());
		LOG.info("取消操作任务：{}",JSONObject.toJSONString(operationTask));
		packetModel.setPacketType(SocketUtil.getResponsePacketType(packetModel.getPacketType()));
		try {
			sendMsgToClient(packetModel);
		} catch (IOException e) {
			LOG.error("应答取消操作任务，指令发送异常！",e);
		}
		Server.vehicle01.setOperationState(NaviStateEnum.IDLE.getValue());
		up_state(LSConstants.STATE_TYPE_OPRT);
	}
	/**暂停操作任务
	 * @param packetModel
	 */
	private void doPauseOperationTask(PacketModel packetModel){
		OperationTask operationTask = new OperationTask().fromBytes(packetModel.getData_bytes());
		LOG.info("暂停操作任务：{}",JSONObject.toJSONString(operationTask));
		packetModel.setPacketType(SocketUtil.getResponsePacketType(packetModel.getPacketType()));
		try {
			sendMsgToClient(packetModel);
		} catch (IOException e) {
			LOG.error("应答暂停操作任务，指令发送异常！",e);
		}
		Server.vehicle01.setOperationState(NaviStateEnum.HANG.getValue());
		up_state(LSConstants.STATE_TYPE_OPRT);
	}
	/**恢复操作任务
	 * @param packetModel
	 */
	private void doRecoverOperationTask(PacketModel packetModel){
		OperationTask operationTask = new OperationTask().fromBytes(packetModel.getData_bytes());
		LOG.info("恢复操作任务：{}",JSONObject.toJSONString(operationTask));
		packetModel.setPacketType(SocketUtil.getResponsePacketType(packetModel.getPacketType()));
		try {
			sendMsgToClient(packetModel);
		} catch (IOException e) {
			LOG.error("应答恢复操作任务，指令发送异常！",e);
		}
		Server.vehicle01.setOperationState(NaviStateEnum.RUNNING.getValue());
		up_state(LSConstants.STATE_TYPE_OPRT);
	}
	
	/**写变量
	 * @param packetModel
	 */
	private void doWriteVarTask(PacketModel packetModel){
		WriteItem writeItem = new WriteItem().fromBytes(packetModel.getData_bytes());
		if(writeItem.getVarID()==LSConstants.VARID_STOPCHARGE){
			int controlChargeCode=((ControlChargeContent)writeItem.getWriteContent()).getControlChargeCode();
			if(controlChargeCode==LSConstants.CONTROLCHARGE_STOP){
				// 停止充电
				LOG.info("停止充电");
			}
			else if(controlChargeCode==LSConstants.CONTROLCHARGE_START){
				// 开始充电
				LOG.info("开始充电");
			}
		}
		else if(writeItem.getVarID()==LSConstants.VARID_PACKAGESIZE){
			// 写入包裹大小
			PackageSize packageSize =(PackageSize)writeItem.getWriteContent();
			LOG.info("写入包裹大小:{}",JSONObject.toJSONString(packageSize));
		}
		packetModel.setPacketType(SocketUtil.getResponsePacketType(packetModel.getPacketType()));
		packetModel.setData_bytes(new byte[0]);
		try {
			sendMsgToClient(packetModel);
		} catch (IOException e) {
			LOG.error("应答写变量，指令发送异常！",e);
		}
	}
	
	/**清除错误
	 * @param packetModel
	 */
	private void doClearError(PacketModel packetModel){
		LOG.info("清除错误");
		Server.vehicle01.setNaviState(NaviStateEnum.IDLE.getValue());
		up_state(LSConstants.STATE_TYPE_NAVI);
		packetModel.setPacketType(SocketUtil.getResponsePacketType(packetModel.getPacketType()));
		try {
			sendMsgToClient(packetModel);
		} catch (IOException e) {
			LOG.error("应答清除错误，指令发送异常！",e);
		}
	}
	
	private ReadItem_Response responsePosition(ReadItem item_request){
		double x = Server.vehicle01.getPosition().getPosition_x();
		double y = Server.vehicle01.getPosition().getPosition_y();
		PositionContent varContent = new PositionContent(x, y,
				Server.vehicle01.getAngle(), Server.vehicle01.getConfidenceDegree(),
				Server.vehicle01.getPathId(), Server.vehicle01.getNaviState());
		ReadItem_Response response =new ReadItem_Response(item_request.getVarType(), item_request.getVarID(), 0,varContent.toBytes().length, varContent);
//		LOG.info("positin:({})", (x + "," + y));
		return response;
	}
	private ReadItem_Response responseVelocity(ReadItem item_request){
		VelocityContent varContent = new VelocityContent(
				Server.vehicle01.getRadarCollisionAvoidance(),
				Server.vehicle01.getMalfunction(), Server.vehicle01.getVelocity_x(),
				Server.vehicle01.getVelocity_y(), Server.vehicle01.getAngularVelocity(),
				Server.vehicle01.getMileage());
		ReadItem_Response response =new ReadItem_Response(item_request.getVarType(), item_request.getVarID(), 0,varContent.toBytes().length, varContent);
		return response;
	}
	
	private ReadItem_Response responseChargeState(ReadItem item_request){
		ChargeStateContent varContent  = new ChargeStateContent(Server.vehicle01.getContactChargingPile(), Server.vehicle01.getRelayOn());
		ReadItem_Response response =new ReadItem_Response(item_request.getVarType(), item_request.getVarID(), 0,varContent.toBytes().length, varContent);
		return response;
	}
	
	private ReadItem_Response responseCheckPackage(ReadItem item_request){
		CheckPackageContent varContent  = new CheckPackageContent(Server.vehicle01.getHasPackage());
		ReadItem_Response response =new ReadItem_Response(item_request.getVarType(), item_request.getVarID(), 0,varContent.toBytes().length, varContent);
		return response;
	}
	
	private ReadItem_Response responseFlipState(ReadItem item_request){
		FlipStateContent varContent = new FlipStateContent(Server.vehicle01.getFlipState());
		ReadItem_Response response =new ReadItem_Response(item_request.getVarType(), item_request.getVarID(), 0,varContent.toBytes().length, varContent);
		return response;
	}
	
	private ReadItem_Response responseJackingDistance(ReadItem item_request){
		JackingDistanceContent varContent  = new JackingDistanceContent(Server.vehicle01.getJackingDistance());
		ReadItem_Response response =new ReadItem_Response(item_request.getVarType(), item_request.getVarID(), 0,varContent.toBytes().length, varContent);
		return response;
	}
	
	private ReadItem_Response responseBeltRotationState(ReadItem item_request){
		BeltRotationStateContent varContent  = new BeltRotationStateContent(Server.vehicle01.getBeltRotationState());
		ReadItem_Response response =new ReadItem_Response(item_request.getVarType(), item_request.getVarID(), 0,varContent.toBytes().length, varContent);
		return response;
	}
	
	private ReadItem_Response responseOperationState(ReadItem item_request){
		OperationStateContent varContent  = new OperationStateContent(Server.vehicle01.getOperationState());
		ReadItem_Response response =new ReadItem_Response(item_request.getVarType(), item_request.getVarID(), 0,varContent.toBytes().length, varContent);
		return response;
	}
	
	private ReadItem_Response responseBatteryCapacity(ReadItem item_request){
		return null;
	}

	public void close() {
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.terminate = true;
		fixedThreadPool.shutdown();
	}

	public boolean isTerminate() {
		return terminate;
	}

	public void setTerminate(boolean terminate) {
		this.terminate = terminate;
	}

}
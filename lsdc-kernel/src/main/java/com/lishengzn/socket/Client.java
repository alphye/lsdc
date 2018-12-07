package com.lishengzn.socket;

import java.io.*;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import com.lishengzn.constant.TopicAddressConstants;
import com.lishengzn.service.WebSocketMsgService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.lishengzn.dto.CommandDto;
import com.lishengzn.dto.OperationCommandDto;
import com.lishengzn.dto.VehicleDto;
import com.lishengzn.dto.VehicleMoveDto;
import com.lishengzn.entity.Coordinate;
import com.lishengzn.entity.Vehicle;
import com.lishengzn.entity.map.Node;
import com.lishengzn.entity.navi.AppendNaviTask;
import com.lishengzn.entity.navi.NaviTask;
import com.lishengzn.entity.navi.NaviTrail;
import com.lishengzn.entity.navi.QueryNaviTrailsRequest;
import com.lishengzn.entity.navi.QueryNaviTrailsResponse;
import com.lishengzn.entity.navi.SendNaviTask;
import com.lishengzn.entity.operation.OperationTask;
import com.lishengzn.entity.operation.SendOperationTask;
import com.lishengzn.entity.order.TransportOrder;
import com.lishengzn.entity.read.ReadItem_Response;
import com.lishengzn.entity.read.ReadVariable;
import com.lishengzn.entity.read.content.BatteryCapacityContent;
import com.lishengzn.entity.read.content.BeltRotationStateContent;
import com.lishengzn.entity.read.content.ChargeStateContent;
import com.lishengzn.entity.read.content.CheckPackageContent;
import com.lishengzn.entity.read.content.FlipStateContent;
import com.lishengzn.entity.read.content.JackingDistanceContent;
import com.lishengzn.entity.read.content.OperationStateContent;
import com.lishengzn.entity.read.content.PositionContent;
import com.lishengzn.entity.read.content.UpdateStateContent;
import com.lishengzn.entity.read.content.VelocityContent;
import com.lishengzn.entity.write.ControlChargeContent;
import com.lishengzn.entity.write.PackageSize;
import com.lishengzn.entity.write.WriteItem;
import com.lishengzn.enums.NaviStateEnum;
import com.lishengzn.exception.SimpleException;
import com.lishengzn.objectpool.TransportOrderPool;
import com.lishengzn.packet.PacketModel;
import com.lishengzn.packet.PacketSerialNo;
import com.lishengzn.task.CyclicTask;
import com.lishengzn.task.RetrievalAGVInfoTask;
import com.lishengzn.util.BeanConvertUtil;
import com.lishengzn.util.CacheManager;
import com.lishengzn.util.LSConstants;
import com.lishengzn.util.ReadMapUtil;
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
	private TransportOrderPool transportOrderPool;
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
		this.vehicle=new Vehicle();
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
		transportOrderPool = wac.getBean(TransportOrderPool.class);
		this.webSocketMsgService = webSocketMsgService;

	}
	private  void runRegrievalTask(Client client){
		List<Integer> varIDList_high=new ArrayList<Integer>();
		varIDList_high.add(LSConstants.VARID_POSITOIN);
		varIDList_high.add(LSConstants.VARID_VELOCITY);
		varIDList_high.add(LSConstants.VARID_CHARGESTATE);
		varIDList_high.add(LSConstants.VARID_CHECKPACKAGE);
		varIDList_high.add(LSConstants.VARID_FLIP_STATE);
		varIDList_high.add(LSConstants.VARID_JACKING_DISTANCE);
		varIDList_high.add(LSConstants.VARID_BELT_ROTATION_STATE);
		varIDList_high.add(LSConstants.VARID_OPERATION_STATE);
		CyclicTask task_high = new RetrievalAGVInfoTask(Integer.valueOf(kernelBaseLinebundle.getString("RetrievalAGVMsgTask_high_tSleep")), client,varIDList_high);
		fixedThreadPool.execute(task_high);

		List<Integer> varIDList_low=new ArrayList<Integer>();
		varIDList_low.add(LSConstants.VARID_BATTERYCAPACITY);
		CyclicTask task_low = new RetrievalAGVInfoTask(Integer.valueOf(kernelBaseLinebundle.getString("RetrievalAGVMsgTask_low_tSleep")), client,varIDList_low);
		fixedThreadPool.execute(task_low);
		
	}
	public void listen2Server() {
		fixedThreadPool.execute(this::handleServerMsg);
		fixedThreadPool.execute(this::sendHeartBeat);
	}
	
	/**
	 * 发送心跳
	 */
	private void sendHeartBeat(){
		while(!terminate){
			// 发送心跳包
    		LOG.info("------------ping");
    		try {
    			PacketModel packetModel = new PacketModel();
    			packetModel.setPacketType(LSConstants.PACKET_TYPE_HEART);
    			packetModel.setData_bytes(new byte[0]);
    			packetModel.setErrorCode(LSConstants.ERROR_CODE_SUCCESS);
				sendMsgToServer(packetModel);
				Thread.sleep(Long.valueOf(kernelBaseLinebundle.getString("sendHeartBeat_tSleep")));
			} catch (Exception e) {
				close();
				LOG.error("发心跳包异常，连接关闭！",e);
			} 
		}
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
	/**向服务器发送信息
	 * @param packetModel 包
	 * @return
	 */
	public void sendMsgToServer(PacketModel packetModel) {
		OutputStream os = null;
		try {
			os = socket.getOutputStream();
			SocketUtil.sendPacketData(os, packetModel);

		} catch (Exception e) {
			LOG.error("指令发送异常",e);
			close();
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
			PacketModel packetModel = null;
			try {
				while (!terminate && (packetModel = SocketUtil.readNextPacketData(is)) != null) {
					try {
//				LOG.info("command:  " + packetModel.getPacketSerialNo() + "==" + packetModel.getPacketType() + "=="+ Arrays.toString(packetModel.getData_bytes()));
						int packetType = packetModel.getPacketType();
						// 这里是否考虑 每一种包类型的处理都开启单独的线程
						if ( SocketUtil.getResponsePacketType(LSConstants.PACKET_TYPE_READVAR)==packetType) {
							// 处理读取变量的应答包
							PacketModel packetModel1=packetModel;
							fixedThreadPool.execute(()->handleReadVarResponse(packetModel1));
						}
						
						else if (LSConstants.PACKET_TYPE_UP_STATE==(packetType)) {
							// 处理agv上报状态
							PacketModel packetModel1=packetModel;
							fixedThreadPool.execute(()->handleUpState(packetModel1));
						}
						else if ( SocketUtil.getResponsePacketType(LSConstants.PACKET_TYPE_LOGIN_STR)==(packetType)) {
							// 调度中心需要登录
							// TODO
						}
						else if ( SocketUtil.getResponsePacketType(LSConstants.PACKET_TYPE_SEND_NAVITASK)==(packetType)) {
							// 发送导航任务应答包
							PacketModel packetModel1=packetModel;
							fixedThreadPool.execute(()->handleSendNaviTaskResponse(packetModel1));
				
						}
						else if ( SocketUtil.getResponsePacketType(LSConstants.PACKET_TYPE_CANCLE_NAVITASK)==(packetType)) {
							// 取消导航任务应答包
							PacketModel packetModel1=packetModel;
							fixedThreadPool.execute(()->handleCancleNaviTaskResponse(packetModel1));
				
						}
						else if ( SocketUtil.getResponsePacketType(LSConstants.PACKET_TYPE_PAUSE_NAVITASK)==(packetType)) {
							// 暂停导航任务应答包
							PacketModel packetModel1=packetModel;
							fixedThreadPool.execute(()->handlePauseNaviTaskResponse(packetModel1));
							
						}
						else if ( SocketUtil.getResponsePacketType(LSConstants.PACKET_TYPE_RECOVER_NAVITASK)==(packetType)) {
							// 恢复导航任务应答包
							PacketModel packetModel1=packetModel;
							fixedThreadPool.execute(()->handleRecoverNaviTaskResponse(packetModel1));
							
						}
						else if ( SocketUtil.getResponsePacketType(LSConstants.PACKET_TYPE_QUERY_NAVITRAILS)==(packetType)) {
							// 查询导航轨迹应答包
							PacketModel packetModel1=packetModel;
							fixedThreadPool.execute(()->queryNaviTrailsResponse(packetModel1));
							
						}
						else if ( SocketUtil.getResponsePacketType(LSConstants.PACKET_TYPE_APPEND_NAVITASK)==(packetType)) {
							// 追加导航任务应答包
							PacketModel packetModel1=packetModel;
							fixedThreadPool.execute(()->handleAppendNaviTaskResponse(packetModel1));
							
						}
						else if ( SocketUtil.getResponsePacketType(LSConstants.PACKET_TYPE_SEND_OPRTNTASK)==(packetType)) {
							// 发送操作任务应答包
							PacketModel packetModel1=packetModel;
							fixedThreadPool.execute(()->handleSendOperationTaskResponse(packetModel1));
							
						}
						else if ( SocketUtil.getResponsePacketType(LSConstants.PACKET_TYPE_CANCLE_OPRTNTASK)==(packetType)) {
							// 取消操作任务应答包
							PacketModel packetModel1=packetModel;
							fixedThreadPool.execute(()->handleCanlceOperationTaskResponse(packetModel1));
							
						}
						else if ( SocketUtil.getResponsePacketType(LSConstants.PACKET_TYPE_PAUSE_OPRTNTASK)==(packetType)) {
							// 暂停操作任务应答包
							PacketModel packetModel1=packetModel;
							fixedThreadPool.execute(()->handlePauseOperationTaskResponse(packetModel1));
							
						}
						else if ( SocketUtil.getResponsePacketType(LSConstants.PACKET_TYPE_RECOVER_OPRTNTASK)==(packetType)) {
							// 恢复操作任务应答包
							PacketModel packetModel1=packetModel;
							fixedThreadPool.execute(()->handleRecoverOperationTaskResponse(packetModel1));
							
						}
						else if ( SocketUtil.getResponsePacketType(LSConstants.PACKET_TYPE_WRITEVAR)==(packetType)) {
							// 写变量应答包
							PacketModel packetModel1=packetModel;
							fixedThreadPool.execute(()->handleWriteVarResponse(packetModel1));
							
						}
						else if ( SocketUtil.getResponsePacketType(LSConstants.PACKET_TYPE_CLEAR_ERROR)==(packetType)) {
							// 清除错误应答包
							PacketModel packetModel1=packetModel;
							fixedThreadPool.execute(()->handleClearErrorResponse(packetModel1));
							
						}
					} catch (Exception e) {
						LOG.error("解析小车答应信息异常!",e);
					}
				}
				LOG.info("handleServerMsg ===end");
			} catch (IOException e) {
				LOG.error("指令接收异常!",e);
				close();
			}
	}
	
	/**处理发送导航任务应答包
	 * @param packetModel
	 */
	private void handleSendNaviTaskResponse(PacketModel packetModel) {
		NaviTask naviTask = new NaviTask().fromBytes(packetModel.getData_bytes());
		TransportOrder transportOrder =transportOrderPool.getTransportOrderByNaviTaskID(naviTask.getTaskID());
		if(LSConstants.ERROR_CODE_SUCCESS==packetModel.getErrorCode()  && transportOrder.getNaviTask().isCompleteNavi()){
			while(!(vehicle.getPosition().equals(transportOrder.getDestCoor())));
			transportOrder.setState(TransportOrder.STATE.FINISHED);
			LOG.info("处理发送导航任务应答包--导航任务完成");
		}
		else if (LSConstants.ERROR_CODE_FAILED==packetModel.getErrorCode()){
			transportOrder.setState(TransportOrder.STATE.FAILED);
			LOG.error("处理发送导航任务应答包--导航任务失败");
		}
	}
	/**处理取消导航任务的应答包
	 * @param packetModel
	 */
	private void handleCancleNaviTaskResponse(PacketModel packetModel) {
		NaviTask naviTask = new NaviTask().fromBytes(packetModel.getData_bytes());
		LOG.info("处理取消导航任务应答包:{}",naviTask.getTaskID());
		if(packetModel.getErrorCode()==LSConstants.ERROR_CODE_SUCCESS){
			TransportOrder transportOrder = transportOrderPool.getTransportOrderByNaviTaskID(naviTask.getTaskID());
			if(transportOrder!=null){
				transportOrder.setState(TransportOrder.STATE.FAILED);
				LOG.info("取消导航任务完成");
			}
			vehicle.setNaviState(NaviStateEnum.IDLE.getValue());
		}else{
			throw new SimpleException("取消导航任务失败，任务ID:"+naviTask.getTaskID());
		}
	}
	/**处理暂停导航任务的应答包
	 * @param packetModel
	 */
	private void handlePauseNaviTaskResponse(PacketModel packetModel) {
		NaviTask naviTask = new NaviTask().fromBytes(packetModel.getData_bytes());
		LOG.info("处理暂停导航任务应答包:{}",naviTask.getTaskID());
		if(packetModel.getErrorCode()==LSConstants.ERROR_CODE_SUCCESS){
			TransportOrder transportOrder = transportOrderPool.getTransportOrderByNaviTaskID(naviTask.getTaskID());
			if(transportOrder!=null){
				transportOrder.setState(TransportOrder.STATE.PAUSE);
				LOG.info("暂停导航任务完成");
			}
			vehicle.setNaviState(NaviStateEnum.HANG.getValue());
		}else{
			throw new SimpleException("暂停导航任务失败，任务ID:"+naviTask.getTaskID());
		}
	}
	/**处理恢复导航任务的应答包
	 * @param packetModel
	 */
	private void handleRecoverNaviTaskResponse(PacketModel packetModel) {
		NaviTask naviTask = new NaviTask().fromBytes(packetModel.getData_bytes());
		LOG.info("处理恢复导航任务应答包:{}",naviTask.getTaskID());
		if(packetModel.getErrorCode()==LSConstants.ERROR_CODE_SUCCESS){
			TransportOrder transportOrder = transportOrderPool.getTransportOrderByNaviTaskID(naviTask.getTaskID());
			if(transportOrder!=null){
				transportOrder.setState(TransportOrder.STATE.EXECUTING);
				LOG.info("恢复导航任务完成");
			}
			vehicle.setNaviState(NaviStateEnum.RUNNING.getValue());
		}else{
			throw new SimpleException("恢复导航任务失败，任务ID:"+naviTask.getTaskID());
		}
	}
	
	/**处理查询导航轨迹应答包
	 * @param packetModel
	 */
	private void queryNaviTrailsResponse(PacketModel packetModel){
		QueryNaviTrailsResponse queryNaviTrails = new QueryNaviTrailsResponse().fromBytes(packetModel.getData_bytes());
		if(packetModel.getErrorCode()==LSConstants.ERROR_CODE_SUCCESS){
			LOG.info("处理查询导航轨迹应答包：{}",JSONObject.toJSONString(queryNaviTrails));
		}else{
			throw new SimpleException("查询导航轨失败，任务ID:"+queryNaviTrails.getTaskID());
		}
		
	}
	/**处理追加导航的应答包
	 * @param packetModel
	 */
	private void handleAppendNaviTaskResponse(PacketModel packetModel){
		NaviTask naviTask = new NaviTask().fromBytes(packetModel.getData_bytes());
		LOG.info("处理追加导航任务应答包:{}",naviTask.getTaskID());
		TransportOrder transportOrder =transportOrderPool.getTransportOrderByNaviTaskID(naviTask.getTaskID());
		if(packetModel.getErrorCode()==LSConstants.ERROR_CODE_SUCCESS){
			List<AppendNaviTask> appendNaviTasks = transportOrder.getAppendNavitasks();
			if(appendNaviTasks.get(appendNaviTasks.size()-1).isDestTrail()){
				while(!(vehicle.getPosition().equals(transportOrder.getDestCoor())));
				transportOrder.setState(TransportOrder.STATE.FINISHED);
				LOG.info("处理追加导航任务应答包:{}，--最终导航，任务完成！",naviTask.getTaskID());
			}
		}else{
			throw new SimpleException("追加导航任务失败，任务ID:"+naviTask.getTaskID());
		}
	}
	
	
	/**处理发送操作任务的应答包
	 * @param packetModel
	 */
	private void handleSendOperationTaskResponse(PacketModel packetModel){
		OperationTask operationTask = new OperationTask().fromBytes(packetModel.getData_bytes());
		LOG.info("处理发送操作任务的应答包：{}",operationTask.getTaskID());
		if(packetModel.getErrorCode()==LSConstants.ERROR_CODE_SUCCESS){
			LOG.info("发送操作任务完成");
		}else{
			throw new SimpleException("操作任务失败，任务ID:"+operationTask.getTaskID());
		}
	}
	
	/**处理取消操作任务的应答包
	 * @param packetModel
	 */
	private void handleCanlceOperationTaskResponse(PacketModel packetModel){
		OperationTask operationTask = new OperationTask().fromBytes(packetModel.getData_bytes());
		LOG.info("处理取消操作任务的应答包：{}",operationTask.getTaskID());
		if(packetModel.getErrorCode()==LSConstants.ERROR_CODE_SUCCESS){
			LOG.info("取消操作任务完成");
		}else{
			throw new SimpleException("取消操作任务失败，任务ID:"+operationTask.getTaskID());
		}
	}
	/**处理暂停操作任务的应答包
	 * @param packetModel
	 */
	private void handlePauseOperationTaskResponse(PacketModel packetModel){
		OperationTask operationTask = new OperationTask().fromBytes(packetModel.getData_bytes());
		LOG.info("处理暂停操作任务的应答包：{}",operationTask.getTaskID());
		if(packetModel.getErrorCode()==LSConstants.ERROR_CODE_SUCCESS){
			LOG.info("暂停操作任务完成");
		}else{
			throw new SimpleException("暂停操作任务失败，任务ID:"+operationTask.getTaskID());
		}
	}
	/**处理恢复操作任务的应答包
	 * @param packetModel
	 */
	private void handleRecoverOperationTaskResponse(PacketModel packetModel){
		OperationTask operationTask = new OperationTask().fromBytes(packetModel.getData_bytes());
		LOG.info("处理恢复操作任务的应答包：{}",operationTask.getTaskID());
		if(packetModel.getErrorCode()==LSConstants.ERROR_CODE_SUCCESS){
			LOG.info("恢复操作任务完成");
		}else{
			throw new SimpleException("恢复操作任务失败，任务ID:"+operationTask.getTaskID());
		}
	}
	
	
	/**处理写变量的应答包
	 * @param packetModel
	 */
	private void handleWriteVarResponse(PacketModel packetModel){
		if(packetModel.getErrorCode()==LSConstants.ERROR_CODE_SUCCESS){
			LOG.info("写变量完成");
		}else{
			throw new SimpleException("写变量失败");
		}
	}
	
	/**处理清除错误的应答包
	 * @param packetModel
	 */
	private void handleClearErrorResponse(PacketModel packetModel){
		if(packetModel.getErrorCode()==LSConstants.ERROR_CODE_SUCCESS){
			LOG.info("清除错误完成");
			if(vehicle.getTransportOrder()!=null && vehicle.getTransportOrder().getState()!=TransportOrder.STATE.FINISHED
					&& vehicle.getTransportOrder().getState()!=TransportOrder.STATE.FAILED){
				vehicle.getTransportOrder().setState(TransportOrder.STATE.FAILED);
			}
		}else{
			throw new SimpleException("清除错误失败");
		}
	}
	
	/**处理agv上报状态
	 * @param packetModel
	 */
	private void handleUpState(PacketModel packetModel) {
		UpdateStateContent updateState = new UpdateStateContent().fromBytes(packetModel.getData_bytes());
		CommandDto cmd=null;
		String topicAddress = "";
		if(updateState.getStateType()==(LSConstants.STATE_TYPE_NAVI)){//导航状态
			vehicle.setNaviState(updateState.getState());
			VehicleDto vehicleDto =BeanConvertUtil.beanConvert(vehicle, VehicleDto.class);
			TranslateUtil.translateEntity(vehicleDto);
			cmd = new CommandDto(CommandDto.TYPE.setVehicleNaviState.name(), vehicleDto);
			topicAddress=TopicAddressConstants.setVehicleNaviState;
		}else{
			vehicle.setOperationState(updateState.getState());
			VehicleDto vehicleDto =BeanConvertUtil.beanConvert(vehicle, VehicleDto.class);
			TranslateUtil.translateEntity(vehicleDto);
			cmd = new CommandDto(CommandDto.TYPE.setVehicleOperationState.name(), vehicleDto);
			topicAddress=TopicAddressConstants.setVehicleOperationState;
		}
		
		LOG.info("更新车辆状态信息：{}" + JSONObject.toJSONString(updateState));
		webSocketMsgService.sendToAll(topicAddress,JSONObject.toJSONString(cmd));
	}

	/**处理读取变量的应答包
	 * @param packetModel
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	private void handleReadVarResponse(PacketModel packetModel){
		ReadVariable<ReadItem_Response> readVariable =new ReadVariable<ReadItem_Response>(ReadItem_Response.class).fromBytes(packetModel.getData_bytes());
		List<ReadItem_Response> itemList =readVariable.getItems();
		for (int i = 0; i < itemList.size(); i++) {
			ReadItem_Response item=itemList.get(i);
			// 取出应答包中变量
			if (item.getVarID() == LSConstants.VARID_POSITOIN) {// 位置
				// 将变量总的readContent_bytes转换位具体的subVariable对象
				PositionContent pc =(PositionContent) item.getReadContent();
				vehicle.setPosition(new Coordinate(pc.getPosition_x(),pc.getPosition_y()));
				vehicle.setAngle(pc.getAngle());
				vehicle.setPathId(pc.getPathId());
				vehicle.setConfidenceDegree(pc.getConfidenceDegree());
//				vehicle.setNaviState(pc.getNaviState());
				
			}else if (item.getVarID() == LSConstants.VARID_VELOCITY) {// 速度
				VelocityContent vv = (VelocityContent) item.getReadContent();
				vehicle.setVelocity_x(vv.getVx());
				vehicle.setVelocity_y(vv.getVy());
				vehicle.setAngularVelocity(vv.getAngularVelocity());
				vehicle.setRadarCollisionAvoidance(vv.getRadarCollisionAvoidance());
				vehicle.setMalfunction(vv.getMalfunction());
				vehicle.setMileage(vv.getMileage());
			}else if (item.getVarID() == LSConstants.VARID_CHARGESTATE) {// 充电状态
				ChargeStateContent cv = (ChargeStateContent) item.getReadContent();
				vehicle.setContactChargingPile(cv.getContactChargingPile());
				vehicle.setRelayOn(cv.getRelayOn());
			}else if (item.getVarID() == LSConstants.VARID_CHECKPACKAGE) {// 检测包裹
				CheckPackageContent cv = (CheckPackageContent) item.getReadContent();
				vehicle.setHasPackage(cv.getHasPackage());
			}else if (item.getVarID() == LSConstants.VARID_FLIP_STATE) {// 翻盖状态
				FlipStateContent cv =  (FlipStateContent) item.getReadContent();
				vehicle.setFlipState(cv.getFlipState());
			}else if (item.getVarID() == LSConstants.VARID_JACKING_DISTANCE) {// 顶升距离
				JackingDistanceContent cv = (JackingDistanceContent) item.getReadContent();
				vehicle.setJackingDistance(cv.getJackingDistance());
			}else if (item.getVarID() == LSConstants.VARID_BELT_ROTATION_STATE) {// 皮带转动状态
				BeltRotationStateContent cv = (BeltRotationStateContent) item.getReadContent();
				vehicle.setBeltRotationState(cv.getBeltRotationState());
			}else if (item.getVarID() == LSConstants.VARID_OPERATION_STATE) {// 操作状态
				OperationStateContent cv = (OperationStateContent) item.getReadContent();
				vehicle.setOperationState(cv.getOperationState());
			}else if (item.getVarID() == LSConstants.VARID_BATTERYCAPACITY) {// 电池容量
				BatteryCapacityContent cv = (BatteryCapacityContent) item.getReadContent();
				vehicle.setBatteryCapacity(cv.getBatteryCapacity());
				vehicle.setBatteryResidues(cv.getBatteryResidues());
				vehicle.setBatteryState(cv.getBatteryState());
			}
			
		}
//		LOG.info("更新车辆信息：{}" + JSONObject.toJSONString(vehicle));
		VehicleDto vehicleDto =BeanConvertUtil.beanConvert(vehicle, VehicleDto.class);
		TranslateUtil.translateEntity(vehicleDto);
		CommandDto cmd = new CommandDto(CommandDto.TYPE.setVehicleInfo.name(), vehicleDto);
		webSocketMsgService.sendToAll(TopicAddressConstants.setVehicleInfo,JSONObject.toJSONString(cmd));
	}

	
	
	/**取消导航任务
	 * 
	 */
	public void cancleNaviTask(){
		TransportOrder transportOrder = vehicle.getTransportOrder();
		if(transportOrder==null){
			throw new SimpleException("当前车辆未执行导航任务！");
		}
		if(transportOrder.getState()==TransportOrder.STATE.FINISHED||
				transportOrder.getState()==TransportOrder.STATE.FAILED){
			throw new SimpleException("当前车辆未执行导航任务或导航任务已经执行结束！");
		}
		LOG.info("取消导航任务:{}",transportOrder.getNaviTask().getTaskID());
		PacketModel packetModel = new PacketModel(); 
		packetModel.setPacketSerialNo(PacketSerialNo.getSerialNo());
		packetModel.setErrorCode(LSConstants.ERROR_CODE_SUCCESS);
		packetModel.setPacketType(LSConstants.PACKET_TYPE_CANCLE_NAVITASK);
		NaviTask naviTask = new NaviTask(transportOrder.getNaviTaskID());
		packetModel.setData_bytes(naviTask.toBytes());
		sendMsgToServer(packetModel);
	}
	/**暂停导航任务
	 * 
	 */
	public void pauseNaviTask(){
		TransportOrder transportOrder = vehicle.getTransportOrder();
		if(transportOrder==null){
			throw new SimpleException("当前车辆未执行导航任务！");
		}
		if(transportOrder.getState()!=TransportOrder.STATE.EXECUTING){
			throw new SimpleException("当前车辆未执行导航任务或导航任务已经执行结束！");
		}
		LOG.info("暂停导航任务:{}",transportOrder.getNaviTask().getTaskID());
		PacketModel packetModel = new PacketModel(); 
		packetModel.setPacketSerialNo(PacketSerialNo.getSerialNo());
		packetModel.setErrorCode(LSConstants.ERROR_CODE_SUCCESS);
		packetModel.setPacketType(LSConstants.PACKET_TYPE_PAUSE_NAVITASK);
		NaviTask naviTask = new NaviTask(transportOrder.getNaviTaskID());
		packetModel.setData_bytes(naviTask.toBytes());
		sendMsgToServer(packetModel);
	}
	/**恢复导航任务
	 * 
	 */
	public void recoverNaviTask(){
		TransportOrder transportOrder = vehicle.getTransportOrder();
		if(transportOrder==null){
			throw new SimpleException("当前车辆未执行导航任务！");
		}
		if(transportOrder.getState()!=TransportOrder.STATE.PAUSE){
			throw new SimpleException("当前车辆没有暂停状态的导航任务！");
		}
		LOG.info("恢复导航任务:{}",transportOrder.getNaviTask().getTaskID());
		PacketModel packetModel = new PacketModel(); 
		packetModel.setPacketSerialNo(PacketSerialNo.getSerialNo());
		packetModel.setErrorCode(LSConstants.ERROR_CODE_SUCCESS);
		packetModel.setPacketType(LSConstants.PACKET_TYPE_RECOVER_NAVITASK);
		NaviTask naviTask = new NaviTask(transportOrder.getNaviTaskID());
		packetModel.setData_bytes(naviTask.toBytes());
		sendMsgToServer(packetModel);
	}
	
	
	/**向小车发送导航任务
	 * @param transportOrder
	 */
	public void sendNaviTask(TransportOrder transportOrder){
		// 要根据vehicleMove里的数据，生成导航任务，发送给小车（server）
		try {
			// 根据目的地 生成导航任务
			SendNaviTask naviTask = generateSendNaviTask(transportOrder);
			if(naviTask==null){
				return;
			}
			LOG.info("发送导航任务:{}",JSONObject.toJSONString(naviTask));
			transportOrder.setNaviTask(naviTask);
			vehicle.setTransportOrder(transportOrder);
			transportOrder.setState(TransportOrder.STATE.EXECUTING);
			PacketModel packetModel = new PacketModel();
			packetModel.setPacketSerialNo(PacketSerialNo.getSerialNo());
			packetModel.setErrorCode(LSConstants.ERROR_CODE_SUCCESS);
			packetModel.setPacketType(LSConstants.PACKET_TYPE_SEND_NAVITASK);
			packetModel.setData_bytes(naviTask.toBytes());
			sendMsgToServer(packetModel);
			vehicle.setNaviState(NaviStateEnum.RUNNING.getValue());
		}catch (SimpleException e) {
			transportOrder.setState(TransportOrder.STATE.FAILED);
			throw e;
		} catch (Exception e) {
			transportOrder.setState(TransportOrder.STATE.FAILED);
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 查询导航轨迹
	 */
	public void queryNaviTrails(){
		LOG.info("查询导航轨迹");
		TransportOrder transportOrder = vehicle.getTransportOrder();
		if(transportOrder==null){
			throw new SimpleException("当前车辆未执行导航任务！");
		}
		// 暂定查询10条
		QueryNaviTrailsRequest queryNaviTrails = new QueryNaviTrailsRequest(transportOrder.getNaviTaskID(),20);
		PacketModel packetModel = new PacketModel();
		packetModel.setPacketSerialNo(PacketSerialNo.getSerialNo());
		packetModel.setErrorCode(LSConstants.ERROR_CODE_SUCCESS);
		packetModel.setPacketType(LSConstants.PACKET_TYPE_QUERY_NAVITRAILS);
		packetModel.setData_bytes(queryNaviTrails.toBytes());
		sendMsgToServer(packetModel);
	}
	/**追加导航
	 * @param vehicleMove
	 */
	public void appendNaviTaskWidthCellNum(VehicleMoveDto vehicleMove) {
		TransportOrder transportOrder = vehicle.getTransportOrder();
		if(transportOrder==null || transportOrder.getState()!=TransportOrder.STATE.EXECUTING){
			throw new SimpleException("当前车辆未执行导航任务！");
		}
		try {
			Coordinate startCoor=transportOrder.getDestCoor().clone();
			Coordinate destCoor = calcDestCoorByCellNum(startCoor, vehicleMove.getCellNum(), vehicleMove.getDirect());
			transportOrder.setDestCoor(destCoor);
			AppendNaviTask naviTask =generateAppendNaviTask(startCoor, transportOrder);
			LOG.info("追加导航任务:{}",JSONObject.toJSONString(naviTask));
			List<AppendNaviTask>appendNaviTasks =transportOrder.getAppendNavitasks();
			if(appendNaviTasks==null){
				appendNaviTasks=new ArrayList<AppendNaviTask>();
			}
			appendNaviTasks.add(naviTask);
			transportOrder.setAppendNavitasks(appendNaviTasks);
			
			if(naviTask==null){
				return;
			}
			vehicle.setTransportOrder(transportOrder);
			PacketModel packetModel = new PacketModel();
			packetModel.setPacketSerialNo(PacketSerialNo.getSerialNo());
			packetModel.setErrorCode(LSConstants.ERROR_CODE_SUCCESS);
			packetModel.setPacketType(LSConstants.PACKET_TYPE_APPEND_NAVITASK);
			packetModel.setData_bytes(naviTask.toBytes());
			sendMsgToServer(packetModel);
		} catch (SimpleException e) {
//			transportOrder.setState(TransportOrder.STATE.FAILED);
			throw e;
		} catch (Exception e) {
//			transportOrder.setState(TransportOrder.STATE.FAILED);
			throw new RuntimeException(e);
		}
		
	}
	
	
	/**发送操作任务
	 * @param operationCommandDto
	 */
	public void sendOperationTask(OperationCommandDto operationCommandDto){
		SendOperationTask sendOperationTask = new SendOperationTask();
		long taskID=Long.parseLong(new Double(Math.round(Math.random()*10000)).intValue()+""+System.currentTimeMillis());
		sendOperationTask.setTaskID(taskID);
		sendOperationTask.setOperationCode(operationCommandDto.getOperationCode());
		sendOperationTask.setOperationParamNum(operationCommandDto.getOperationParams().size());
		sendOperationTask.setOperationParams(operationCommandDto.getOperationParams());
		LOG.info("发送操作任务:{}",JSONObject.toJSONString(sendOperationTask));
		PacketModel packetModel = new PacketModel();
		packetModel.setPacketSerialNo(PacketSerialNo.getSerialNo());
		packetModel.setPacketType(LSConstants.PACKET_TYPE_SEND_OPRTNTASK);
		packetModel.setErrorCode(LSConstants.ERROR_CODE_SUCCESS);
		packetModel.setData_bytes(sendOperationTask.toBytes());
		sendMsgToServer(packetModel);
	}
	
	
	
	/**取消操作任务
	 * @param operationCommandDto
	 */
	public void cancleOperationTask(OperationCommandDto operationCommandDto){
		OperationTask operationTask = new OperationTask();
		long taskID=Long.parseLong(new Double(Math.round(Math.random()*10000)).intValue()+""+System.currentTimeMillis());
		operationTask.setTaskID(taskID);
		LOG.info("取消操作任务:{}",JSONObject.toJSONString(operationTask));
		PacketModel packetModel = new PacketModel();
		packetModel.setPacketSerialNo(PacketSerialNo.getSerialNo());
		packetModel.setPacketType(LSConstants.PACKET_TYPE_CANCLE_OPRTNTASK);
		packetModel.setErrorCode(LSConstants.ERROR_CODE_SUCCESS);
		packetModel.setData_bytes(operationTask.toBytes());
		sendMsgToServer(packetModel);
	}
	
	/**暂停操作任务
	 * @param operationCommandDto
	 */
	public void pauseOperationTask(OperationCommandDto operationCommandDto){
		OperationTask operationTask = new OperationTask();
		long taskID=Long.parseLong(new Double(Math.round(Math.random()*10000)).intValue()+""+System.currentTimeMillis());
		operationTask.setTaskID(taskID);
		LOG.info("暂停操作任务:{}",JSONObject.toJSONString(operationTask));
		PacketModel packetModel = new PacketModel();
		packetModel.setPacketSerialNo(PacketSerialNo.getSerialNo());
		packetModel.setPacketType(LSConstants.PACKET_TYPE_PAUSE_OPRTNTASK);
		packetModel.setErrorCode(LSConstants.ERROR_CODE_SUCCESS);
		packetModel.setData_bytes(operationTask.toBytes());
		sendMsgToServer(packetModel);
	}
	
	/**恢复操作任务
	 * @param operationCommandDto
	 */
	public void recoverOperationTask(OperationCommandDto operationCommandDto){
		OperationTask operationTask = new OperationTask();
		long taskID=Long.parseLong(new Double(Math.round(Math.random()*10000)).intValue()+""+System.currentTimeMillis());
		operationTask.setTaskID(taskID);
		LOG.info("恢复操作任务:{}",JSONObject.toJSONString(operationTask));
		PacketModel packetModel = new PacketModel();
		packetModel.setPacketSerialNo(PacketSerialNo.getSerialNo());
		packetModel.setPacketType(LSConstants.PACKET_TYPE_RECOVER_OPRTNTASK);
		packetModel.setErrorCode(LSConstants.ERROR_CODE_SUCCESS);
		packetModel.setData_bytes(operationTask.toBytes());
		sendMsgToServer(packetModel);
	}
	/**控制充电
	 */
	public void controlCharge(int controlChargeCode){
		LOG.info("写变量--控制充电："+(controlChargeCode==LSConstants.CONTROLCHARGE_STOP?"停止充电":"开始充电"));
		WriteItem writeItem = new WriteItem(LSConstants.VARTYPE_STOPCHARGE, LSConstants.VARID_STOPCHARGE, 0, 0, new ControlChargeContent(controlChargeCode));
		writeVariable(writeItem);
	}
	
	/**写入包裹大小
	 */
	public void writePakageSize(VehicleMoveDto vehicleMove){
		PackageSize packageSize = new PackageSize(vehicleMove.getPackageLength(),vehicleMove.getPackageWidth());
		LOG.info("写入包裹大小:{}",JSONObject.toJSONString(packageSize));
		WriteItem writeItem = new WriteItem(LSConstants.VARTYPE_PACKAGESIZE, LSConstants.VARID_PACKAGESIZE, 0, 0, packageSize);
		writeVariable(writeItem);
	}
	
	private void writeVariable(WriteItem writeItem ){
		PacketModel packetModel = new PacketModel();
		packetModel.setPacketSerialNo(PacketSerialNo.getSerialNo());
		packetModel.setPacketType(LSConstants.PACKET_TYPE_WRITEVAR);
		packetModel.setErrorCode(LSConstants.ERROR_CODE_SUCCESS);
		packetModel.setData_bytes(writeItem.toBytes());
		sendMsgToServer(packetModel);
	}
	/**
	 * 清除错误
	 */
	public void clearError(){
		LOG.info("清除错误");
		PacketModel packetModel = new PacketModel();
		packetModel.setPacketSerialNo(PacketSerialNo.getSerialNo());
		packetModel.setPacketType(LSConstants.PACKET_TYPE_CLEAR_ERROR);
		packetModel.setErrorCode(LSConstants.ERROR_CODE_SUCCESS);
		packetModel.setData_bytes(new byte[0]);
		sendMsgToServer(packetModel);
	}
	/**生成追加导航的任务实体
	 * @param startCoor
	 * @param transportOrder
	 * @return
	 */
	private AppendNaviTask generateAppendNaviTask(Coordinate startCoor,TransportOrder transportOrder){
		Coordinate destCoor = transportOrder.getDestCoor();
		if(destCoor==null){
			transportOrder.setState(TransportOrder.STATE.FAILED);
			LOG.error("追加导航目的地为空!");
			return null;
		}
		else if(destCoor.equals(startCoor)){
			transportOrder.setState(TransportOrder.STATE.FAILED);
			LOG.error("追加导航目的地为小车当前导航终点!");
			return null;
		}
		// 生成最简单的导航：先x方向移动，再y方向移动。
		int appendIndex = transportOrder.getNaviTask().getTrails().size();
		AppendNaviTask naviTask=null;
		Coordinate currCoor = startCoor.clone();
		List<Coordinate> coorList = new ArrayList<Coordinate>();
		
		coorList.add(currCoor);
		// 首先将X方向上要经过的点加入到list
		for(int i=1;i<(Math.abs(destCoor.getPosition_x()-currCoor.getPosition_x()))/cellLength+1;i++){
			if(destCoor.getPosition_x()<currCoor.getPosition_x()){
				coorList.add(new Coordinate(currCoor.getPosition_x()-i*cellLength, currCoor.getPosition_y()));
			}else{
				coorList.add(new Coordinate(currCoor.getPosition_x()+i*cellLength, currCoor.getPosition_y()));
			}
		}
		
		// 再将Y方向上要经过的点加入到list
		for(int i=1;i<(Math.abs(destCoor.getPosition_y()-currCoor.getPosition_y()))/cellLength+1;i++){
			if(destCoor.getPosition_y()<currCoor.getPosition_y()){
				coorList.add(new Coordinate(currCoor.getPosition_x(), currCoor.getPosition_y()-i*cellLength));
			}else{
				coorList.add(new Coordinate(currCoor.getPosition_x(), currCoor.getPosition_y()+i*cellLength));
			}
			
		}
		
		List<NaviTrail> trails=generateTrails(coorList);
		
		naviTask = new AppendNaviTask(transportOrder.getNaviTaskID(),appendIndex, true,trails.get(trails.size()-1).getPathID(), 0.0,0.0,0.0, trails.size(), trails);
		return naviTask;
	}
	
	/**生成导航轨迹
	 * @param coorList
	 * @return
	 */
	private List<NaviTrail> generateTrails(List<Coordinate> coorList){
		Coordinate lastCoor=coorList.get(coorList.size()-1);// 最后一个点
		Coordinate last2Coor=coorList.get(coorList.size()-2);// 倒数第二个点
		// 如果最后一个点比倒数第二个点的坐标值大，那说明小车的最后一段move，是从某个边的起点到终点，那么，coorList要再加上小车停车时所在边的终点
		// 如果最后一个点比倒数第二个点的坐标值小，那说明小车的最后一段move，是从某个边的终点到起点,那么coorList已经包含了小车停车时所在边的终点，不需再做处理
		if(Math.pow(lastCoor.getPosition_x(), 2)+Math.pow(lastCoor.getPosition_y(), 2) >
		Math.pow(last2Coor.getPosition_x(), 2)+Math.pow(last2Coor.getPosition_y(), 2)){
			if(lastCoor.getPosition_y()==last2Coor.getPosition_y()){
				// 如果最后一个点与倒数第二个点在同一横线上，则将最后一个点右边的点加入到coorList
				coorList.add(new Coordinate(lastCoor.getPosition_x()+cellLength, lastCoor.getPosition_y()));
				
			}else if(lastCoor.getPosition_x()==last2Coor.getPosition_x()){
				// 如果最后一个点与倒数第二个点在同一竖线上，则将最后一个点上边的点加入到coorList
				coorList.add(new Coordinate(lastCoor.getPosition_x(), lastCoor.getPosition_y()+cellLength));
			}
		}
		List<NaviTrail> trails=new ArrayList<NaviTrail>();
		for(int i=0;i<coorList.size()-1;i++){
			trails.add(new NaviTrail(ReadMapUtil.getEdgeID(coorList.get(i), coorList.get(i+1)),false,false));
		}
		LOG.info("导航路线：{}",JSONObject.toJSONString(trails));
		return trails;
	}
	
	/**生成发送导航任务的实体
	 * @param transportOrder
	 * @return
	 */
	private SendNaviTask generateSendNaviTask(TransportOrder transportOrder){
		Coordinate destCoor = transportOrder.getDestCoor();
		if(destCoor==null){
			transportOrder.setState(TransportOrder.STATE.FAILED);
			LOG.error("导航目的地为空!");
			return null;
		}
		else if(destCoor.equals(vehicle.getPosition())){
			transportOrder.setState(TransportOrder.STATE.FAILED);
			LOG.error("导航目的为小车所在位置!");
			return null;
		}
		// 生成最简单的导航：先x方向移动，再y方向移动。
		SendNaviTask naviTask=null;
		Coordinate currCoor = vehicle.getPosition().clone();
		List<Coordinate> coorList = new ArrayList<Coordinate>();
		
		coorList.add(currCoor);
		Coordinate calcCurrCoor =currCoor.clone();
		// 首先将X方向上要经过的点加入到list
		for(int i=1;i<(Math.abs(destCoor.getPosition_x()-currCoor.getPosition_x()))/cellLength+1;i++){
			if(destCoor.getPosition_x()<currCoor.getPosition_x()){
				// 先判断小车当位置是不是在一个Node上，如不是，取X前进方向上最近的一个Node视为小车当前位置
				if(i==1&& !ReadMapUtil.checkCoorAtNode(currCoor)){
					Node nearestNode =ReadMapUtil.getNearestNode(currCoor,ReadMapUtil.MAPDIRECTION_LEFT);
					if(nearestNode ==null){
						throw new SimpleException("车辆无法向此方向移动！");
					}
					currCoor=nearestNode.getPosition();
					calcCurrCoor =currCoor.clone();
					
				}
				calcCurrCoor.setPosition_x(calcCurrCoor.getPosition_x()-cellLength);
//				coorList.add(new Coordinate(currCoor.getPosition_x()-i*cellLength, currCoor.getPosition_y()));
			}else{
				if(i==1&& !ReadMapUtil.checkCoorAtNode(currCoor)){
					Node nearestNode =ReadMapUtil.getNearestNode(currCoor,ReadMapUtil.MAPDIRECTION_RIGHT);
					if(nearestNode ==null){
						throw new SimpleException("车辆无法向此方向移动！");
					}
					currCoor=nearestNode.getPosition();
					calcCurrCoor =currCoor.clone();
				}
				calcCurrCoor.setPosition_x(calcCurrCoor.getPosition_x()+cellLength);
//				coorList.add(new Coordinate(currCoor.getPosition_x()+i*cellLength, currCoor.getPosition_y()));
			}
			coorList.add(calcCurrCoor.clone());
		}
		
		// 再将Y方向上要经过的点加入到list
		for(int i=1;i<(Math.abs(destCoor.getPosition_y()-currCoor.getPosition_y()))/cellLength+1;i++){
			if(destCoor.getPosition_y()<currCoor.getPosition_y()){
				// 先判断小车当位置是不是在一个Node上，如不是，取Y前进方向上最近的一个Node视为小车当前位置
				if(i==1&& !ReadMapUtil.checkCoorAtNode(currCoor)){
					Node nearestNode =ReadMapUtil.getNearestNode(currCoor,ReadMapUtil.MAPDIRECTION_BOTTOM);
					if(nearestNode ==null){
						throw new SimpleException("车辆无法向此方向移动！");
					}
					currCoor=nearestNode.getPosition();
					calcCurrCoor =currCoor.clone();
				}
				calcCurrCoor.setPosition_y(calcCurrCoor.getPosition_y()-cellLength);
//				coorList.add(new Coordinate(currCoor.getPosition_x(), currCoor.getPosition_y()-i*cellLength));
			}else{
				if(i==1&& !ReadMapUtil.checkCoorAtNode(currCoor)){
					Node nearestNode =ReadMapUtil.getNearestNode(currCoor,ReadMapUtil.MAPDIRECTION_TOP);
					if(nearestNode ==null){
						throw new SimpleException("车辆无法向此方向移动！");
					}
					currCoor=nearestNode.getPosition();
					calcCurrCoor =currCoor.clone();
				}
				calcCurrCoor.setPosition_y(calcCurrCoor.getPosition_y()+cellLength);
//				coorList.add(new Coordinate(currCoor.getPosition_x(), currCoor.getPosition_y()+i*cellLength));
			}
			coorList.add(calcCurrCoor.clone());
			
		}
		List<NaviTrail> trails=generateTrails(coorList);
		printNaviTrails(trails);
		naviTask = new SendNaviTask(transportOrder.getNaviTaskID(), 0, 0, 0, 0, false, trails.size(), trails);
		return naviTask;
	}
	private void printNaviTrails(List<NaviTrail> trails) {
		List<Integer> edgeIDs = trails.stream().map((trail)->{return trail.getPathID();}).collect(Collectors.toList());
		CommandDto command = new CommandDto();
		command.setCommandType(CommandDto.TYPE.printNaviTrails.name());
		command.setMessage(edgeIDs);
		webSocketMsgService.sendToAll(TopicAddressConstants.printNaviTrails,JSONObject.toJSONString(command));
	}
	/**根据移动格子数计算出终点
	 * @param startCoor
	 * @param cellNum
	 * @param direct
	 * @return
	 */
	private Coordinate calcDestCoorByCellNum(Coordinate startCoor,int cellNum,int direct){
		Coordinate destCoor= startCoor.clone();
		int mapDirection = 0;
		if(direct==1){// 车前进，X增加
			destCoor.setPosition_x(destCoor.getPosition_x()+cellLength*cellNum);
			mapDirection=ReadMapUtil.MAPDIRECTION_RIGHT;
		}else if(direct==2){ // 车后退X减少
			destCoor.setPosition_x(destCoor.getPosition_x()-cellLength*cellNum);
			mapDirection=ReadMapUtil.MAPDIRECTION_LEFT;
		}else if(direct==3){// 车向左，Y增加
			destCoor.setPosition_y(destCoor.getPosition_y()+cellLength*cellNum);
			mapDirection=ReadMapUtil.MAPDIRECTION_TOP;
		}else if(direct==4){ // 车向右，Y减少
			destCoor.setPosition_y(destCoor.getPosition_y()-cellLength*cellNum);
			mapDirection=ReadMapUtil.MAPDIRECTION_BOTTOM;
		}
		if(!ReadMapUtil.checkCoorAtNode(destCoor)){
			Node nearestNode =ReadMapUtil.getNearestNode(destCoor,mapDirection);
			if(nearestNode ==null){
				throw new SimpleException("目标地址超出了小车可行驶范围！");
			}
			destCoor=nearestNode.getPosition();
		}
		return destCoor;
	}
	private void calcDestCoorByNodeId(TransportOrder transportOrder){
		VehicleMoveDto vehicleMove = transportOrder.getVehicleMove();
		Coordinate destCoor = vehicleMove.getDestCoor();
		if(!ReadMapUtil.checkCoorAtNode(destCoor)){
			throw new SimpleException("目标地址必须为某个格子的坐标！");
		}
		transportOrder.setDestCoor(destCoor);
	}
	public boolean canDispath() {
		boolean b=vehicle.getNaviState()==NaviStateEnum.IDLE.getValue() &&
				(vehicle.getTransportOrder()==null || !vehicle.getTransportOrder().getState().equals(TransportOrder.STATE.EXECUTING)
				&&!vehicle.getTransportOrder().getState().equals(TransportOrder.STATE.PAUSE));
		return b;
	}

	public void dispatch(TransportOrder transportOrder) {
		try {
			if(transportOrder.getTransportOrderType().equals(TransportOrder.TYPE.moveWidthCells)){
				// 按格子数移动
				Coordinate destCoor =calcDestCoorByCellNum(vehicle.getPosition(), transportOrder.getVehicleMove().getCellNum(), transportOrder.getVehicleMove().getDirect());
				transportOrder.setDestCoor(destCoor);
				sendNaviTask(transportOrder);
			}
			else if(transportOrder.getTransportOrderType().equals(TransportOrder.TYPE.sendToNode)){
				// 发送到指定点
				calcDestCoorByNodeId(transportOrder);
				sendNaviTask(transportOrder);
			}
		} catch (Exception e) {
			transportOrder.setState(TransportOrder.STATE.FAILED);
			throw e;
		}
			

	}
/*	private void readPositionInfo(){
		PacketModel packetModel = new PacketModel();
		packetModel.setPacketSerialNo(PacketSerialNo.getSerialNo());
		packetModel.setErrorCode(LSConstants.ERROR_CODE_SUCCESS);
		packetModel.setPacketType(LSConstants.PACKET_TYPE_READVAR);
		ReadVariable<ReadItem_Request> readVariable = new ReadVariable<ReadItem_Request>(ReadItem_Request.class);
		readVariable.setItemNum(1);
		List<ReadItem_Request> items = new ArrayList<ReadItem_Request>();
		ReadItem_Request item =new ReadItem_Request(LSConstants.VARTYPE_POSITOIN, LSConstants.VARID_POSITOIN, 0, 0);
		items.add(item);
		readVariable.setItems(items);
		packetModel.setData_bytes(readVariable.toBytes());
		sendMsgToServer(packetModel);
	}*/

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

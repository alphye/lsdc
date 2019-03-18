package com.lishengzn.lsdc.agv.socket;

import com.alibaba.fastjson.JSONObject;
import com.lishengzn.common.constants.SocketConstants;
import com.lishengzn.lsdc.entity.Coordinate;
import com.lishengzn.common.entity.Vehicle;
import com.lishengzn.lsdc.enums.NaviStateEnum;
import com.lishengzn.common.packet.PacketModel;
import com.lishengzn.common.util.SocketUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

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
				return new Server(19204);
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
		ClientHandler clientHandler=null;
		while(true){
			Socket socket = getSocket();
			LOG.info("客户端连接：" + socket.getInetAddress().getHostAddress());
			if(clientHandler!=null){
				clientHandler.close();
			}
			clientHandler = new ClientHandler(socket);
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
	private static final Logger LOG = LoggerFactory.getLogger(ClientHandler_navi.class);
	private long naviId;
	private long operationId;
	private volatile boolean terminate;
	private Socket socket;
	long lastHeartBeatTime;
	private ExecutorService fixedThreadPool = Executors.newFixedThreadPool(8);
	/**
	 * 小车接下来要经过的点
	 */
	private Queue<Coordinate> vehiclePassingCoorQueue = new LinkedBlockingQueue<Coordinate>();

	public ClientHandler(Socket socket) {
		super();
		terminate = false;
		naviId=0L;
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
		new Thread(() -> listen2Server(socket),"listen2Server"+System.currentTimeMillis()).start();
		new Thread(() -> {
			handleHeartBeat(socket);
		},"handleHeartBeat"+System.currentTimeMillis()).start();
	}
	private void handleHeartBeat(Socket socket) {
		while(!isTerminate() && !socket.isClosed()){
			if(System.currentTimeMillis() - lastHeartBeatTime < 1000000000){
				continue;
			}
			// 走到这里，则说明超时未收到心跳
			LOG.error("超时未收到心跳，连接关闭！{}", System.currentTimeMillis() - lastHeartBeatTime);
			break;
		}
		close();
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


	
	private void listen2Server(Socket socket) {
		InputStream is = null;
		OutputStream out =null;
		try {
			is = socket.getInputStream();
			out = socket.getOutputStream();
			double x=1;
			double y=10;
			while (!isTerminate()) {
				PacketModel packetModel = null;
				// 如果能读取取数据包
				if (!((packetModel = SocketUtil.readNextPacketData(is)) == null)) {
					LOG.debug("收到客户端信息：{}", JSONObject.toJSONString(packetModel));
					if(packetModel.getPacketType()== SocketConstants.PACKET_TYPE_STATUS_API_ALL2){
						packetModel.setPacketType((short)(SocketConstants.RESPONSE_PACKET_ADDED+packetModel.getPacketType()));
						String packetData="{\"angle\": -0.0064,\"confidence\": 0.637,\"x\": "+x+++",\"y\": "+y+++",\"current_station\": \"LM1\"}";
						packetModel.setDataLength(packetData.getBytes().length);
						packetModel.setData(packetData);
						SocketUtil.sendPacketData(out,packetModel);
					}
					else if(packetModel.getPacketType()== SocketConstants.PACKET_TYPE_STATUS_API_BLOCK){
						packetModel.setPacketType((short)(SocketConstants.RESPONSE_PACKET_ADDED+packetModel.getPacketType()));
						packetModel.setData("{\"block_reason\": 3,\"block_x\": 0.637,\"block_y\": 3.5069,\"block_ultrasonic_id\": 0}");
						packetModel.setDataLength(packetModel.getData().getBytes().length);
						SocketUtil.sendPacketData(out,packetModel);
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			close();
		}
	}


	public void close() {
		LOG.info("socketHandler closed");
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		terminate = true;
		fixedThreadPool.shutdown();
	}

	public boolean isTerminate() {
		return terminate;
	}

	public void setTerminate(boolean terminate) {
		this.terminate = terminate;
	}
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		LOG.info("====clientHandler finalize:{}");
	}
}
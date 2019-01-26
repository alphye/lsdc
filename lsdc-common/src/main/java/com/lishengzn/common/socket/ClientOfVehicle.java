package com.lishengzn.lsdc.kernel.socket;

import com.lishengzn.common.communication.AbstractCommunicateAdapter;
import com.lishengzn.common.communication.service.MessageReceiveService;
import com.lishengzn.common.communication.service.MessageSenderService;
import com.lishengzn.common.entity.Vehicle;
import com.lishengzn.common.exception.SimpleException;
import com.lishengzn.common.packet.PacketModel;
import com.lishengzn.common.util.ByteUtil;
import com.lishengzn.common.util.SocketUtil;
import com.lishengzn.lsdc.kernel.pool.ObjectPool;
import com.lishengzn.lsdc.entity.Coordinate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;


public class ClientOfVehicle {
	private static final Logger LOG = LoggerFactory.getLogger(ClientOfVehicle.class);
	private Socket socket;
	private String ip;
	private int port;
	private ClientType clientType;
	private Vehicle vehicle;
	private AbstractCommunicateAdapter messageSenderService;
	private AbstractCommunicateAdapter messageReceiveService;

	public ClientOfVehicle(String ip, int port,AbstractCommunicateAdapter messageSenderService,AbstractCommunicateAdapter messageReceiveService,ClientType clientType) {
		this.ip = ip;
		this.port = port;
		this.messageSenderService = messageSenderService;
		this.messageReceiveService = messageReceiveService;
		this.clientType=clientType;
	}

	public void  initialize(){
		try {
			this.socket = new Socket(ip, port);
		} catch (IOException e) {
			throw new SimpleException("无法连接到车辆，请确认车辆已开启！");
		}
		this.vehicle=new Vehicle(new Coordinate(0,0));
		vehicle.setVehicleIp(ip);
		messageSenderService.setVehicle(vehicle);
		messageReceiveService.setVehicle(vehicle);
		messageSenderService.setSocket(socket);
		messageReceiveService.setSocket(socket);
		try {
			messageSenderService.initialize();
			messageReceiveService.initialize();
			LOG.info("connect to " + ip + ":" + port + "  successful");
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	public void close(){
		LOG.info("与车辆连接关闭,IP：{},clientType:{}",ip,clientType);
		try {
			if(socket!=null){
				socket.close();
			}
			messageSenderService.terminate();
			messageReceiveService.terminate();
			ObjectPool.removeClientOfVehicle(this.ip,clientType);
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

	public void sendTestMsg(short packetType,String msg){
		short seriaNo=1;
		PacketModel packetModel = new PacketModel(seriaNo,msg.getBytes().length,packetType,msg);
		System.out.println(ByteUtil.bytesToHex(packetModel.toBytes()));
		sendMsgToServer(packetModel);
	}




	public String getIp() {
		return ip;
	}

	public int getPort() {
		return port;
	}

	public Vehicle getVehicle() {
		return vehicle;
	}

	public MessageSenderService getMessageSenderService() {
		return (MessageSenderService) messageSenderService;
	}

	public MessageReceiveService getMessageReceiveService() {
		return (MessageReceiveService) messageReceiveService;
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		LOG.info("====client finalize:{},{}",ip,clientType);
	}

	public enum ClientType {
		statusAPi,vehicleControl,vehicleNavi,vehicleConfig,other;
	}
}

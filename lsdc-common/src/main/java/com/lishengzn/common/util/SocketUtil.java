package com.lishengzn.util;

import com.lishengzn.SocketConstants;
import com.lishengzn.constants.LSConstants;
import com.lishengzn.packet.PacketModel;
import com.lishengzn.packet.PacketSerialNo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
/** 通讯协议工具类
 *
 名称			内容	长度（byte）
 报文同步头		0x5A	1 (uint8)
 协议版本		1		1 (uint8) 均填 0x01
 序号					2 (uint16)
 数据区长度				4(uint32)
 报文类型 				2(uint16)
 保留区域				6(uint8[6])
 数据区		取决于头部中的数据区长度	JSON 序列化的数据内容
 **/
public class SocketUtil {

	private static short packetSerialNo=-1;
	private static final Logger LOG = LoggerFactory.getLogger(SocketUtil.class);

	/**
	 * 读取下一个数据包
	 * 
	 * @param in
	 *            输入流
	 * @return 从输入流解读出的下一个数据包
	 * @throws IOException
	 */
	public static PacketModel readNextPacketData(InputStream in) throws IOException {
		synchronized (in) {
			// PacketModel packetModel = new PacketModel();
			BufferedInputStream bis = new BufferedInputStream(in);
			byte syncHead[]=new byte[1];
			while(bis.read(syncHead)<0);
			if(syncHead[0]!= SocketConstants.SYNC_HEAD){
				return null;
			}
			byte[] head = new byte[15];// 包头内容（去除第一个字节syncHead）
			while (bis.read(head) < 0);
			// 底层包头，前4位，固定格式
			// 第一个字节是版本号,暂且不处理

			// 下标1~2，为序号
			short serialNo =ByteUtil.bytes2Short(Arrays.copyOfRange(head,1,3));
			// 下标3~6，数据长度
			byte packetLength_bytes[] = Arrays.copyOfRange(head, 3, 7);
			int dataLength = ByteUtil.bytesToInt(packetLength_bytes);
			byte[] dataPacket_bytes = new byte[dataLength];

			// 下标7~8，为报文类型
			short packetType=ByteUtil.bytes2Short(Arrays.copyOfRange(head,7,9));
			// 下标9~14 为 保留区域，暂且不处理
			while (bis.read(dataPacket_bytes) < 0);
			PacketModel packetModel = new PacketModel(serialNo,dataLength,packetType,new String(dataPacket_bytes, StandardCharsets.UTF_8));
			return packetModel ;
		}
	}


	/**发送 数据包
	 * @param out
	 * @param packetModel
	 * @throws IOException
	 */
	public static void sendPacketData(OutputStream out, PacketModel packetModel) {
		synchronized (out) {
			if(packetModel.getSerialNo()<0){
				packetModel.setSerialNo(getNextSerialNo());
			}
			byte[] sendBytes = packetModel.toBytes();
			BufferedOutputStream bos = new BufferedOutputStream(out);
			try {
				bos.write(sendBytes);
				bos.flush();
			} catch (IOException e) {
				LOG.error("发送指令异常！",e);
			}
		}
	}

	/**
	 * 根据包类型获取 应答包的报类型
	 * 
	 * @param packetType
	 * @return
	 */
	public static short getResponsePacketType(short packetType) {
		short responsePacketType = (short)(packetType | SocketConstants.RESPONSE_PACKET_ADDED);
		return responsePacketType;
	}

	public  static short getNextSerialNo(){
		synchronized(SocketUtil.class){
			return ++packetSerialNo;
		}
	}
}

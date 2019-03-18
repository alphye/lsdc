package com.lishengzn.common.util;

import com.google.common.primitives.Bytes;
import com.lishengzn.common.packet.PacketModel;
import com.lishengzn.common.constants.SocketConstants;
import com.lishengzn.common.packet.PacketSerialNo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
//			BufferedInputStream bis = new BufferedInputStream(in);
			byte syncHead[]=new byte[1];
			while(in.read(syncHead)<0 || syncHead[0]!= SocketConstants.SYNC_HEAD);

			byte[] head = new byte[15];// 包头内容（去除第一个字节syncHead）
			while (in.read(head) < 0);
			// 底层包头，前4位，固定格式
			// 第一个字节是版本号,暂且不处理

			// 下标1~2，为序号
			short serialNo =ByteUtil.bytes2Short(Arrays.copyOfRange(head,1,3));
			// 下标3~6，数据长度
			byte packetLength_bytes[] = Arrays.copyOfRange(head, 3, 7);
			int dataLength = ByteUtil.bytesToInt(packetLength_bytes);// 数据长度

			// 下标7~8，为报文类型
			short packetType=ByteUtil.bytes2Short(Arrays.copyOfRange(head,7,9));
			// 下标9~14 为 保留区域，暂且不处理

			int perBytesLen=1024;
			int remainedDataLength=dataLength;// 剩余未读取的长度
			int sumReadLength=0;// 累计读取到的长度
			List<Byte> dataByteList = new ArrayList<>();

			do {
				int nextReadLength = remainedDataLength > perBytesLen ? perBytesLen : remainedDataLength;// 本轮接下来要读取的长度
				byte currentReadBytes[] = new byte[nextReadLength];

				int currReadLen =in.read(currentReadBytes);// 本轮读取到的长度
				currReadLen = currReadLen > 0 ? currReadLen : 0;// 当小于0时，置为0，方便下边计算。
				remainedDataLength -= currReadLen;
				sumReadLength+=currReadLen;

				if (currReadLen > 0) {
					if(currReadLen<nextReadLength){
						// 当实际读取到的长度小于预先设定的读取长度，说明可能时到了包尾，读取到的byte尾端会有N个0。
						// 必须要把数组截取，去除尾端的脏数据
						currentReadBytes=Arrays.copyOf(currentReadBytes,currReadLen);
					}
					dataByteList.addAll(Bytes.asList(currentReadBytes));
				}

//				LOG.debug("=====nextReadLength:	{}	,currReadLength:	{}	,readBytesSize:	{}",nextReadLength,currReadLen,currentReadBytes.length);
			} while (remainedDataLength > 0);// 所有要读取的数据都 读取到了，结束本轮读取操作
//			LOG.debug("读取到数据,,dataLength：{},sumReadLength:{},listSize:{}",dataLength,sumReadLength,dataByteList.size());
			byte[] dataPacket_bytes = Bytes.toArray(dataByteList);
			return new PacketModel(serialNo,dataLength,packetType,new String(dataPacket_bytes, StandardCharsets.UTF_8));
		}
	}


	/**发送 数据包
	 * @param out
	 * @param packetModel
	 * @throws IOException
	 */
	public static void sendPacketData(OutputStream out, PacketModel packetModel) throws IOException {
		synchronized (out) {
			if(packetModel.getSerialNo()<=0){
				packetModel.setSerialNo(PacketSerialNo.getNextSerialNo());
			}
			byte[] sendBytes = packetModel.toBytes();
			out.write(sendBytes);
		}
	}

	/**
	 * 根据包类型获取 应答包的报类型
	 * 
	 * @param packetType
	 * @return
	 */
	public static short getResponsePacketType(short packetType) {
		short responsePacketType = (short)(packetType +SocketConstants.RESPONSE_PACKET_ADDED);
		return responsePacketType;
	}

}

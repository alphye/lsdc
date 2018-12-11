package com.lishengzn.util;

import com.lishengzn.packet.PacketModel;
import com.lishengzn.packet.PacketSerialNo;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

/*Nspd:[78, 115, 112, 100]*/
public class SocketUtil {
	/**
	 * int转byte[] 小 端
	 * 
	 * @param num
	 * @return
	 */
	public static byte[] intToBytes(int num) {
		byte[] bytes = new byte[4];
		bytes[0] = (byte) (0xff & (num >> 0));
		bytes[1] = (byte) (0xff & (num >> 8));
		bytes[2] = (byte) (0xff & (num >> 16));
		bytes[3] = (byte) (0xff & (num >> 24));
		return bytes;

	}

	/**
	 * byte[]转int 小端
	 * 
	 * @param bytes
	 * @return
	 */
	public static int bytesToInt(byte[] bytes) {
		int num = 0;
		int temp;
		temp = (0x000000ff & (bytes[0])) << 0;
		num = num | temp;

		temp = (0x000000ff & (bytes[1])) << 8;
		num = num | temp;

		temp = (0x000000ff & (bytes[2])) << 16;
		num = num | temp;

		temp = (0x000000ff & (bytes[3])) << 24;
		num = num | temp;

		return num;
	}

	/**
	 * int转byte[] 大端
	 * 
	 * @param a
	 * @return
	 */
	public static byte[] intToBytes_b(int a) {
		return new byte[] { (byte) ((a >> 24) & 0xFF), (byte) ((a >> 16) & 0xFF), (byte) ((a >> 8) & 0xFF),
				(byte) (a & 0xFF) };
	}

	/**
	 * byte[]转int 大端
	 * 
	 * @param src
	 * @return
	 */
	public static int bytesToInt_b(byte[] src) {
		int value;
		value = (int) (((src[0] & 0xFF) << 24) | ((src[1] & 0xFF) << 16) | ((src[2] & 0xFF) << 8) | (src[3] & 0xFF));
		return value;

	}

	/**
	 * int转byte[]的另一种实现方式，大端
	 * 
	 * @param x
	 * @return
	 */
	public static byte[] int2Bytes(int x) {
		ByteBuffer buffer = ByteBuffer.allocate(4);
		ByteOrder bo = ByteOrder.BIG_ENDIAN;
		buffer.order(bo);
		buffer.putInt(x);
		return buffer.array();
	}

	/**
	 * byte[]转int 的另一种实现方式 大端
	 * 
	 * @param src
	 * @param src
	 * @return
	 */
	public static int bytes2Int(byte[] src) {
		ByteBuffer buffer = ByteBuffer.wrap(src);
		buffer.order(ByteOrder.BIG_ENDIAN);
		return buffer.getInt();
	}

	/**
	 * double 转 byte[] 小端
	 * 
	 * @param d
	 * @return
	 */
	public static byte[] doubleToBytes_l(double d) {
		long value = Double.doubleToRawLongBits(d);
		byte[] byteRet = new byte[8];
		for (int i = 0; i < 8; i++) {
			byteRet[i] = (byte) ((value >> 8 * i) & 0xff);
		}
		return byteRet;

	}

	/**
	 * byte[] 转double 小端
	 * 
	 * @param arr
	 * @return
	 */
	public static double bytesToDouble_l(byte[] arr) {
		long value = 0;
		for (int i = 0; i < 8; i++) {
			value |= ((long) (arr[i] & 0xff)) << (8 * i);
		}
		return Double.longBitsToDouble(value);
	}

	/**
	 * double 转 byte[] 小端
	 * 
	 * @param d
	 * @return
	 */
	public static byte[] doubleToBytes(double d) {
		long longbits = Double.doubleToLongBits(d);
		return longToBytes(longbits);
	}

	/**
	 * byte[] 转double 小端
	 * 
	 * @param arr
	 * @return
	 */
	public static double bytesToDouble(byte[] arr) {
		return Double.longBitsToDouble(bytesToLong(arr));
	}
	/**
	 * double 转 byte[] 
	 * 
	 * @param d
	 * @return
	 */
	public static byte[] doubleToBytes(double d,ByteOrder byteOrder) {
		long longbits = Double.doubleToLongBits(d);
		return longToBytes(longbits,byteOrder);
	}

	/**
	 * byte[] 转double 
	 * 
	 * @param arr
	 * @return
	 */
	public static double bytesToDouble(byte[] arr,ByteOrder byteOrder) {
		return Double.longBitsToDouble(bytesToLong(arr,byteOrder));
	}
	// byte 数组与 long 的相互转换 大端
	/**
	 * long 转byte[] 小端
	 * 
	 * @param x
	 * @return
	 */
	public static byte[] longToBytes(long x) {
		ByteBuffer buffer = ByteBuffer.allocate(8);
		buffer.order(ByteOrder.LITTLE_ENDIAN);
		buffer.putLong(0, x);
		return buffer.array();
	}
	/**
	 * long 转byte[] 
	 * 
	 * @param x
	 * @return
	 */
	public static byte[] longToBytes(long x,ByteOrder byteOrder) {
		ByteBuffer buffer = ByteBuffer.allocate(8);
		buffer.order(byteOrder);
		buffer.putLong(0, x);
		return buffer.array();
	}

	/**
	 * byte[] 转long 小端
	 * 
	 * @param bytes
	 * @return
	 */
	public static long bytesToLong(byte[] bytes) {
		ByteBuffer buffer = ByteBuffer.wrap(bytes);
		buffer.order(ByteOrder.LITTLE_ENDIAN);
		buffer.put(bytes, 0, bytes.length);
		buffer.flip();// need flip
		return buffer.getLong();
	}
	/**
	 * byte[] 转long 
	 * 
	 * @param bytes
	 * @return
	 */
	public static long bytesToLong(byte[] bytes,ByteOrder byteOrder) {
		ByteBuffer buffer = ByteBuffer.wrap(bytes);
		buffer.order(byteOrder);
		buffer.put(bytes, 0, bytes.length);
		buffer.flip();// need flip
		return buffer.getLong();
	}
	/**
	 * 将16进制字符串转换为byte[]
	 * 
	 * @param str
	 * @return
	 */
	public static byte[] hexToBytes(String str) {
		if (str == null || str.trim().equals("")) {
			return new byte[0];
		}
		if (str.length() % 2 == 1) {
			// 奇数
			str = "0" + str;
		}

		byte[] bytes = new byte[str.length() / 2];
		for (int i = 0; i < str.length() / 2; i++) {
			String subStr = str.substring(i * 2, i * 2 + 2);
			bytes[i] = (byte) Integer.parseInt(subStr, 16);
		}

		return bytes;
	}

	/**
	 * byte[] to hex string
	 * 
	 * @param bytes
	 * @return
	 */
	public static String bytesToHex(byte[] bytes) {
		StringBuilder buf = new StringBuilder(bytes.length * 2);
		for (byte b : bytes) { // 使用String的format方法进行转换
			buf.append(String.format("%02x", new Integer(b & 0xff)));
		}

		return buf.toString();
	}
	
	public static boolean byteToBoolean(byte byt){
		boolean b = (byt == 0x00) ? false : true;
		return b;
	}
	
	public static byte booleanToByte(boolean b){
		byte byt = (byte) (b ? 0x01 : 0x00);
		return byt;
	}
	
	public static boolean bytesToBoolean(byte[] bytes){
		if(bytes.length>1){
			throw new RuntimeException("boolean 长度应为1");
		}
		return byteToBoolean(bytes[0]);
	}
	
	public static byte[] booleanToBytes(boolean b){
		return new byte[]{booleanToByte(b)};
	}

	/**将要发送的数据包装为带数据域包头的字节数组
	 * @param packetModel
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private static byte[] prependDataHead(PacketModel packetModel)
			throws UnsupportedEncodingException {
		byte packetType_bytes[] = intToBytes(packetModel.getPacketType());

		byte dataPacket_bytes[] = new byte[24 + packetModel.getData_bytes().length];

		// 在数据域包0-3位放入包序号
		int packetSeriaNo;
		if(packetModel.getPacketSerialNo()!=-1){
			packetSeriaNo=packetModel.getPacketSerialNo();
		}else{
			packetSeriaNo = PacketSerialNo.getSerialNo();
		}
		byte serialNo_bytes[] = intToBytes(packetSeriaNo);
		System.arraycopy(serialNo_bytes, 0, dataPacket_bytes, 0, 4);

		// 在数据域包4-7位放入包类型
		System.arraycopy(packetType_bytes, 0, dataPacket_bytes, 4, 4);

		// 在数据域包8-11位放入数据域长度
		byte dataLength_bytes[] = intToBytes(packetModel.getData_bytes().length);
		System.arraycopy(dataLength_bytes, 0, dataPacket_bytes, 8, 4);
		// 数据所域包第12-15位放入错误代码
		System.arraycopy(intToBytes(packetModel.getErrorCode()), 0, dataPacket_bytes, 12, 4);
		// 数据所域包第16-23位放入保留位，不需要向数组做任何操作

		// 至此数据域包中包头整理完毕，从第24位开始就是数据域
		System.arraycopy(packetModel.getData_bytes(), 0, dataPacket_bytes, 24, packetModel.getData_bytes().length);

		return dataPacket_bytes;

	}

	/**
	 * 生成底层包头并拼接到前面
	 * 
	 * @param dataPacket_bytes
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private static byte[] prependBaseHead(byte[] dataPacket_bytes) throws UnsupportedEncodingException {
		byte packet_bytes[] = new byte[8 + dataPacket_bytes.length];

		// 第0-3位放固定值Nspd
		System.arraycopy("Nspd".getBytes(), 0, packet_bytes, 0, 4);

		// 第4-7位放包的总长度
		System.arraycopy(intToBytes(dataPacket_bytes.length), 0, packet_bytes, 4, 4);

		// 第8位开始，放数据域包
		System.arraycopy(dataPacket_bytes, 0, packet_bytes, 8, dataPacket_bytes.length);

		return packet_bytes;
	}

	/**打包数据
	 * @param packetModel
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static byte[] packetMessage(PacketModel packetModel)
			throws UnsupportedEncodingException {
		byte dataPacket_bytes[] = prependDataHead(packetModel);
		byte packet_bytes[] = prependBaseHead(dataPacket_bytes);
		return packet_bytes;
	}

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
			byte[] head = new byte[8];
			while (bis.read(head) < 0)
				;
			// 底层包头，前4位，固定格式
			String Nspd = new String(Arrays.copyOf(head, 4));
			if (!"Nspd".equals(Nspd)) {
				return null;
			}
			// 底层包头，4-7位，数据域包长度
			byte packetLength_bytes[] = Arrays.copyOfRange(head, 4, 8);
			int packetLength = bytesToInt(packetLength_bytes);
			byte[] dataPacket_bytes = new byte[packetLength];
			if (bis.read(dataPacket_bytes) < 0) {
				return null;
			}
			return unPacketDataPacket(dataPacket_bytes);
		}
	}

	/**
	 * 解析数据域包
	 * 
	 * @param dataPacket_bytes
	 * @return
	 */
	public static PacketModel unPacketDataPacket(byte[] dataPacket_bytes) {
		PacketModel packetModel = new PacketModel();
		// 数据域包，前4位 包序号
		int packetSerialNo = bytesToInt(Arrays.copyOf(dataPacket_bytes, 4));
		packetModel.setPacketSerialNo(packetSerialNo);

		// 数据域包，4-7位，包类型
		int packetType = bytesToInt(Arrays.copyOfRange(dataPacket_bytes, 4, 8));
		packetModel.setPacketType(packetType);
		// 数据域包 8-11位,数据域长度
		int dataLength = bytesToInt(Arrays.copyOfRange(dataPacket_bytes, 8, 12));
		// 数据所域包第12-15位，错误代码
		int errorCode = bytesToInt(Arrays.copyOfRange(dataPacket_bytes, 12, 16));
		packetModel.setErrorCode(errorCode);
		if (dataPacket_bytes.length - dataLength != 24) {
			throw new RuntimeException("报文长度校验异常！");
		}
		// 数据包，24之后都是数据域
		byte data_bytes[] = Arrays.copyOfRange(dataPacket_bytes, 24, dataPacket_bytes.length);
		packetModel.setData_bytes(data_bytes);
		return packetModel;
	}

	/**发送 数据包
	 * @param out
	 * @param packetModel
	 * @throws IOException
	 */
	public static void sendPacketData(OutputStream out, PacketModel packetModel)
			throws IOException {
		synchronized (out) {
			byte[] sendBytes = SocketUtil.packetMessage(packetModel);
			BufferedOutputStream bos = new BufferedOutputStream(out);
			bos.write(sendBytes);
			bos.flush();
		}
	}

	/**
	 * 根据包类型获取 应答包的报类型
	 * 
	 * @param packetType
	 * @return
	 */
	public static int getResponsePacketType(int packetType) {
		int responsePacketType = packetType | LSConstants.PACKET_TYPE_ADDED;
		return responsePacketType;
	}


	public static void sendSimpleProtocol(OutputStream out, String command) throws IOException {
		synchronized (out) {
			byte[] sendBytes = hexToBytes(command);
			out.write(sendBytes);
		}
	}


	public static String readLineSimpleProtocol(InputStream in) throws IOException {
		String result="";
		BufferedReader bf = new BufferedReader(new InputStreamReader(in));
		result=bf.readLine();
		return result;
	}
	public static String readSimpleProtocol(InputStream in) throws IOException {
		String result="";
		byte[] bytes = new byte[2];
		while (in.read(bytes)<=0){}
		result=bytesToHex(bytes);
		return result;
	}
}

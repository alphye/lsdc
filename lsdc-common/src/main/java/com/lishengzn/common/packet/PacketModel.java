package com.lishengzn.common.packet;

import com.google.common.primitives.Bytes;
import com.lishengzn.common.util.ByteUtil;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class PacketModel {
	private byte syncHead=0x5A;
	private byte version=1;
	private short serialNo=-1;
	private int dataLength;
	private short packetType;
	private byte[] reserve=new byte[6];
	private String data;


	public PacketModel(short serialNo, int dataLength, short packetType, String data) {
		this.serialNo = serialNo;
		this.dataLength = dataLength;
		this.packetType = packetType;
		this.data = data;
	}
	public PacketModel(int dataLength, short packetType, String data) {
		this.dataLength = dataLength;
		this.packetType = packetType;
		this.data = data;
	}
	public PacketModel(short packetType,String data) {
		this.dataLength = data.getBytes(StandardCharsets.UTF_8).length;
		this.packetType = packetType;
		this.data = data;

	}

	public byte[] toBytes(){
		List<Byte> byteList=new ArrayList<Byte>();
		byteList.add(syncHead);
		byteList.add(version);

		byteList.addAll(Bytes.asList(ByteUtil.short2Bytes(serialNo)));
		byteList.addAll(Bytes.asList(ByteUtil.int2Bytes(dataLength)));
		byteList.addAll(Bytes.asList(ByteUtil.short2Bytes(packetType)));
		byteList.addAll(Bytes.asList(reserve));
		byteList.addAll(Bytes.asList(data.getBytes(StandardCharsets.UTF_8)));
		return Bytes.toArray(byteList);
	}

	public short getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(short serialNo) {
		this.serialNo = serialNo;
	}

	public int getDataLength() {
		return dataLength;
	}

	public void setDataLength(int dataLength) {
		this.dataLength = dataLength;
	}

	public short getPacketType() {
		return packetType;
	}

	public void setPacketType(short packetType) {
		this.packetType = packetType;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
}

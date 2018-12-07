package com.lishengzn.packet;

import java.util.Arrays;

public class PacketModel {
	private int packetSerialNo=-1;
	private int packetType;
	private int errorCode;
	private byte[] data_bytes;
	public int getPacketSerialNo() {
		return packetSerialNo;
	}
	public void setPacketSerialNo(int packetSerialNo) {
		this.packetSerialNo = packetSerialNo;
	}
	public int getPacketType() {
		return packetType;
	}
	public void setPacketType(int packetType) {
		this.packetType = packetType;
	}
	public byte[] getData_bytes() {
		return data_bytes;
	}
	public void setData_bytes(byte[] data_bytes) {
		this.data_bytes = data_bytes;
	}
	public int getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	@Override
	public String toString() {
		return "PacketModel [packetSerialNo=" + packetSerialNo + ", packetType=" + packetType + ", errorCode="
				+ errorCode + ", data_bytes=" + Arrays.toString(data_bytes) + "]";
	}
	
	
}

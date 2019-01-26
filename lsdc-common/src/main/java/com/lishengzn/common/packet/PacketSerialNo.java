package com.lishengzn.common.packet;

public  class PacketSerialNo {
	private static  Short serialNo=0;

	public synchronized static Short getNextSerialNo() {
		return serialNo++;
	}
	
}

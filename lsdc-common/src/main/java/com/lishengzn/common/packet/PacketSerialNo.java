package com.lishengzn.packet;

public  class PacketSerialNo {
	private static  Integer serialNo=1;

	public synchronized static Integer getSerialNo() {
		return serialNo++;
	}
	
}

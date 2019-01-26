package com.lishengzn.lsdc.entity;

public interface DataArea {
	byte[] toBytes();
	DataArea fromBytes(byte[] item_bytes);
}

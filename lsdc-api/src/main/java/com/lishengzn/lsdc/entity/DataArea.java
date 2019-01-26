package com.lishengzn.entity;

public interface DataArea {
	byte[] toBytes();
	DataArea fromBytes(byte[] item_bytes);
}

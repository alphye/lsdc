package com.lishengzn.entity.read.content;

import com.lishengzn.entity.DataArea;

public abstract class VariableContent implements DataArea{
	

	public abstract byte[] toBytes();
	

	public abstract VariableContent fromBytes(byte[] content_bytes);
}

package com.lishengzn.entity.read;

import com.lishengzn.entity.DataArea;

/**发起读取数据命令时，变量的实体
 * @author Administrator
 *
 */
public abstract class ReadItem implements DataArea {
	protected int varType;
	protected int varID;
	protected int varOffset;
	protected int varLength;
	
	
	public int getVarType() {
		return varType;
	}
	public void setVarType(int varType) {
		this.varType = varType;
	}
	public int getVarID() {
		return varID;
	}
	public void setVarID(int varID) {
		this.varID = varID;
	}
	public int getVarOffset() {
		return varOffset;
	}
	public void setVarOffset(int varOffset) {
		this.varOffset = varOffset;
	}
	public int getVarLength() {
		return varLength;
	}
	public void setVarLength(int varLength) {
		this.varLength = varLength;
	}
	public abstract byte[] toBytes();
	public abstract ReadItem fromBytes(byte[] item_bytes);
	
}

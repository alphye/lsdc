package com.lishengzn.entity.read;

import com.lishengzn.util.SocketUtil;

import java.util.Arrays;

/**发起读取数据命令时，变量的实体
 * @author Administrator
 *
 */
public class ReadItem_Request extends ReadItem {
	
	public ReadItem_Request(){}
	
	/**
	 * @param varType
	 * @param varID
	 * @param varOffset
	 * @param varLength
	 */
	public ReadItem_Request( int varType,int varID, int varOffset, int varLength) {
		super();
		this.varType = varType;
		this.varID = varID;
		this.varOffset = varOffset;
		this.varLength = varLength;
	}
	


	@Override
	public String toString() {
		return "ReadItem_Request [varType=" + varType + ", varID=" + varID + ", varOffset=" + varOffset + ", varLength="
				+ varLength + "]";
	}

	public byte[] toBytes(){
		byte[] item_bytes=new byte[16];
		
		byte[] varType_byte=SocketUtil.intToBytes(varType);
		byte[] varId_byte=SocketUtil.intToBytes(varID);
		byte[] varOffset_byte=SocketUtil.intToBytes(varOffset);
		byte[] varLength_byte=SocketUtil.intToBytes(varLength);
		System.arraycopy(varType_byte, 0, item_bytes, 0, varType_byte.length);
		System.arraycopy(varId_byte, 0, item_bytes, 4, varId_byte.length);
		System.arraycopy(varOffset_byte, 0, item_bytes, 8, varOffset_byte.length);
		System.arraycopy(varLength_byte, 0, item_bytes, 12, varLength_byte.length);
		
		return item_bytes;
	}
	
	
	public ReadItem_Request fromBytes(byte[] item_bytes){
		if(item_bytes.length<16){
			throw new RuntimeException("item_bytes 长度至少为16");
		}
		this.setVarType(SocketUtil.bytesToInt(Arrays.copyOfRange(item_bytes, 0, 4)));
		this.setVarID(SocketUtil.bytesToInt(Arrays.copyOfRange(item_bytes, 4, 8)));
		this.setVarOffset(SocketUtil.bytesToInt(Arrays.copyOfRange(item_bytes, 8, 12)));
		this.setVarLength(SocketUtil.bytesToInt(Arrays.copyOfRange(item_bytes, 12, 16)));
		return this;
	}
}

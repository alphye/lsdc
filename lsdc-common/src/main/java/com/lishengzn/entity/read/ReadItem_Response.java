package com.lishengzn.entity.read;

import com.lishengzn.entity.read.content.*;
import com.lishengzn.util.LSConstants;
import com.lishengzn.util.SocketUtil;

import java.util.Arrays;

/**发起读取数据命令时，变量的实体
 * @author Administrator
 *
 */
public class ReadItem_Response extends ReadItem {
	private VariableContent readContent;
	public ReadItem_Response(){
	}
	
	public ReadItem_Response(int varType, int varID, int varOffset, int varLength,VariableContent readContent) {
		super();
		this.varType = varType;
		this.varID = varID;
		this.varOffset = varOffset;
		this.varLength = varLength;
		this.readContent=readContent;
	}


	public VariableContent getReadContent() {
		return readContent;
	}

	public void setReadContent(VariableContent readContent) {
		this.readContent = readContent;
	}


	@Override
	public String toString() {
		return "ReadItem_Response [readContent=" + readContent + ", varType=" + varType + ", varID=" + varID
				+ ", varOffset=" + varOffset + ", varLength=" + varLength + "]";
	}

	public byte[] toBytes(){
		
		byte[] varType_bytes=SocketUtil.intToBytes(varType);
		byte[] varId_bytes=SocketUtil.intToBytes(varID);
		byte[] varOffset_bytes=SocketUtil.intToBytes(varOffset);
		byte[] varLength_bytes=SocketUtil.intToBytes(varLength);
		byte[] readContent_bytes =readContent==null ?new byte[0]:readContent.toBytes();
		byte[] item_bytes=new byte[varId_bytes.length+varType_bytes.length+varOffset_bytes.length+varLength_bytes.length+readContent_bytes.length];
		System.arraycopy(varType_bytes, 0, item_bytes, 0, varType_bytes.length);
		System.arraycopy(varId_bytes, 0, item_bytes, 4, varId_bytes.length);
		System.arraycopy(varOffset_bytes, 0, item_bytes, 8, varOffset_bytes.length);
		System.arraycopy(varLength_bytes, 0, item_bytes, 12, varLength_bytes.length);
		System.arraycopy(readContent_bytes, 0, item_bytes, 16, readContent_bytes.length);
		
		return item_bytes;
	}
	
	
	public ReadItem_Response fromBytes(byte[] item_bytes){
		if(item_bytes.length<16){
			throw new RuntimeException("item_bytes 长度至少为16");
		}
		this.setVarType(SocketUtil.bytesToInt(Arrays.copyOfRange(item_bytes, 0, 4)));
		this.setVarID(SocketUtil.bytesToInt(Arrays.copyOfRange(item_bytes, 4, 8)));
		this.setVarOffset(SocketUtil.bytesToInt(Arrays.copyOfRange(item_bytes, 8, 12)));
		this.setVarLength(SocketUtil.bytesToInt(Arrays.copyOfRange(item_bytes, 12, 16)));
		
		VariableContent subVar=null;
		if(this.varID==LSConstants.VARID_POSITOIN){
			subVar=new PositionContent();
		}else if(this.varID==LSConstants.VARID_VELOCITY){
			subVar=new VelocityContent();
		}else if(this.varID==LSConstants.VARID_CHARGESTATE){
			subVar=new ChargeStateContent();
		}else if(this.varID==LSConstants.VARID_CHECKPACKAGE){
			subVar=new CheckPackageContent();
		}else if(this.varID==LSConstants.VARID_FLIP_STATE){
			subVar=new FlipStateContent();
		}else if(this.varID==LSConstants.VARID_JACKING_DISTANCE){
			subVar=new JackingDistanceContent();
		}else if(this.varID==LSConstants.VARID_BELT_ROTATION_STATE){
			subVar=new BeltRotationStateContent();
		}else if(this.varID==LSConstants.VARID_OPERATION_STATE){
			subVar=new OperationStateContent();
		}else if(this.varID==LSConstants.VARID_BATTERYCAPACITY){
			subVar=new BatteryCapacityContent();
		}else{
			throw new RuntimeException("读取变量，变量ID不在有效范围内:"+Integer.toHexString(this.varID));
		}
		
		
		if(subVar!=null){
			this.setReadContent(subVar.fromBytes(Arrays.copyOfRange(item_bytes, 16, item_bytes.length)));
		}
		return this;
	}

}

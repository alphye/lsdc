package com.lishengzn.entity.write;

import com.lishengzn.entity.read.ReadItem;
import com.lishengzn.entity.read.content.VariableContent;
import com.lishengzn.util.LSConstants;
import com.lishengzn.util.SocketUtil;

import java.util.Arrays;

/**发起写变量命令时，变量的实体
 * @author Administrator
 *
 */
public class WriteItem extends ReadItem {
	private VariableContent writeContent;
	public WriteItem(){
	}
	
	public WriteItem(int varType, int varID, int varOffset, int varLength,VariableContent writeContent) {
		super();
		this.varType = varType;
		this.varID = varID;
		this.varOffset = varOffset;
		this.varLength = varLength;
		this.writeContent=writeContent;
	}


	public VariableContent getWriteContent() {
		return writeContent;
	}

	public void setWriteContent(VariableContent writeContent) {
		this.writeContent = writeContent;
	}


	@Override
	public String toString() {
		return "WriteItem [writeContent=" + writeContent + ", varType=" + varType + ", varID=" + varID
				+ ", varOffset=" + varOffset + ", varLength=" + varLength + "]";
	}

	public byte[] toBytes(){
		
		byte[] varType_bytes=SocketUtil.intToBytes(varType);
		byte[] varId_bytes=SocketUtil.intToBytes(varID);
		byte[] varOffset_bytes=SocketUtil.intToBytes(varOffset);
		byte[] varLength_bytes=SocketUtil.intToBytes(varLength);
		byte[] writeContent_bytes =writeContent==null ?new byte[0]:writeContent.toBytes();
		byte[] item_bytes=new byte[varId_bytes.length+varType_bytes.length+varOffset_bytes.length+varLength_bytes.length+writeContent_bytes.length];
		System.arraycopy(varType_bytes, 0, item_bytes, 0, varType_bytes.length);
		System.arraycopy(varId_bytes, 0, item_bytes, 4, varId_bytes.length);
		System.arraycopy(varOffset_bytes, 0, item_bytes, 8, varOffset_bytes.length);
		System.arraycopy(varLength_bytes, 0, item_bytes, 12, varLength_bytes.length);
		System.arraycopy(writeContent_bytes, 0, item_bytes, 16, writeContent_bytes.length);
		
		return item_bytes;
	}
	
	
	public WriteItem fromBytes(byte[] item_bytes){
		if(item_bytes.length<16){
			throw new RuntimeException("item_bytes 长度至少为16");
		}
		this.setVarType(SocketUtil.bytesToInt(Arrays.copyOfRange(item_bytes, 0, 4)));
		this.setVarID(SocketUtil.bytesToInt(Arrays.copyOfRange(item_bytes, 4, 8)));
		this.setVarOffset(SocketUtil.bytesToInt(Arrays.copyOfRange(item_bytes, 8, 12)));
		this.setVarLength(SocketUtil.bytesToInt(Arrays.copyOfRange(item_bytes, 12, 16)));
		
		VariableContent content=null;
		if(this.varID==LSConstants.VARID_STOPCHARGE){
			content=new ControlChargeContent().fromBytes(Arrays.copyOfRange(item_bytes, 16, item_bytes.length));
		}
		else if (this.varID==LSConstants.VARID_PACKAGESIZE){
			content=new PackageSize().fromBytes(Arrays.copyOfRange(item_bytes, 16, item_bytes.length));
		}
		else{
		}
		this.setWriteContent(content);
		return this;
	}

}

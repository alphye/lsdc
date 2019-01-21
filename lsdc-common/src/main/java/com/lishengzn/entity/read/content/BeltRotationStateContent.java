package com.lishengzn.entity.read.content;

import com.google.common.primitives.Bytes;
import com.lishengzn.util.SocketUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**提供皮带转动状态
 * @author Administrator
 *
 */
public class BeltRotationStateContent extends VariableContent {

	private long operationId;
	/**
	 * 皮带转动状态
	 */
	private int beltRotationState;

	public BeltRotationStateContent() {
	}

	public BeltRotationStateContent(long operationId,int beltRotationState) {
		super();
		this.operationId=operationId;
		this.beltRotationState = beltRotationState;
	}

	public long getOperationId() {
		return operationId;
	}

	public void setOperationId(long operationId) {
		this.operationId = operationId;
	}

	public int getBeltRotationState() {
		return beltRotationState;
	}

	public void setBeltRotationState(int beltRotationState) {
		this.beltRotationState = beltRotationState;
	}

	@Override
	public byte[] toBytes() {
		List<Byte> byteList=new ArrayList<Byte>();
		byteList.addAll(Bytes.asList(SocketUtil.longToBytes(operationId)));
		byteList.addAll(Bytes.asList(SocketUtil.intToBytes(beltRotationState)));
		return Bytes.toArray(byteList);
	}

	@Override
	public BeltRotationStateContent fromBytes(byte[] subVar_bytes) {
		if (subVar_bytes.length != 12) {
			throw new RuntimeException("“提供皮带转动状态”变量内容字节数须为12");
		}
		this.setOperationId(SocketUtil.bytesToLong(Arrays.copyOfRange(subVar_bytes, 0, 8)));
		this.setBeltRotationState(SocketUtil.bytesToInt(Arrays.copyOfRange(subVar_bytes, 8, 12)));
		return this;
	}
}

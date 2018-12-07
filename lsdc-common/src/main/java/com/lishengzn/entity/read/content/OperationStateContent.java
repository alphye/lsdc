package com.lishengzn.entity.read.content;

import com.lishengzn.util.SocketUtil;

import java.util.Arrays;

/**提供操作状态
 * @author Administrator
 *
 */
public class OperationStateContent extends VariableContent {
	/**
	 * 操作状态
	 */
	private int operationState;

	public OperationStateContent() {
	}



	public OperationStateContent(int operationState) {
		super();
		this.operationState = operationState;
	}



	public int getOperationState() {
		return operationState;
	}



	public void setOperationState(int operationState) {
		this.operationState = operationState;
	}



	@Override
	public byte[] toBytes() {
		byte[] operationState_byte = SocketUtil.intToBytes(operationState);

		byte[] subVar_bytes = new byte[operationState_byte.length];
		System.arraycopy(operationState_byte, 0, subVar_bytes, 0, operationState_byte.length);

		return subVar_bytes;
	}

	@Override
	public OperationStateContent fromBytes(byte[] subVar_bytes) {
		if (subVar_bytes.length != 4) {
			throw new RuntimeException("“提供操作状态”变量内容字节数须为4");
		}
		this.setOperationState(SocketUtil.bytesToInt(Arrays.copyOfRange(subVar_bytes, 0, 4)));
		return this;
	}

}

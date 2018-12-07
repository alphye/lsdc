package com.lishengzn.entity.read.content;

import com.lishengzn.util.SocketUtil;

import java.util.Arrays;

/**上报状态
 * @author Administrator
 *
 */
public class UpdateStateContent extends VariableContent {
	
	private int stateType;
	/**
	 * 状态
	 */
	private int state;

	public UpdateStateContent() {
	}

	public int getStateType() {
		return stateType;
	}

	public void setStateType(int stateType) {
		this.stateType = stateType;
	}


	public UpdateStateContent(int stateType, int state) {
		super();
		this.stateType = stateType;
		this.state = state;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	@Override
	public byte[] toBytes() {
		byte[] state_byte = SocketUtil.intToBytes(state);
		byte[] stateType_byte = SocketUtil.intToBytes(stateType);

		byte[] subVar_bytes = new byte[state_byte.length+stateType_byte.length];
		System.arraycopy(stateType_byte, 0, subVar_bytes, 0, stateType_byte.length);
		System.arraycopy(state_byte, 0, subVar_bytes, 4, state_byte.length);

		return subVar_bytes;
	}

	@Override
	public UpdateStateContent fromBytes(byte[] subVar_bytes) {
		if (subVar_bytes.length != 8) {
			throw new RuntimeException("上报状态 数据长度须为8");
		}
		this.setStateType(SocketUtil.bytesToInt(Arrays.copyOfRange(subVar_bytes, 0, 4)));
		this.setState(SocketUtil.bytesToInt(Arrays.copyOfRange(subVar_bytes, 4, 8)));
		return this;
	}

}

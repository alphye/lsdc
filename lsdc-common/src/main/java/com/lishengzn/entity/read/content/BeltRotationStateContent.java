package com.lishengzn.entity.read.content;

import com.lishengzn.util.SocketUtil;

import java.util.Arrays;

/**提供皮带转动状态
 * @author Administrator
 *
 */
public class BeltRotationStateContent extends VariableContent {
	/**
	 * 皮带转动状态
	 */
	private int beltRotationState;

	public BeltRotationStateContent() {
	}

	public BeltRotationStateContent(int beltRotationState) {
		super();
		this.beltRotationState = beltRotationState;
	}

	public int getBeltRotationState() {
		return beltRotationState;
	}

	public void setBeltRotationState(int beltRotationState) {
		this.beltRotationState = beltRotationState;
	}

	@Override
	public byte[] toBytes() {
		byte[] beltRotationState_byte = SocketUtil.intToBytes(beltRotationState);

		byte[] subVar_bytes = new byte[beltRotationState_byte.length];
		System.arraycopy(beltRotationState_byte, 0, subVar_bytes, 0, beltRotationState_byte.length);

		return subVar_bytes;
	}

	@Override
	public BeltRotationStateContent fromBytes(byte[] subVar_bytes) {
		if (subVar_bytes.length != 4) {
			throw new RuntimeException("“提供皮带转动状态”变量内容字节数须为4");
		}
		this.setBeltRotationState(SocketUtil.bytesToInt(Arrays.copyOfRange(subVar_bytes, 0, 4)));
		return this;
	}

	public enum State{
		/**
		 * 正在转动 1
		 */
		ROTATING(1,"正在转动"),
		/**
		 * 停止转动 0
		 */ 
		STOPPED(0,"停止转动");
		private int value;
		private String description;
		private State(int value, String description) {
			this.value = value;
			this.description = description;
		}
		public int getValue() {
			return value;
		}
		public String getDescription() {
			return description;
		}
		
	}
}

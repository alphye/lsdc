package com.lishengzn.entity.read.content;

import com.lishengzn.util.SocketUtil;

import java.util.Arrays;

/**AGV本体是通过翻盖来卸包裹，提供翻盖状态
 * @author Administrator
 *
 */
public class FlipStateContent extends VariableContent {
	/**
	 * 翻盖状态
	 */
	private int flipState;

	public FlipStateContent() {
	}

	


	public FlipStateContent(int flipState) {
		super();
		this.flipState = flipState;
	}




	public int getFlipState() {
		return flipState;
	}




	public void setFlipState(int flipState) {
		this.flipState = flipState;
	}




	@Override
	public byte[] toBytes() {
		byte[] flipState_byte = SocketUtil.intToBytes(flipState);

		byte[] subVar_bytes = new byte[flipState_byte.length];
		System.arraycopy(flipState_byte, 0, subVar_bytes, 0, flipState_byte.length);

		return subVar_bytes;
	}

	@Override
	public FlipStateContent fromBytes(byte[] subVar_bytes) {
		if (subVar_bytes.length != 4) {
			throw new RuntimeException("“检测翻盖状态”变量内容字节数须为4");
		}
		this.setFlipState(SocketUtil.bytesToInt(Arrays.copyOfRange(subVar_bytes, 0, 4)));
		return this;
	}

	public enum State{
		/**
		 * 正在翻盖 0
		 */
		FLIPING(0,"正在翻盖"),
		/**
		 * 翻盖到最大角度 1
		 */
		FINISHEDFLIP(1,"翻盖到最大角度"),
		/**
		 * 正在收盖 2
		 */
		RETRACTING(2,"正在收盖"),
		/**
		 * 收盖完成 3
		 */
		FINISHEDRETRACT(3,"收盖完成");
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

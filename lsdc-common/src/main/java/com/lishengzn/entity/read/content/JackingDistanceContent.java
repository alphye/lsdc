package com.lishengzn.entity.read.content;

import com.lishengzn.util.SocketUtil;

import java.util.Arrays;

/**
 * 提供当前顶升距离
 * 
 * @author Administrator
 *
 */
public class JackingDistanceContent extends VariableContent {
	/**
	 * 顶升距离
	 */
	private int jackingDistance;

	public JackingDistanceContent() {
	}

	public JackingDistanceContent(int jackingDistance) {
		super();
		this.jackingDistance = jackingDistance;
	}

	public int getJackingDistance() {
		return jackingDistance;
	}

	public void setJackingDistance(int jackingDistance) {
		this.jackingDistance = jackingDistance;
	}

	@Override
	public byte[] toBytes() {
		byte[] jackingDistance_byte = SocketUtil.intToBytes(jackingDistance);

		byte[] subVar_bytes = new byte[jackingDistance_byte.length];
		System.arraycopy(jackingDistance_byte, 0, subVar_bytes, 0, jackingDistance_byte.length);

		return subVar_bytes;
	}

	@Override
	public JackingDistanceContent fromBytes(byte[] subVar_bytes) {
		if (subVar_bytes.length != 4) {
			throw new RuntimeException("“提供当前顶升距离”变量内容字节数须为4");
		}
		this.setJackingDistance(SocketUtil.bytesToInt(Arrays.copyOfRange(subVar_bytes, 0, 4)));
		return this;
	}

}

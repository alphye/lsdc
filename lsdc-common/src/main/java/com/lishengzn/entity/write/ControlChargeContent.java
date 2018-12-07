package com.lishengzn.entity.write;

import com.lishengzn.entity.read.content.VariableContent;
import com.lishengzn.util.SocketUtil;

import java.util.Arrays;

/**控制充电
 * @author Administrator
 *
 */
public class ControlChargeContent extends VariableContent {
	/**
	 * 控制充电代码2--开始充电 0--停止充电
	 */
	private int controlChargeCode;

	public ControlChargeContent() {
	}



	public ControlChargeContent(int controlChargeCode) {
		super();
		this.controlChargeCode = controlChargeCode;
	}



	public int getControlChargeCode() {
		return controlChargeCode;
	}



	public void setControlChargeCode(int controlChargeCode) {
		this.controlChargeCode = controlChargeCode;
	}



	@Override
	public byte[] toBytes() {
		byte[] controlChargeCode_byte = SocketUtil.intToBytes(controlChargeCode);

		byte[] data_bytes = new byte[controlChargeCode_byte.length];
		System.arraycopy(controlChargeCode_byte, 0, data_bytes, 0, controlChargeCode_byte.length);

		return data_bytes;
	}

	@Override
	public ControlChargeContent fromBytes(byte[] data_bytes) {
		if (data_bytes.length != 4) {
			throw new RuntimeException("“控制充电”变量内容字节数须为4");
		}
		this.setControlChargeCode(SocketUtil.bytesToInt(Arrays.copyOfRange(data_bytes, 0, 4)));
		return this;
	}

}

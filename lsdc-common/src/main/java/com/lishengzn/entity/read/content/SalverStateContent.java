package com.lishengzn.entity.read.content;

import com.google.common.primitives.Bytes;
import com.lishengzn.util.SocketUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**托盘状态
 * @author Administrator
 *
 */
public class SalverStateContent extends VariableContent {


	/**
	 * 货柜与车正前方的夹角（弧度）
	 */
	private double containerAngle;
	/**
	 * 托盘状态
	 */
	private int salverState;

	public SalverStateContent() {
	}

	public SalverStateContent(double containerAngle, int salverState) {
		super();
		this.containerAngle=containerAngle;
		this.salverState = salverState;
	}

	public double getContainerAngle() {
		return containerAngle;
	}

	public void setContainerAngle(double containerAngle) {
		this.containerAngle = containerAngle;
	}

	public int getSalverState() {
		return salverState;
	}

	public void setSalverState(int salverState) {
		this.salverState = salverState;
	}

	@Override
	public byte[] toBytes() {
		List<Byte> byteList=new ArrayList<Byte>();
		byteList.addAll(Bytes.asList(SocketUtil.doubleToBytes(containerAngle)));
		byteList.addAll(Bytes.asList(SocketUtil.intToBytes(salverState)));
		return Bytes.toArray(byteList);
	}

	@Override
	public SalverStateContent fromBytes(byte[] subVar_bytes) {
		if (subVar_bytes.length != 12) {
			throw new RuntimeException("“读取货柜状态”变量内容字节数须为12");
		}
		this.setContainerAngle(SocketUtil.bytesToDouble(Arrays.copyOfRange(subVar_bytes, 0, 8)));
		this.setSalverState(SocketUtil.bytesToInt(Arrays.copyOfRange(subVar_bytes, 8, 12)));
		return this;
	}

}

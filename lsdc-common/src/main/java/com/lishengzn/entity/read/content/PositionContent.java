package com.lishengzn.entity.read.content;

import com.google.common.primitives.Bytes;
import com.lishengzn.util.SocketUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**读取当前导航状态及位置信息
 * @author Administrator
 *
 */
public class PositionContent extends VariableContent {
	private long naviId;
	/**
	 * x坐标
	 */
	private double position_x;
	/**
	 * y坐标
	 */
	private double position_y;
	/**
	 * 角度
	 */
	private double angle;
	/**
	 * 置信度
	 */
	private int confidenceDegree;
	/**
	 * 所在的边ID
	 */
	private int pathId;
	/**
	 * 导航状态
	 */
	private int naviState;

	
	public PositionContent() {
	}


	public PositionContent(long naviId, double position_x, double position_y, double angle, int confidenceDegree, int pathId, int naviState) {
		this.naviId = naviId;
		this.position_x = position_x;
		this.position_y = position_y;
		this.angle = angle;
		this.confidenceDegree = confidenceDegree;
		this.pathId = pathId;
		this.naviState = naviState;
	}

	public long getNaviId() {
		return naviId;
	}

	public void setNaviId(long naviId) {
		this.naviId = naviId;
	}

	public double getPosition_x() {
		return position_x;
	}

	public void setPosition_x(double position_x) {
		this.position_x = position_x;
	}

	public double getPosition_y() {
		return position_y;
	}

	public void setPosition_y(double position_y) {
		this.position_y = position_y;
	}

	public double getAngle() {
		return angle;
	}

	public void setAngle(double angle) {
		this.angle = angle;
	}

	public int getConfidenceDegree() {
		return this.confidenceDegree;
	}


	public void setConfidenceDegree(int confidenceDegree) {
		this.confidenceDegree = confidenceDegree;
	}


	public int getPathId() {
		return pathId;
	}

	public void setPathId(int pathId) {
		this.pathId = pathId;
	}

	public int getNaviState() {
		return naviState;
	}

	public void setNaviState(int naviState) {
		this.naviState = naviState;
	}

	@Override
	public String toString() {
		return "PositionContent [position_x=" + position_x + ", position_y=" + position_y + ", angle=" + angle
				+ ", confidenceDegree=" + confidenceDegree + ", pathId=" + pathId + ", naviState=" + naviState + "]";
	}


	@Override
	public byte[] toBytes() {
		List<Byte> byteList=new ArrayList<Byte>();
		byteList.addAll(Bytes.asList(SocketUtil.longToBytes(naviId)));
		byteList.addAll(Bytes.asList(SocketUtil.doubleToBytes(position_x)));
		byteList.addAll(Bytes.asList(SocketUtil.doubleToBytes(position_y)));
		byteList.addAll(Bytes.asList(SocketUtil.doubleToBytes(angle)));
		byteList.addAll(Bytes.asList(SocketUtil.intToBytes(confidenceDegree)));
		byteList.addAll(Bytes.asList(SocketUtil.intToBytes(pathId)));
		byteList.addAll(Bytes.asList(SocketUtil.intToBytes(naviState)));
		return Bytes.toArray(byteList);
	}

	@Override
	public PositionContent fromBytes(byte[] subVar_bytes) {
		if(subVar_bytes.length!=44){
			throw new RuntimeException("“位置及导航信息”变量内容字节数须为44");
		}
		this.setNaviId(SocketUtil.bytesToLong(Arrays.copyOfRange(subVar_bytes, 0, 8)));
		this.setPosition_x(SocketUtil.bytesToDouble(Arrays.copyOfRange(subVar_bytes, 8, 16)));
		this.setPosition_y(SocketUtil.bytesToDouble(Arrays.copyOfRange(subVar_bytes, 16, 24)));
		this.setAngle(SocketUtil.bytesToDouble(Arrays.copyOfRange(subVar_bytes, 24, 32)));
		this.setConfidenceDegree(SocketUtil.bytesToInt(Arrays.copyOfRange(subVar_bytes, 32, 36)));
		this.setPathId(SocketUtil.bytesToInt(Arrays.copyOfRange(subVar_bytes, 36, 40)));
		this.setNaviState(SocketUtil.bytesToInt(Arrays.copyOfRange(subVar_bytes, 40, 44)));
		return this;
	}

}

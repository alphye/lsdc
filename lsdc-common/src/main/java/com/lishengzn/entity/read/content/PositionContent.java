package com.lishengzn.entity.read.content;

import com.lishengzn.util.SocketUtil;

import java.util.Arrays;

/**读取当前导航状态及位置信息
 * @author Administrator
 *
 */
public class PositionContent extends VariableContent {
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
	

	/**
	 * @param position_x
	 * @param position_y
	 * @param angle
	 * @param confidenceDegree
	 * @param pathId
	 * @param naviState
	 */
	public PositionContent(double position_x, double position_y, double angle, int confidenceDegree, int pathId,
			int naviState) {
		this.position_x = position_x;
		this.position_y = position_y;
		this.angle = angle;
		this.confidenceDegree = confidenceDegree;
		this.pathId = pathId;
		this.naviState = naviState;
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

		byte[] position_x_byte = SocketUtil.doubleToBytes(position_x);
		byte[] position_y_byte = SocketUtil.doubleToBytes(position_y);
		byte[] angle_byte = SocketUtil.doubleToBytes(angle);
		byte[] confidenceDegree_byte = SocketUtil.intToBytes(confidenceDegree);
		byte[] pathId_byte = SocketUtil.intToBytes(pathId);
		byte[] naviState_byte = SocketUtil.intToBytes(naviState);
		byte[] subVar_bytes = new byte[position_x_byte.length+position_y_byte.length+angle_byte.length+confidenceDegree_byte.length+pathId_byte.length+naviState_byte.length];
		System.arraycopy(position_x_byte, 0, subVar_bytes, 0, position_x_byte.length);
		System.arraycopy(position_y_byte, 0, subVar_bytes, 8, position_y_byte.length);
		System.arraycopy(angle_byte, 0, subVar_bytes, 16, angle_byte.length);
		System.arraycopy(confidenceDegree_byte, 0, subVar_bytes, 24, confidenceDegree_byte.length);
		System.arraycopy(pathId_byte, 0, subVar_bytes, 28, pathId_byte.length);
		System.arraycopy(naviState_byte, 0, subVar_bytes, 32, naviState_byte.length);

		return subVar_bytes;
	}

	@Override
	public PositionContent fromBytes(byte[] subVar_bytes) {
		if(subVar_bytes.length!=36){
			throw new RuntimeException("“位置及导航信息”变量内容字节数须为36");
		}
		this.setPosition_x(SocketUtil.bytesToDouble(Arrays.copyOfRange(subVar_bytes, 0, 8)));
		this.setPosition_y(SocketUtil.bytesToDouble(Arrays.copyOfRange(subVar_bytes, 8, 16)));
		this.setAngle(SocketUtil.bytesToDouble(Arrays.copyOfRange(subVar_bytes, 16, 24)));
		this.setConfidenceDegree(SocketUtil.bytesToInt(Arrays.copyOfRange(subVar_bytes, 24, 28)));
		this.setPathId(SocketUtil.bytesToInt(Arrays.copyOfRange(subVar_bytes, 28, 32)));
		this.setNaviState(SocketUtil.bytesToInt(Arrays.copyOfRange(subVar_bytes, 32, subVar_bytes.length)));
		return this;
	}

}

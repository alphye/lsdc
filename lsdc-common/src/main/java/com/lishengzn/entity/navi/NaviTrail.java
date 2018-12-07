package com.lishengzn.entity.navi;

import com.lishengzn.entity.DataArea;
import com.lishengzn.util.SocketUtil;

import java.util.Arrays;

public class NaviTrail implements DataArea{

	/**
	 * 边ID
	 */
	private int pathID;
	/**
	 * 是否从边启动move到终点
	 */
	private boolean moveToPathEnd;
	/**
	 * 是否倒车move
	 */
	private boolean reversingMove;
	
	public NaviTrail(){}
	public NaviTrail(int pathID, boolean moveToPathEnd, boolean reversingMove) {
		super();
		this.pathID = pathID;
		this.moveToPathEnd = moveToPathEnd;
		this.reversingMove = reversingMove;
	}

	public int getPathID() {
		return pathID;
	}

	public void setPathID(int pathID) {
		this.pathID = pathID;
	}

	public boolean isMoveToPathEnd() {
		return moveToPathEnd;
	}

	public void setMoveToPathEnd(boolean moveToPathEnd) {
		this.moveToPathEnd = moveToPathEnd;
	}

	public boolean isReversingMove() {
		return reversingMove;
	}

	public void setReversingMove(boolean reversingMove) {
		this.reversingMove = reversingMove;
	}

	@Override
	public byte[] toBytes() {
		byte[] bytes = new byte[6];
		byte[] pathID_bytes = SocketUtil.intToBytes(pathID);
		byte[] moveToPathEnd_bytes = SocketUtil.booleanToBytes(moveToPathEnd);
		byte[] reversingMove_bytes = SocketUtil.booleanToBytes(reversingMove);
		System.arraycopy(pathID_bytes, 0, bytes, 0, pathID_bytes.length);
		System.arraycopy(moveToPathEnd_bytes, 0, bytes, 4, moveToPathEnd_bytes.length);
		System.arraycopy(reversingMove_bytes, 0, bytes, 5, reversingMove_bytes.length);
		return bytes;
	}

	@Override
	public NaviTrail fromBytes(byte[] bytes) {
		if(bytes.length!=6){
			throw new RuntimeException("导航轨迹数据长度须为6！");
		}
		this.setPathID(SocketUtil.bytesToInt(Arrays.copyOf(bytes, 4)));
		this.setMoveToPathEnd(SocketUtil.bytesToBoolean(Arrays.copyOfRange(bytes, 4,5)));
		this.setReversingMove(SocketUtil.bytesToBoolean(Arrays.copyOfRange(bytes, 5,6)));
		return this;
	}

}

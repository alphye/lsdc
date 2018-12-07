package com.lishengzn.entity.navi;

import com.google.common.primitives.Bytes;
import com.lishengzn.util.SocketUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SendNaviTask extends NaviTask{

	/**
	 * 目的地边ID
	 */
	private int destPathID;
	/**
	 * 目的地X坐标
	 */
	private double destCoor_x;
	/**
	 * 目的地Y坐标
	 */
	private double destCoor_y;
	/**
	 * 目的地角度（弧度制）
	 */
	private double destAngle;
	/**
	 * 是否完整导航
	 */
	private boolean completeNavi;
	/**
	 * 导航轨迹个数
	 */
	private int naviTrailNum;
	/**
	 * 导航轨迹
	 */
	
	private List<NaviTrail> trails;
	
	

	public SendNaviTask(){}
	public SendNaviTask(long taskID, int destPathID, double destCoor_x, double destCoor_y, double destAngle,
			boolean completeNavi, int naviTrailNum, List<NaviTrail> trails) {
		super(taskID);
		this.destPathID = destPathID;
		this.destCoor_x = destCoor_x;
		this.destCoor_y = destCoor_y;
		this.destAngle = destAngle;
		this.completeNavi = completeNavi;
		this.naviTrailNum = naviTrailNum;
		this.trails = trails;
	}

	public int getDestPathID() {
		return destPathID;
	}

	public void setDestPathID(int destPathID) {
		this.destPathID = destPathID;
	}

	public double getDestCoor_x() {
		return destCoor_x;
	}

	public void setDestCoor_x(double destCoor_x) {
		this.destCoor_x = destCoor_x;
	}

	public double getDestCoor_y() {
		return destCoor_y;
	}

	public void setDestCoor_y(double destCoor_y) {
		this.destCoor_y = destCoor_y;
	}

	public double getDestAngle() {
		return destAngle;
	}

	public void setDestAngle(double destAngle) {
		this.destAngle = destAngle;
	}

	public boolean isCompleteNavi() {
		return completeNavi;
	}

	public void setCompleteNavi(boolean completeNavi) {
		this.completeNavi = completeNavi;
	}

	public int getNaviTrailNum() {
		return naviTrailNum;
	}

	public void setNaviTrailNum(int naviTrailNum) {
		this.naviTrailNum = naviTrailNum;
	}

	public List<NaviTrail> getTrails() {
		return trails;
	}

	public void setTrails(List<NaviTrail> trails) {
		this.trails = trails;
	}

	@Override
	public byte[] toBytes() {
		List<Byte> byteList=new ArrayList<Byte>();
		byteList.addAll(Bytes.asList(SocketUtil.longToBytes(taskID)));
		byteList.addAll(Bytes.asList(SocketUtil.intToBytes(destPathID)));
		byteList.addAll(Bytes.asList(SocketUtil.doubleToBytes(destCoor_x)));
		byteList.addAll(Bytes.asList(SocketUtil.doubleToBytes(destCoor_y )));
		byteList.addAll(Bytes.asList(SocketUtil.doubleToBytes(destAngle )));
		byteList.addAll(Bytes.asList(SocketUtil.booleanToBytes(completeNavi )));
		byteList.addAll(Bytes.asList(SocketUtil.intToBytes(naviTrailNum )));
		trails.forEach((trail)->{
			if(trail !=null){
				byteList.addAll(Bytes.asList(trail.toBytes()));
			}
		});
		
		return Bytes.toArray(byteList);
	}

	@Override
	public SendNaviTask fromBytes(byte[] naviTaskVar_bytes) {
		if(naviTaskVar_bytes.length<41){
			throw new RuntimeException("发送导航任务的数据长度不得小于41！");
		}
		this.setTaskID(SocketUtil.bytesToLong(Arrays.copyOf(naviTaskVar_bytes, 8)));
		this.setDestPathID(SocketUtil.bytesToInt(Arrays.copyOfRange(naviTaskVar_bytes, 8,12)));
		this.setDestCoor_x(SocketUtil.bytesToDouble(Arrays.copyOfRange(naviTaskVar_bytes, 12,20)));
		this.setDestCoor_y(SocketUtil.bytesToDouble(Arrays.copyOfRange(naviTaskVar_bytes, 20,28)));
		this.setDestAngle(SocketUtil.bytesToDouble(Arrays.copyOfRange(naviTaskVar_bytes, 28,36)));
		this.setCompleteNavi(SocketUtil.bytesToBoolean(Arrays.copyOfRange(naviTaskVar_bytes, 36,37)));
		this.setNaviTrailNum(SocketUtil.bytesToInt(Arrays.copyOfRange(naviTaskVar_bytes, 37,41)));
		// 从41 开始，后边的内容都是trail，每个trail的长度为6
		int trailStart_index=41;
		List<NaviTrail> trails=new ArrayList<NaviTrail>();
		for(int i=0;i<this.getNaviTrailNum();i++){
			trails.add(new NaviTrail().fromBytes(Arrays.copyOfRange(naviTaskVar_bytes, trailStart_index,trailStart_index+=6)));
			
		}
		this.setTrails(trails);
		return this;
	}

}

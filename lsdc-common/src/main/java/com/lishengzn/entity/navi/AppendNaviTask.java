package com.lishengzn.entity.navi;

import com.google.common.primitives.Bytes;
import com.lishengzn.util.SocketUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**追加导航
 * @author Administrator
 *
 */
public class AppendNaviTask extends NaviTask{
	
	/**
	 * 索引 表示当前追加的导航第⼀个trail位于整个导航trail的下标
	 */
	private int appendIndex;
	/**
	 * 是否最后的导航轨迹
	 */
	private boolean destTrail;
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
	 * 导航轨迹个数
	 */
	private int naviTrailNum;
	/**
	 * 导航轨迹
	 */
	private List<NaviTrail> trails;
	
	public AppendNaviTask() {
		super();
	}



	public AppendNaviTask(long taskID, int appendIndex, boolean destTrail, int destPathID, double destCoor_x,
			double destCoor_y, double destAngle, int naviTrailNum, List<NaviTrail> trails) {
		super(taskID);
		this.appendIndex = appendIndex;
		this.destTrail = destTrail;
		this.destPathID = destPathID;
		this.destCoor_x = destCoor_x;
		this.destCoor_y = destCoor_y;
		this.destAngle = destAngle;
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
	public int getAppendIndex() {
		return appendIndex;
	}
	public void setAppendIndex(int appendIndex) {
		this.appendIndex = appendIndex;
	}
	public boolean isDestTrail() {
		return destTrail;
	}
	public void setDestTrail(boolean destTrail) {
		this.destTrail = destTrail;
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
		byteList.addAll(Bytes.asList(SocketUtil.intToBytes(appendIndex)));
		byteList.addAll(Bytes.asList(SocketUtil.booleanToBytes(destTrail )));
		byteList.addAll(Bytes.asList(SocketUtil.intToBytes(destPathID)));
		byteList.addAll(Bytes.asList(SocketUtil.doubleToBytes(destCoor_x)));
		byteList.addAll(Bytes.asList(SocketUtil.doubleToBytes(destCoor_y )));
		byteList.addAll(Bytes.asList(SocketUtil.doubleToBytes(destAngle )));
		byteList.addAll(Bytes.asList(SocketUtil.intToBytes(naviTrailNum )));
		trails.forEach((trail)->{
			if(trail !=null){
				byteList.addAll(Bytes.asList(trail.toBytes()));
			}
		});
		
		return Bytes.toArray(byteList);
	}

	@Override
	public AppendNaviTask fromBytes(byte[] data_bytes) {
		if(data_bytes.length<45){
			throw new RuntimeException("追加导航任务的数据长度不得小于45！");
		}
		this.setTaskID(SocketUtil.bytesToLong(Arrays.copyOf(data_bytes, 8)));
		this.setAppendIndex(SocketUtil.bytesToInt(Arrays.copyOfRange(data_bytes, 8,12)));
		this.setDestTrail(SocketUtil.bytesToBoolean(Arrays.copyOfRange(data_bytes, 12,13)));
		this.setDestPathID(SocketUtil.bytesToInt(Arrays.copyOfRange(data_bytes, 13,17)));
		
		this.setDestCoor_x(SocketUtil.bytesToDouble(Arrays.copyOfRange(data_bytes, 17,25)));
		this.setDestCoor_y(SocketUtil.bytesToDouble(Arrays.copyOfRange(data_bytes, 25,33)));
		this.setDestAngle(SocketUtil.bytesToDouble(Arrays.copyOfRange(data_bytes, 33,41)));
		
		this.setNaviTrailNum(SocketUtil.bytesToInt(Arrays.copyOfRange(data_bytes, 41,45)));
		// 从45 开始，后边的内容都是trail，每个trail的长度为6
		int trailStart_index=45;
		List<NaviTrail> trails=new ArrayList<NaviTrail>();
		for(int i=0;i<this.getNaviTrailNum();i++){
			trails.add(new NaviTrail().fromBytes(Arrays.copyOfRange(data_bytes, trailStart_index,trailStart_index+=6)));
			
		}
		this.setTrails(trails);
		return this;
	}
	
}

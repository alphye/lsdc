package com.lishengzn.entity.navi;

import com.google.common.primitives.Bytes;
import com.lishengzn.util.SocketUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**查询导航轨迹
 * @author Administrator
 *
 */
public class QueryNaviTrailsResponse extends NaviTask{
	/**
	 * 需要最近多少条轨迹数 据
	 */
	private int naviTrailNum;
	/**
	 * 导航轨迹
	 */
	private List<NaviTrail> trails;
	
	public QueryNaviTrailsResponse() {
		super();
	}
	
	public QueryNaviTrailsResponse(long taskID, int naviTrailNum, List<NaviTrail> trails) {
		super(taskID);
		this.naviTrailNum = naviTrailNum;
		this.trails = trails;
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
		byteList.addAll(Bytes.asList(SocketUtil.intToBytes(naviTrailNum )));
		trails.forEach((trail)->{
			if(trail !=null){
				byteList.addAll(Bytes.asList(trail.toBytes()));
			}
		});
		return Bytes.toArray(byteList);
	}

	@Override
	public QueryNaviTrailsResponse fromBytes(byte[] data_bytes) {
		if(data_bytes.length<12){
			throw new RuntimeException("应答查询导航轨迹的数据长度至少为12！");
		}
		this.setTaskID(SocketUtil.bytesToLong(Arrays.copyOf(data_bytes, 8)));
		this.setNaviTrailNum(SocketUtil.bytesToInt(Arrays.copyOfRange(data_bytes, 8, 12)));
		// 从12 开始，后边的内容都是trail，每个trail的长度为6
		int trailStart_index=12;
		List<NaviTrail> trails=new ArrayList<NaviTrail>();
		for(int i=0;i<this.getNaviTrailNum();i++){
			trails.add(new NaviTrail().fromBytes(Arrays.copyOfRange(data_bytes, trailStart_index,trailStart_index+=6)));
			
		}
		this.setTrails(trails);
		return this;
	}
	
}

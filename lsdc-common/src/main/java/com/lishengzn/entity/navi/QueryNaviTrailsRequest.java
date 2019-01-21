package com.lishengzn.entity.navi;

import com.google.common.primitives.Bytes;
import com.lishengzn.entity.DataArea;
import com.lishengzn.util.SocketUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**查询导航轨迹
 * @author Administrator
 *
 */
public class QueryNaviTrailsRequest implements DataArea {
	/**
	 * 需要最近多少条轨迹数 据
	 */
	private int naviTrailNum;
	
	public QueryNaviTrailsRequest() {
	}
	

	public QueryNaviTrailsRequest( int naviTrailNum) {
		this.naviTrailNum = naviTrailNum;
	}

	public int getNaviTrailNum() {
		return naviTrailNum;
	}
	public void setNaviTrailNum(int naviTrailNum) {
		this.naviTrailNum = naviTrailNum;
	}
	

	@Override
	public byte[] toBytes() {
		List<Byte> byteList=new ArrayList<Byte>();
		byteList.addAll(Bytes.asList(SocketUtil.intToBytes(naviTrailNum )));
		return Bytes.toArray(byteList);
	}

	@Override
	public QueryNaviTrailsRequest fromBytes(byte[] data_bytes) {
		if(data_bytes.length!=4){
			throw new RuntimeException("查询导航轨迹的数据长度须为4！");
		}
		this.setNaviTrailNum(SocketUtil.bytesToInt(Arrays.copyOfRange(data_bytes, 0, 4)));
		return this;
	}
	
}

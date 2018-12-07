package com.lishengzn.entity.order;

import com.lishengzn.dto.VehicleMoveDto;
import com.lishengzn.entity.Coordinate;
import com.lishengzn.entity.navi.AppendNaviTask;
import com.lishengzn.entity.navi.SendNaviTask;

import java.util.List;

public class TransportOrder {
	private long naviTaskID;
	private Coordinate destCoor;
	private VehicleMoveDto vehicleMove;
	private SendNaviTask naviTask;
	private List<AppendNaviTask> appendNavitasks;
	private long createTime =System.currentTimeMillis();
	private volatile STATE state;
	
	private TYPE transportOrderType;
	
	public long getNaviTaskID() {
		return naviTaskID;
	}
	public void setNaviTaskID(long naviTaskID) {
		this.naviTaskID = naviTaskID;
	}
	public VehicleMoveDto getVehicleMove() {
		return vehicleMove;
	}
	public void setVehicleMove(VehicleMoveDto vehicleMove) {
		this.vehicleMove = vehicleMove;
	}
	
	public SendNaviTask getNaviTask() {
		return naviTask;
	}
	public void setNaviTask(SendNaviTask naviTask) {
		this.naviTask = naviTask;
	}
	
	
	public Coordinate getDestCoor() {
		return destCoor;
	}
	public void setDestCoor(Coordinate destCoor) {
		this.destCoor = destCoor;
	}
	public long getCreateTime() {
		return createTime;
	}
	




	public List<AppendNaviTask> getAppendNavitasks() {
		return appendNavitasks;
	}
	public void setAppendNavitasks(List<AppendNaviTask> appendNavitasks) {
		this.appendNavitasks = appendNavitasks;
	}
	public STATE getState() {
		return state;
	}
	public void setState(STATE state) {
		this.state = state;
	}
	public TYPE getTransportOrderType() {
		return transportOrderType;
	}
	public void setTransportOrderType(TYPE transportOrderType) {
		this.transportOrderType = transportOrderType;
	}




	public enum TYPE{
		moveWidthCells,
		sendToNode;
	}
	public enum STATE{
		DISPATCHABLE,
		EXECUTING,
		PAUSE,
		FAILED,
		FINISHED;
	}
}

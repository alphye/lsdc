package com.lishengzn.dto;

import java.io.Serializable;

import com.lishengzn.entity.Coordinate;

public class VehicleMoveDto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String vehicleIp;
	private int direct;
	private int cellNum;
	private double packageLength;
	private double packageWidth;
	
	private Integer destNodeId;
	/**
	 * 发送到指定位置
	 */
	private Coordinate destCoor;
	private boolean moveWidthCellNum;
	
	
	

	public String getVehicleIp() {
		return vehicleIp;
	}
	public void setVehicleIp(String vehicleIp) {
		this.vehicleIp = vehicleIp;
	}
	public int getDirect() {
		return direct;
	}
	public void setDirect(int direct) {
		this.direct = direct;
	}
	public int getCellNum() {
		return cellNum;
	}
	public void setCellNum(int cellNum) {
		this.cellNum = cellNum;
	}
	
	public double getPackageLength() {
		return packageLength;
	}
	public void setPackageLength(double packageLength) {
		this.packageLength = packageLength;
	}
	public double getPackageWidth() {
		return packageWidth;
	}
	public void setPackageWidth(double packageWidth) {
		this.packageWidth = packageWidth;
	}
	public boolean isMoveWidthCellNum() {
		return moveWidthCellNum;
	}
	public void setMoveWidthCellNum(boolean moveWidthCellNum) {
		this.moveWidthCellNum = moveWidthCellNum;
	}
	
	
	public Coordinate getDestCoor() {
		return destCoor;
	}
	public void setDestCoor(Coordinate destCoor) {
		this.destCoor = destCoor;
	}
	public Integer getDestNodeId() {
		return destNodeId;
	}
	public void setDestNodeId(Integer destNodeId) {
		this.destNodeId = destNodeId;
	}
	

	
}

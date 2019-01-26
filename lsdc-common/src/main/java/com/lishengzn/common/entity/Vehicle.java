package com.lishengzn.common.entity;


import com.lishengzn.lsdc.entity.Coordinate;

import java.io.Serializable;

public class Vehicle implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String vehicleIp;
	/**
	 * 车辆位置
	 */
	private Coordinate position;
	
	/**
	 * x方向速度
	 */
	private double vx;
	/**
	 * y方向速度
	 */
	private double vy;
	
	/**
	 * 角速度
	 */
	private double angularVelocity;
	/**
	 * 加速度
	 */
	private double acceleration;
	
	/**
	 * 角度
	 */
	private double angle;
	
	/**
	 * 所在边ID
	 */
	private int pathId;
	
	/**
	 * 导航状态
	 */
	private int naviState;
	
	private String naviStateName;
	
	/**
	 * 置信度
	 */
	private int confidenceDegree;
	
		/**
	 * 总里程数
	 */
	private long mileage;
	
	/**
	 * 小车是否有包裹
	 */
	private int hasPackage;
	
	/**
	 * 皮带转动状态
	 */
	private int beltRotationState;

	/**
	 * 操作状态
	 */
	private int operationState;
	
	private String operationStateName;
	
	/**
	 * 电池容量
	 */
	private int batteryCapacity;
	
	/**
	 * 电池余量
	 */
	private int batteryResidues;

	
	public Vehicle(){}
	public Vehicle(Coordinate position){
		this.position=position;
	}

	public String getVehicleIp() {
		return vehicleIp;
	}

	public void setVehicleIp(String vehicleIp) {
		this.vehicleIp = vehicleIp;
	}

	public Coordinate getPosition() {
		return position;
	}

	public void setPosition(Coordinate position) {
		this.position = position;
	}

	public double getVx() {
		return vx;
	}

	public void setVx(double vx) {
		this.vx = vx;
	}

	public double getVy() {
		return vy;
	}

	public void setVy(double vy) {
		this.vy = vy;
	}

	public double getAngularVelocity() {
		return angularVelocity;
	}

	public void setAngularVelocity(double angularVelocity) {
		this.angularVelocity = angularVelocity;
	}

	public double getAcceleration() {
		return acceleration;
	}

	public void setAcceleration(double acceleration) {
		this.acceleration = acceleration;
	}

	public double getAngle() {
		return angle;
	}

	public void setAngle(double angle) {
		this.angle = angle;
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

	public String getNaviStateName() {
		return naviStateName;
	}

	public void setNaviStateName(String naviStateName) {
		this.naviStateName = naviStateName;
	}

	public int getConfidenceDegree() {
		return confidenceDegree;
	}

	public void setConfidenceDegree(int confidenceDegree) {
		this.confidenceDegree = confidenceDegree;
	}

	public long getMileage() {
		return mileage;
	}

	public void setMileage(long mileage) {
		this.mileage = mileage;
	}

	public int getHasPackage() {
		return hasPackage;
	}

	public void setHasPackage(int hasPackage) {
		this.hasPackage = hasPackage;
	}

	public int getBeltRotationState() {
		return beltRotationState;
	}

	public void setBeltRotationState(int beltRotationState) {
		this.beltRotationState = beltRotationState;
	}

	public int getOperationState() {
		return operationState;
	}

	public void setOperationState(int operationState) {
		this.operationState = operationState;
	}

	public String getOperationStateName() {
		return operationStateName;
	}

	public void setOperationStateName(String operationStateName) {
		this.operationStateName = operationStateName;
	}

	public int getBatteryCapacity() {
		return batteryCapacity;
	}

	public void setBatteryCapacity(int batteryCapacity) {
		this.batteryCapacity = batteryCapacity;
	}

	public int getBatteryResidues() {
		return batteryResidues;
	}

	public void setBatteryResidues(int batteryResidues) {
		this.batteryResidues = batteryResidues;
	}
}

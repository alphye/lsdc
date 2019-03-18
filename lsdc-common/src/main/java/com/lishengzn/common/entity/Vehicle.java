package com.lishengzn.common.entity;


import com.lishengzn.lsdc.entity.Coordinate;

import java.io.Serializable;

public class Vehicle implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** 锁对象的整合类，只提供getter */
	private RequestSyncObjCollection requestSnynObj = new RequestSyncObjCollection();

	private String vehicleIp;
	/**
	 * 车辆位置
	 */
	private Coordinate position;
	/** 机器人当前所在站点的 ID（该判断比较严格，机器人必须很靠近某一个站点(<30cm)，否则为空字符，即不处于任何站点）; */
	private String currentStation;
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
	private double confidenceDegree;
	
		/**
	 * 总里程数
	 */
	private double mileage;
	
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

	public Vehicle(String vehicleIp, Coordinate position, double vx, double vy, double angularVelocity, double acceleration, double angle, int pathId, int naviState, String naviStateName, double confidenceDegree, double mileage, int hasPackage, int beltRotationState, int operationState, String operationStateName, int batteryCapacity, int batteryResidues) {
		this.vehicleIp = vehicleIp;
		this.position = position;
		this.vx = vx;
		this.vy = vy;
		this.angularVelocity = angularVelocity;
		this.acceleration = acceleration;
		this.angle = angle;
		this.pathId = pathId;
		this.naviState = naviState;
		this.naviStateName = naviStateName;
		this.confidenceDegree = confidenceDegree;
		this.mileage = mileage;
		this.hasPackage = hasPackage;
		this.beltRotationState = beltRotationState;
		this.operationState = operationState;
		this.operationStateName = operationStateName;
		this.batteryCapacity = batteryCapacity;
		this.batteryResidues = batteryResidues;
	}

	public RequestSyncObjCollection getRequestSnynObj() {
		return requestSnynObj;
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

	public String getCurrentStation() {
		return currentStation;
	}

	public void setCurrentStation(String currentStation) {
		this.currentStation = currentStation;
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

	public double getConfidenceDegree() {
		return confidenceDegree;
	}

	public void setConfidenceDegree(double confidenceDegree) {
		this.confidenceDegree = confidenceDegree;
	}

	public double getMileage() {
		return mileage;
	}

	public void setMileage(double mileage) {
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

	@Override
	public Vehicle clone()  {
		return new Vehicle(vehicleIp, position, vx, vy, angularVelocity, acceleration, angle, pathId, naviState, naviStateName, confidenceDegree, mileage, hasPackage, beltRotationState, operationState, operationStateName, batteryCapacity, batteryResidues);
	}
}

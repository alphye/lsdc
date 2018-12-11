package com.lishengzn.entity;

import com.lishengzn.entity.order.TransportOrder;

import java.io.Serializable;

public class Vehicle implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String vehicleIp;
	/**
	 * 小车在执行的任务
	 */
	private TransportOrder transportOrder;
	/**
	 * 车辆位置
	 */
	private Coordinate position;

	private Coordinate targetPosition;
	
	/**
	 * x方向速度
	 */
	private double velocity_x;
	/**
	 * y方向速度
	 */
	private double velocity_y;
	/**
	 * 速度
	 */
	private double velocity;
	
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
	 * 是否处于雷达防撞
	 */
	private int radarCollisionAvoidance;
	
	/**
	 * 是否出现故障
	 */
	private int malfunction;
	
	/**
	 * 总里程数
	 */
	private long mileage;
	
	
	/**
	 * 是否接触到充电桩
	 */
	private int contactChargingPile;
	
	/**
	 * 充电继电器是否打开
	 */
	private int relayOn;

	/**
	 * 小车是否有包裹
	 */
	private int hasPackage;
	
	/**
	 * 小车翻盖状态
	 */
	private int flipState;
	
	/**
	 * 顶升距离
	 */
	private int jackingDistance;
	
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
	private double batteryCapacity;
	
	/**
	 * 电池余量
	 */
	private double batteryResidues;
	
	/**
	 * 电池状态
	 */
	private int batteryState;
	
	
	public Vehicle(){}
	public Vehicle(Coordinate position){
		this.position=position;
	}



	public Vehicle(Coordinate position,  double velocity_x, double velocity_y, double velocity,
			double angularVelocity, double acceleration, int angle, int pathId, int naviState, int confidenceDegree,
			int radarCollisionAvoidance, int malfunction, int mileage, int contactChargingPile,int relayOn,int hasPackage,
			int flipState,int jackingDistance,int beltRotationState,int operationState,double batteryCapacity,double batteryResidues,int batteryState) {
		super();
		this.position = position;
		this.velocity_x = velocity_x;
		this.velocity_y = velocity_y;
		this.velocity = velocity;
		this.angularVelocity = angularVelocity;
		this.acceleration = acceleration;
		this.angle = angle;
		this.pathId = pathId;
		this.naviState = naviState;
		this.confidenceDegree = confidenceDegree;
		this.radarCollisionAvoidance = radarCollisionAvoidance;
		this.malfunction = malfunction;
		this.mileage = mileage;
		this.contactChargingPile=contactChargingPile;
		this.relayOn=relayOn;
		this.hasPackage=hasPackage;
		this.flipState=flipState;
		this.setJackingDistance(jackingDistance);
		this.beltRotationState =beltRotationState;
		this.operationState=operationState;
		this.batteryCapacity=batteryCapacity;
		this.batteryResidues=batteryResidues;
		this.batteryState =batteryState;
	}


	

	
	public TransportOrder getTransportOrder() {
		return transportOrder;
	}
	public void setTransportOrder(TransportOrder transportOrder) {
		this.transportOrder = transportOrder;
	}
	public String getVehicleIp() {
		return vehicleIp;
	}
	public  Coordinate getPosition() {
		return position;
	}

	public  void setPosition(Coordinate position) {
		this.position = position;
	}

	public Coordinate getTargetPosition() {
		return targetPosition;
	}

	public void setTargetPosition(Coordinate targetPosition) {
		this.targetPosition = targetPosition;
	}

	public  double getVelocity_x() {
		return velocity_x;
	}

	public  void setVelocity_x(double velocity_x) {
		this.velocity_x = velocity_x;
	}

	public  double getVelocity_y() {
		return velocity_y;
	}

	public  void setVelocity_y(double velocity_y) {
		this.velocity_y = velocity_y;
	}

	public  double getVelocity() {
		return velocity;
	}

	public  void setVelocity(double velocity) {
		this.velocity = velocity;
	}

	public  double getAcceleration() {
		return acceleration;
	}

	public  void setAcceleration(double acceleration) {
		this.acceleration = acceleration;
	}

	public  double getAngle() {
		return angle;
	}

	public  void setAngle(double angle) {
		this.angle = angle;
	}


	public  int getPathId() {
		return pathId;
	}

	public  void setPathId(int pathId) {
		this.pathId = pathId;
	}

	public  int getNaviState() {
		return naviState;
	}

	public   void setNaviState(int naviState) {
		this.naviState = naviState;
	}



	public  double getAngularVelocity() {
		return angularVelocity;
	}



	public  void setAngularVelocity(double angularVelocity) {
		this.angularVelocity = angularVelocity;
	}



	public  int getConfidenceDegree() {
		return confidenceDegree;
	}



	public  void setConfidenceDegree(int confidenceDegree) {
		this.confidenceDegree = confidenceDegree;
	}



	public  int getRadarCollisionAvoidance() {
		return radarCollisionAvoidance;
	}



	public  void setRadarCollisionAvoidance(int radarCollisionAvoidance) {
		this.radarCollisionAvoidance = radarCollisionAvoidance;
	}



	public  int getMalfunction() {
		return malfunction;
	}



	public  void setMalfunction(int malfunction) {
		this.malfunction = malfunction;
	}



	public  long getMileage() {
		return mileage;
	}



	public  void setMileage(long mileage) {
		this.mileage = mileage;
	}
	public  int getContactChargingPile() {
		return contactChargingPile;
	}
	public  void setContactChargingPile(int contactChargingPile) {
		this.contactChargingPile = contactChargingPile;
	}
	public  int getHasPackage() {
		return hasPackage;
	}
	public  void setHasPackage(int hasPackage) {
		this.hasPackage = hasPackage;
	}
	public  int getFlipState() {
		return flipState;
	}
	public  void setFlipState(int flipState) {
		this.flipState = flipState;
	}
	public  int getJackingDistance() {
		return jackingDistance;
	}
	public  void setJackingDistance(int jackingDistance) {
		this.jackingDistance = jackingDistance;
	}
	public  int getBeltRotationState() {
		return beltRotationState;
	}
	public  void setBeltRotationState(int beltRotationState) {
		this.beltRotationState = beltRotationState;
	}
	public  int getOperationState() {
		return operationState;
	}
	public  void setOperationState(int operationState) {
		this.operationState = operationState;
	}
	public  double getBatteryCapacity() {
		return batteryCapacity;
	}
	public  void setBatteryCapacity(double batteryCapacity) {
		this.batteryCapacity = batteryCapacity;
	}
	public  double getBatteryResidues() {
		return batteryResidues;
	}
	public  void setBatteryResidues(double batteryResidues) {
		this.batteryResidues = batteryResidues;
	}
	public  int getBatteryState() {
		return batteryState;
	}
	public  void setBatteryState(int batteryState) {
		this.batteryState = batteryState;
	}
	public  int getRelayOn() {
		return relayOn;
	}
	public  void setRelayOn(int relayOn) {
		this.relayOn = relayOn;
	}
	public  String getNaviStateName() {
		return naviStateName;
	}
	public  void setNaviStateName(String naviStateName) {
		this.naviStateName = naviStateName;
	}
	public  String getOperationStateName() {
		return operationStateName;
	}
	public  void setOperationStateName(String operationStateName) {
		this.operationStateName = operationStateName;
	}
	@Override
	public String toString() {
		return "Vehicle [vehicleIp=" + vehicleIp + ", transportOrder=" + transportOrder + ", position=" + position
				+ ", velocity_x=" + velocity_x + ", velocity_y=" + velocity_y + ", velocity=" + velocity
				+ ", angularVelocity=" + angularVelocity + ", acceleration=" + acceleration + ", angle=" + angle
				+ ", pathId=" + pathId + ", naviState=" + naviState + ", naviStateName=" + naviStateName
				+ ", confidenceDegree=" + confidenceDegree + ", radarCollisionAvoidance=" + radarCollisionAvoidance
				+ ", malfunction=" + malfunction + ", mileage=" + mileage + ", contactChargingPile="
				+ contactChargingPile + ", relayOn=" + relayOn + ", hasPackage=" + hasPackage + ", flipState="
				+ flipState + ", jackingDistance=" + jackingDistance + ", beltRotationState=" + beltRotationState
				+ ", operationState=" + operationState + ", operationStateName=" + operationStateName
				+ ", batteryCapacity=" + batteryCapacity + ", batteryResidues=" + batteryResidues + ", batteryState="
				+ batteryState + "]";
	}

	
	
	
}

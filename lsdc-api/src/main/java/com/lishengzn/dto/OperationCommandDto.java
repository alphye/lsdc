package com.lishengzn.dto;

import java.io.Serializable;
import java.util.List;

public class OperationCommandDto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String vehicleIp;
	private int operationCode;
	private List<Integer> operationParams;
	
	public String getVehicleIp() {
		return vehicleIp;
	}
	public void setVehicleIp(String vehicleIp) {
		this.vehicleIp = vehicleIp;
	}
	
	public int getOperationCode() {
		return operationCode;
	}
	public void setOperationCode(int operationCode) {
		this.operationCode = operationCode;
	}
	public List<Integer> getOperationParams() {
		return operationParams;
	}
	public void setOperationParams(List<Integer> operationParams) {
		this.operationParams = operationParams;
	}
	
	
}

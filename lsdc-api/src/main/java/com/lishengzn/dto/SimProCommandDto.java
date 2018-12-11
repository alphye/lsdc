package com.lishengzn.dto;

import java.io.Serializable;
import java.util.List;

public class SimProCommandDto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String vehicleIp;
	private String param;

	public SimProCommandDto(String vehicleIp, String param) {
		this.vehicleIp = vehicleIp;
		this.param = param;
	}

	public String getVehicleIp() {
		return vehicleIp;
	}
	public void setVehicleIp(String vehicleIp) {
		this.vehicleIp = vehicleIp;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}
}

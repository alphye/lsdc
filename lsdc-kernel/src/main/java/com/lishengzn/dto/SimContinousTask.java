package com.lishengzn.dto;

import java.io.Serializable;

public class SimContinousTask implements Serializable {
    private static final long serialVersionUID = 1L;
    private String  vehicleIp;
    private String destPoint;
    private String destOpt;

    public String getVehicleIp() {
        return vehicleIp;
    }

    public void setVehicleIp(String vehicleIp) {
        this.vehicleIp = vehicleIp;
    }

    public String getDestPoint() {
        return destPoint;
    }

    public void setDestPoint(String destPoint) {
        this.destPoint = destPoint;
    }

    public String getDestOpt() {
        return destOpt;
    }

    public void setDestOpt(String destOpt) {
        this.destOpt = destOpt;
    }
}

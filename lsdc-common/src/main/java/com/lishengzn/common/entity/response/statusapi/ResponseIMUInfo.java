package com.lishengzn.common.entity.response.statusapi;

import com.lishengzn.common.entity.response.ResponseEntity;

public class ResponseIMUInfo extends ResponseEntity {
    /**偏航角，单位：rad; 可缺省：是*/
    private double yaw;

    /**滚转角，单位：rad; 可缺省：是*/
    private double roll;

    /**俯仰角，单位：rad; 可缺省：是*/
    private double pitch;


    public double getYaw() {
        return yaw;
    }

    public void setYaw(double yaw) {
        this.yaw = yaw;
    }

    public double getRoll() {
        return roll;
    }

    public void setRoll(double roll) {
        this.roll = roll;
    }

    public double getPitch() {
        return pitch;
    }

    public void setPitch(double pitch) {
        this.pitch = pitch;
    }
}

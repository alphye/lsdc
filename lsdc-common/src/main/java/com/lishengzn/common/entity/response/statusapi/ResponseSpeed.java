package com.lishengzn.common.entity.response.statusapi;

import com.lishengzn.common.entity.response.ResponseEntity;

public class ResponseSpeed extends ResponseEntity {
    /**机器人在机器人坐标系的 x 方向实际的速度, 单位 m/s; 可缺省：是*/
    private double vx;

    /**机器人在机器人坐标系的 y 方向实际的速度, 单位 m/s; 可缺省：是*/
    private double vy;

    /**机器人在机器人坐标系的实际的角速度(即顺时针转为负, 逆时针转为正), 单位 rad/s; 可缺省：是*/
    private double w;

    /**单舵轮机器人当前的舵轮角度 rad; 可缺省：是*/
    private double steer;

    /**托盘机器人的托盘角度 rad; 可缺省：是*/
    private double spin;

    /**机器人在机器人坐标系的 x 方向接收到的速度, 单位 m/s; 可缺省：是*/
    private double r_vx;

    /**机器人在机器人坐标系的 y 方向收到的速度, 单位 m/s; 可缺省：是*/
    private double r_vy;

    /**机器人在机器人坐标系的收到的角速度(即顺时针转为负, 逆时针转为正), 单位 rad/s; 可缺省：是*/
    private double r_w;

    /**单舵轮机器人收到的舵轮角度 rad; 可缺省：是*/
    private double r_steer;

    /**托盘机器人的收到托盘转动速度 rad/s; 可缺省：是*/
    private double r_spin;


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

    public double getW() {
        return w;
    }

    public void setW(double w) {
        this.w = w;
    }

    public double getSteer() {
        return steer;
    }

    public void setSteer(double steer) {
        this.steer = steer;
    }

    public double getSpin() {
        return spin;
    }

    public void setSpin(double spin) {
        this.spin = spin;
    }

    public double getR_vx() {
        return r_vx;
    }

    public void setR_vx(double r_vx) {
        this.r_vx = r_vx;
    }

    public double getR_vy() {
        return r_vy;
    }

    public void setR_vy(double r_vy) {
        this.r_vy = r_vy;
    }

    public double getR_w() {
        return r_w;
    }

    public void setR_w(double r_w) {
        this.r_w = r_w;
    }

    public double getR_steer() {
        return r_steer;
    }

    public void setR_steer(double r_steer) {
        this.r_steer = r_steer;
    }

    public double getR_spin() {
        return r_spin;
    }

    public void setR_spin(double r_spin) {
        this.r_spin = r_spin;
    }
}

package com.lishengzn.common.entity.request.navi;

/** 路径导航*/
public class RequestGoTarget {
    /**目标站点的 id; 可缺省：否*/
    private String id;

    /**目标站点(世界坐标系)的角度值, 单位 rad; 可缺省：是*/
    private double angle;

    /**最大速度, 单位 m/s; 可缺省：是*/
    private double max_speed;

    /**最大角速度, 单位 rad/s; 可缺省：是*/
    private double max_wspeed;

    /**最大加速度, 单位 m/s^2; 可缺省：是*/
    private double max_acc;

    /**最大角加速度, 单位 rad/s^2; 可缺省：是*/
    private double max_wacc;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public double getMax_speed() {
        return max_speed;
    }

    public void setMax_speed(double max_speed) {
        this.max_speed = max_speed;
    }

    public double getMax_wspeed() {
        return max_wspeed;
    }

    public void setMax_wspeed(double max_wspeed) {
        this.max_wspeed = max_wspeed;
    }

    public double getMax_acc() {
        return max_acc;
    }

    public void setMax_acc(double max_acc) {
        this.max_acc = max_acc;
    }

    public double getMax_wacc() {
        return max_wacc;
    }

    public void setMax_wacc(double max_wacc) {
        this.max_wacc = max_wacc;
    }
}

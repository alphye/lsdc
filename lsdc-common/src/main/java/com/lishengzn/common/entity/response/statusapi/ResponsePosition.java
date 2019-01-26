package com.lishengzn.common.entity.response.statusapi;

import com.lishengzn.common.entity.response.ResponseEntity;

public class ResponsePosition extends ResponseEntity {
    /**机器人的 x 坐标, 单位 m; 可缺省：是*/
    private double x;

    /**机器人的 y 坐标, 单位 m; 可缺省：是*/
    private double y;

    /**机器人的 angle 坐标, 单位 rad; 可缺省：是*/
    private double angle;

    /**机器人的定位置信度, 范围 [0, 1]; 可缺省：是*/
    private double confidence;

    /**机器人当前所在站点的 ID（该判断比较严格，机器人必须很靠近某一个站点(<30cm)，否则为空字符，即不处于任何站点）; 可缺省：是*/
    private String current_station;


    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public double getConfidence() {
        return confidence;
    }

    public void setConfidence(double confidence) {
        this.confidence = confidence;
    }

    public String getCurrent_station() {
        return current_station;
    }

    public void setCurrent_station(String current_station) {
        this.current_station = current_station;
    }
}

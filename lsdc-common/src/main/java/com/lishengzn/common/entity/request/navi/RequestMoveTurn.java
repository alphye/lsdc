package com.lishengzn.common.entity.request.navi;

public class RequestMoveTurn {
    /**转动的角度(机器人坐标系), 绝对值, 单位 rad, 可以大于 2π; 可缺省：否*/
    private double angle;

    /**转动的角速度(机器人坐标系), 正为逆时针转, 负为顺时针转 单位 rad/s; 可缺省：否*/
    private double vw;

    /**0 = 里程模式(根据里程进行运动), 1 = 自定位模式(根据机器人自定位进行运动), 若缺省则默认为里程模式; 可缺省：是*/
    private double mode;

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public double getVw() {
        return vw;
    }

    public void setVw(double vw) {
        this.vw = vw;
    }

    public double getMode() {
        return mode;
    }

    public void setMode(double mode) {
        this.mode = mode;
    }
}

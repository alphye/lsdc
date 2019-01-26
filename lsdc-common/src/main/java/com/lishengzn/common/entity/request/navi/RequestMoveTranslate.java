package com.lishengzn.common.entity.request.navi;

public class RequestMoveTranslate {
    /**直线运动距离, 绝对值, 单位 m; 可缺省：否*/
    private double dist;

    /**直线运动的速度, 正为向前, 负为向后, 单位 m/s; 可缺省：是*/
    private double vx;

    /**横向直线运动的速度, 正为向左, 负为向右, 单位 m/s; 可缺省：是*/
    private double vy;

    /**0 = 里程模式(根据里程进行运动), 1 = 自定位模式(根据机器人自定位进行运动, 目前不可用), 若缺省则默认为里程模式; 可缺省：是*/
    private double mode;

    public double getDist() {
        return dist;
    }

    public void setDist(double dist) {
        this.dist = dist;
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

    public double getMode() {
        return mode;
    }

    public void setMode(double mode) {
        this.mode = mode;
    }
}

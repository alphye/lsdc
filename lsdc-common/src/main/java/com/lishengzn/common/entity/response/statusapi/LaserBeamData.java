package com.lishengzn.common.entity.response.statusapi;

public class LaserBeamData {
    private double angle;
    private double dist;
    private boolean is_obstacle;
    private boolean is_virtual;
    private int rssi;
    private boolean valid;
    private double x;
    private double y;


    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public double getDist() {
        return dist;
    }

    public void setDist(double dist) {
        this.dist = dist;
    }

    public boolean isIs_obstacle() {
        return is_obstacle;
    }

    public void setIs_obstacle(boolean is_obstacle) {
        this.is_obstacle = is_obstacle;
    }

    public boolean isIs_virtual() {
        return is_virtual;
    }

    public void setIs_virtual(boolean is_virtual) {
        this.is_virtual = is_virtual;
    }

    public int getRssi() {
        return rssi;
    }

    public void setRssi(int rssi) {
        this.rssi = rssi;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

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
}

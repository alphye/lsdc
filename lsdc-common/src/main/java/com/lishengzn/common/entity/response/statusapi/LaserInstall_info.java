package com.lishengzn.common.entity.response.statusapi;

public class LaserInstall_info {
    private boolean upside;
    private double x;
    private double y;
    private double z;
    private double yaw;

    public boolean isUpside() {
        return upside;
    }

    public void setUpside(boolean upside) {
        this.upside = upside;
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

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public double getYaw() {
        return yaw;
    }

    public void setYaw(double yaw) {
        this.yaw = yaw;
    }
}

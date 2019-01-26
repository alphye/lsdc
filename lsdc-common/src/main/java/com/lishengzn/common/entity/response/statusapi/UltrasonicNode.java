package com.lishengzn.common.entity.response.statusapi;

public class UltrasonicNode {

    /** 该超声节点的ID*/
    private int id;

    /** 该超声感应到的距离(单位:米)*/
    private double dist;

    /** 是否激活*/
    private boolean valid;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getDist() {
        return dist;
    }

    public void setDist(double dist) {
        this.dist = dist;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }
}

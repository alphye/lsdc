package com.lishengzn.common.entity.response.map;

import com.lishengzn.lsdc.entity.Coordinate;

public class AdvancedPoint {
    private String className;
    private String instanceName;
    private Coordinate pos;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getInstanceName() {
        return instanceName;
    }

    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }

    public Coordinate getPos() {
        return pos;
    }

    public void setPos(Coordinate pos) {
        this.pos = pos;
    }
}

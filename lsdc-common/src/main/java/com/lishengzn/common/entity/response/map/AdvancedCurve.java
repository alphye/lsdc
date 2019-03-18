package com.lishengzn.common.entity.response.map;

import com.lishengzn.lsdc.entity.Coordinate;

import java.util.List;
import java.util.Map;

public class AdvancedCurve {
    private String className;
    private AdvancedPoint startPos;
    private AdvancedPoint endPos;
    private Coordinate controlPos1;
    private Coordinate controlPos2;

    private List<Map> property;
    private List<Map> devices;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public AdvancedPoint getStartPos() {
        return startPos;
    }

    public void setStartPos(AdvancedPoint startPos) {
        this.startPos = startPos;
    }

    public AdvancedPoint getEndPos() {
        return endPos;
    }

    public void setEndPos(AdvancedPoint endPos) {
        this.endPos = endPos;
    }

    public Coordinate getControlPos1() {
        return controlPos1;
    }

    public void setControlPos1(Coordinate controlPos1) {
        this.controlPos1 = controlPos1;
    }

    public Coordinate getControlPos2() {
        return controlPos2;
    }

    public void setControlPos2(Coordinate controlPos2) {
        this.controlPos2 = controlPos2;
    }

    public List<Map> getProperty() {
        return property;
    }

    public void setProperty(List<Map> property) {
        this.property = property;
    }

    public List<Map> getDevices() {
        return devices;
    }

    public void setDevices(List<Map> devices) {
        this.devices = devices;
    }
}

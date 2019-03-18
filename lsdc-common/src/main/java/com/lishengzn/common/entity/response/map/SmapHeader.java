package com.lishengzn.common.entity.response.map;

import com.lishengzn.lsdc.entity.Coordinate;

public class SmapHeader {
    private String mapType;
    private String mapName;
    private Coordinate minPos;
    private Coordinate maxPos;
    private double resolution;
    private String version;

    public String getMapType() {
        return mapType;
    }

    public void setMapType(String mapType) {
        this.mapType = mapType;
    }

    public String getMapName() {
        return mapName;
    }

    public void setMapName(String mapName) {
        this.mapName = mapName;
    }

    public Coordinate getMinPos() {
        return minPos;
    }

    public void setMinPos(Coordinate minPos) {
        this.minPos = minPos;
    }

    public Coordinate getMaxPos() {
        return maxPos;
    }

    public void setMaxPos(Coordinate maxPos) {
        this.maxPos = maxPos;
    }

    public double getResolution() {
        return resolution;
    }

    public void setResolution(double resolution) {
        this.resolution = resolution;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}

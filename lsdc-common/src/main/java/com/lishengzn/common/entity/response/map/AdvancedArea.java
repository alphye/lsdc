package com.lishengzn.common.entity.response.map;

import com.lishengzn.lsdc.entity.Coordinate;

import java.util.List;
import java.util.Map;

public class AdvancedArea {
    private String className;
    private String instanceName;
    private List<Coordinate> posGroup;
    private List<Map>property;

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

    public List<Coordinate> getPosGroup() {
        return posGroup;
    }

    public void setPosGroup(List<Coordinate> posGroup) {
        this.posGroup = posGroup;
    }

    public List<Map> getProperty() {
        return property;
    }

    public void setProperty(List<Map> property) {
        this.property = property;
    }
}

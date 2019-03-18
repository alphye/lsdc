package com.lishengzn.common.entity.response.map;

import com.lishengzn.common.entity.map.Edge;

public class AdvancedEdge {
    private String className;
    private String instanceName;
    private Edge line;

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

    public Edge getLine() {
        return line;
    }

    public void setLine(Edge line) {
        this.line = line;
    }
}

package com.lishengzn.common.entity.response.map;

import com.lishengzn.common.entity.map.Edge;
import com.lishengzn.lsdc.entity.Coordinate;

import java.util.List;

/**
 * 地图对象
 */
public class Smap {
    /** 地图路径, 目前不用 */
    private String mapDirectory;

    /** 地图头部 */
    private SmapHeader header;

    /** 普通点数组 */
    private List<Coordinate> rssiPosList;

    /** 反光板点数组 */
    private List<Coordinate> normalPosList;

    /** 普通线数组 */
    private List<Edge> normalLineList;

    /** 高级点数组 */
    private List<AdvancedPoint> advancedPointList;

    /** 高级线数组 */
    private List<AdvancedEdge> advancedLineList;

    /** 高级曲线数组 */
    private List<AdvancedCurve> advancedCurveList;

    /** 高级区域数组 */
    private List<AdvancedArea> advancedAreaList;


    public String getMapDirectory() {
        return mapDirectory;
    }

    public void setMapDirectory(String mapDirectory) {
        this.mapDirectory = mapDirectory;
    }

    public SmapHeader getHeader() {
        return header;
    }

    public void setHeader(SmapHeader header) {
        this.header = header;
    }

    public List<Coordinate> getRssiPosList() {
        return rssiPosList;
    }

    public void setRssiPosList(List<Coordinate> rssiPosList) {
        this.rssiPosList = rssiPosList;
    }

    public List<Coordinate> getNormalPosList() {
        return normalPosList;
    }

    public void setNormalPosList(List<Coordinate> normalPosList) {
        this.normalPosList = normalPosList;
    }

    public List<Edge> getNormalLineList() {
        return normalLineList;
    }

    public void setNormalLineList(List<Edge> normalLineList) {
        this.normalLineList = normalLineList;
    }

    public List<AdvancedPoint> getAdvancedPointList() {
        return advancedPointList;
    }

    public void setAdvancedPointList(List<AdvancedPoint> advancedPointList) {
        this.advancedPointList = advancedPointList;
    }

    public List<AdvancedEdge> getAdvancedLineList() {
        return advancedLineList;
    }

    public void setAdvancedLineList(List<AdvancedEdge> advancedLineList) {
        this.advancedLineList = advancedLineList;
    }

    public List<AdvancedCurve> getAdvancedCurveList() {
        return advancedCurveList;
    }

    public void setAdvancedCurveList(List<AdvancedCurve> advancedCurveList) {
        this.advancedCurveList = advancedCurveList;
    }

    public List<AdvancedArea> getAdvancedAreaList() {
        return advancedAreaList;
    }

    public void setAdvancedAreaList(List<AdvancedArea> advancedAreaList) {
        this.advancedAreaList = advancedAreaList;
    }
}

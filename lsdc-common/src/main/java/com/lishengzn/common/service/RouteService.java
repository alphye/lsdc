package com.lishengzn.common.service;

import com.lishengzn.lsdc.entity.Coordinate;

import java.util.List;

public interface RouteService {
    void initReadMap();
    List<String> getRouteId(String startNodeId, String endNodeId);
    List<Coordinate> getRouteCoor(String startNodeId, String endNodeId);
}

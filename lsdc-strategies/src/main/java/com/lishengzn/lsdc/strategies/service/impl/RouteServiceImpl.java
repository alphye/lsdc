package com.lishengzn.lsdc.strategies.service.impl;

import com.lishengzn.lsdc.entity.Coordinate;
import com.lishengzn.lsdc.strategies.router.DefaultRouter;
import com.lishengzn.lsdc.strategies.router.ReadMapUtil;
import com.lishengzn.common.service.RouteService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RouteServiceImpl implements RouteService {
    @Override
    public synchronized void initReadMap() {
        if(!ReadMapUtil.initialized){
            ReadMapUtil.initialize();
        }
    }

    @Override
    public List<String> getRouteId(String startNodeId, String endNodeId) {
        if(!ReadMapUtil.initialized){
            ReadMapUtil.initialize();
        }
        return DefaultRouter.getRoute(startNodeId,endNodeId);
    }
    public List<Coordinate> getRouteCoor(String startNodeId, String endNodeId) {
        if(!ReadMapUtil.initialized){
            ReadMapUtil.initialize();
        }
        return DefaultRouter.getRoute2(startNodeId,endNodeId);
    }
}

package com.lishengzn.lsdc.kernel.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.lishengzn.common.service.RouteService;
import com.lishengzn.lsdc.kernel.service.DefaultRouteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultRouteServiceImpl implements DefaultRouteService {
    private static final Logger LOG = LoggerFactory.getLogger(DefaultRouteServiceImpl.class);
//    @Autowired
    private RouteService routeService;
    @Override
    public List<String> getRoute(String startNodeId, String endNodeId) {
        List<String> steps=routeService.getRouteId(startNodeId,endNodeId);
        LOG.debug("路径规划：{}", JSONObject.toJSONString(steps));
        return steps;
    }
}

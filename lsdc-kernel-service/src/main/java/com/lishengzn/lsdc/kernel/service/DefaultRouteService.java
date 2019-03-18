package com.lishengzn.lsdc.kernel.service;

import java.util.List;

public interface DefaultRouteService {
    List<String> getRoute(String startNodeId, String endNodeId);
}

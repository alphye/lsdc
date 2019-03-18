package com.lishengzn.lsdc.kernel.service.impl;

import com.lishengzn.common.service.VehicleControlService;
import com.lishengzn.common.service.VehiclePoolService;
import com.lishengzn.common.service.VehicleService;
import com.lishengzn.lsdc.kernel.service.DefaultRouteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class VehicleServiceImpl implements VehicleService {

    private static final Logger LOG = LoggerFactory.getLogger(VehicleServiceImpl.class);

    @Autowired
    private VehiclePoolService vehiclePoolService;

    @Autowired
    private DefaultRouteService defaultRouteService;

    @Autowired
    private VehicleControlService vehicleOnOffService;

    @Override
    public void vehicleOnline(String ip) {
        LOG.debug("车辆上线：{}",ip);
        vehicleOnOffService.vehicleOnline(ip);
    }

    @Override
    public void vehicleOffline(String ip) {
        LOG.debug("车辆下线：{}",ip);
        vehicleOnOffService.vehicleOffline(ip);
    }

    @Override
    public void allVehiclesOnline() {
        LOG.debug("所有配置车辆上线");
        vehicleOnOffService.allVehiclesOnline();
    }

    @Override
    public void allVehiclesOffline() {
        LOG.debug("所有配置车辆下线");
        vehicleOnOffService.allVehiclesOffline();
    }


    public void getRoute(String startNodeId, String endNodeId){
        defaultRouteService.getRoute(startNodeId, endNodeId);
    }
}

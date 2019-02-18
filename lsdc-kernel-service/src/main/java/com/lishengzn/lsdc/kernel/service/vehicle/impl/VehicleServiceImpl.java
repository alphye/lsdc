package com.lishengzn.lsdc.kernel.service.vehicle.impl;

import com.lishengzn.common.entity.Vehicle;
import com.lishengzn.common.service.VehicleOnOffService;
import com.lishengzn.common.service.VehiclePoolService;
import com.lishengzn.common.service.VehicleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

public class VehicleServiceImpl implements VehicleService {

    private static final Logger LOG = LoggerFactory.getLogger(VehicleServiceImpl.class);

    @Autowired
    private VehiclePoolService vehiclePoolService;

    @Autowired()
    private VehicleOnOffService vehicleOnOffService_status;

    @Autowired
    private VehicleOnOffService vehicleOnOffService_navi;

    @Override
    public void vehicleOnline(String ip) {
        LOG.debug("车辆上线：{}",ip);
        Vehicle vehicle =vehiclePoolService.createVehicle(ip);
        vehicleOnOffService_status.vehicleOnline(vehicle);
//        vehicleOnOffService_navi.vehicleOnline(vehicle);
    }

    @Override
    public void vehicleOffline(String ip) {
        LOG.debug("车辆下线：{}",ip);
        Vehicle vehicle =vehiclePoolService.removeVehicle(ip);
        vehicleOnOffService_status.vehicleOffline(vehicle);
//        vehicleOnOffService_navi.vehicleOffline(vehicle);
    }

    @Override
    public void allVehiclesOnline() {
        LOG.debug("所有配置车辆上线");
        List<String> vehicleIps = vehiclePoolService.getVehicleIps();
        List<Vehicle> vehicles = vehiclePoolService.createVehicles(vehicleIps);
        vehicleOnOffService_status.allVehiclesOnline(vehicles);
        vehicleOnOffService_navi.allVehiclesOnline(vehicles);
    }

    @Override
    public void allVehiclesOffline() {
        LOG.debug("所有配置车辆下线");
        List<Vehicle> vehicles = vehiclePoolService.getVehicles((vehicle)-> true);
        vehicles.forEach((vehicle)->{
            vehiclePoolService.removeVehicle(vehicle.getVehicleIp());
        });
        vehicleOnOffService_status.allVehiclesOffline(vehicles);
        vehicleOnOffService_navi.allVehiclesOffline(vehicles);
    }
}

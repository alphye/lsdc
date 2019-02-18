package com.lishengzn.lsdc.kernel.status.vehicle.service.impl;

import com.lishengzn.common.entity.Vehicle;
import com.lishengzn.common.pool.ObjectPool;
import com.lishengzn.common.service.VehiclePoolService;
import com.lishengzn.common.service.VehicleOnOffService;
import com.lishengzn.common.socket.ClientOfVehicle;
import com.lishengzn.lsdc.kernel.status.vehicle.ClientOfVehicleFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class VehicleOnOffServiceImpl implements VehicleOnOffService {
    private static final Logger LOG = LoggerFactory.getLogger(VehicleOnOffServiceImpl.class);

//    @Autowired
    private VehiclePoolService vehiclePoolService;
    @Override
    public void vehicleOnline(Vehicle vehicle){
        LOG.debug("车辆上线：{}",vehicle.getVehicleIp());
        ClientOfVehicleFactory.createStatusAPIClient(vehicle);
    }

    @Override
    public void allVehiclesOnline(List<Vehicle> vehicles) {
        LOG.debug("所有车辆上线");

        for(Vehicle vehicle : vehicles){
            vehicleOnline(vehicle);
        }
        System.gc();
    }

    @Override
    public void vehicleOffline(Vehicle vehicle) {
        LOG.info("车辆下线：{}",vehicle.getVehicleIp());
        Objects.requireNonNull(ObjectPool.getClientOfVehicle(vehicle.getVehicleIp(), ClientOfVehicle.ClientType.statusAPi)).close();
    }

    @Override
    public void allVehiclesOffline(List<Vehicle> vehicles) {
        LOG.debug("所有车辆下线");
        for(Vehicle vehicle : vehicles){
            vehicleOffline(vehicle);
        }
        System.gc();
    }
}

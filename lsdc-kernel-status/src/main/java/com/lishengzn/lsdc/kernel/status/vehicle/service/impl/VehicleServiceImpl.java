package com.lishengzn.lsdc.kernel.status.vehicle.service.impl;

import com.lishengzn.common.pool.ObjectPool;
import com.lishengzn.common.service.VehicleService;
import com.lishengzn.common.socket.ClientOfVehicle;
import com.lishengzn.lsdc.kernel.status.vehicle.ClientOfVehicleFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class VehicleServiceImpl implements VehicleService {
    private static final Logger LOG = LoggerFactory.getLogger(VehicleServiceImpl.class);

    @Override
    public void vehicleOnline(String ip){
        LOG.info("车辆上线：{}",ip);
        ClientOfVehicleFactory.createStatusAPIClient(ip);
    }

    @Override
    public void allVehiclesOnline() {
        System.gc();
        List<String> ips = ObjectPool.getVehicleIps();
        for(String ip : ips){
            vehicleOnline(ip);
        }
    }

    @Override
    public void vehicleOffline(String ip) {
        LOG.info("车辆下线：{}",ip);
        Objects.requireNonNull(ObjectPool.getClientOfVehicle(ip, ClientOfVehicle.ClientType.statusAPi)).close();
    }

    @Override
    public void allVehiclesOffline() {
        List<String> ips = ObjectPool.getVehicleIps();
        for(String ip : ips){
            vehicleOffline(ip);
        }
        System.gc();
    }
}

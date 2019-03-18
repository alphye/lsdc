package com.lishengzn.lsdc.communication.listener;

import com.lishengzn.lsdc.communication.vehicle.service.impl.VehiclePoolServiceImpl;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class LoadOnStartup implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent event) {
        System.out.println("============================read vehicleIP config===========================");
        VehiclePoolServiceImpl.readVehicleIps();
    }
}

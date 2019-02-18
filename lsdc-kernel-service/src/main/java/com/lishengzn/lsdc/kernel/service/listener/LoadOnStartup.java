package com.lishengzn.lsdc.kernel.service.listener;

import com.lishengzn.lsdc.kernel.service.object.impl.VehiclePoolServiceImpl;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class LoadOnStartup implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent event) {
        System.out.println("============================read vehicleIP config===========================");
        VehiclePoolServiceImpl.readVehicleIps();
    }
}

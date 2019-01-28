package com.lishengzn.lsdc.kernel.status.listener;

import com.lishengzn.common.pool.ObjectPool;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class LoadOnStartup implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent event) {
        System.out.println("============================read vehicleIP config===========================");
        ObjectPool.readVehicleIps();
    }
}

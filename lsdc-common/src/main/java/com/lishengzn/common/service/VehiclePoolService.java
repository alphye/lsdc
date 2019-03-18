package com.lishengzn.common.service;

import com.lishengzn.common.entity.Vehicle;
import java.util.List;
import java.util.function.Predicate;
/**
 * 车辆管理池
 */
public interface VehiclePoolService {

    Vehicle createVehicle(String ip);

    List<Vehicle> createVehicles(List<String> ips);

    void addVehicle(Vehicle vehicle);

     Vehicle removeVehicle(String ip);

     Vehicle getVehicle(String ip);

    Vehicle getCloneVehicle(String ip);

     List<Vehicle> getVehicles(Predicate<Vehicle> predicate);

      List<String> getVehicleIps();
}

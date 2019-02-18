package com.lishengzn.common.service;

import com.lishengzn.common.entity.Vehicle;

import java.util.List;

public interface VehicleOnOffService {
    void vehicleOnline(Vehicle vehicle);
    void vehicleOffline(Vehicle vehicle);
    void allVehiclesOnline(List<Vehicle> vehicles);
    void allVehiclesOffline(List<Vehicle> vehicles);
}

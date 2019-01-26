package com.lishengzn.common.service;

public interface VehicleService {
    void vehicleOnline(String ip);
    void allVehiclesOnline();
    void vehicleOffline(String ip);
    void allVehiclesOffline();
}

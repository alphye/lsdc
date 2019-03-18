package com.lishengzn.common.service;

public interface VehicleControlService {
    void vehicleOnline(String ip);
    void vehicleOffline(String ip);
    void allVehiclesOnline();
    void allVehiclesOffline();
    void showVehicle();
    void showBlockStatus();
}

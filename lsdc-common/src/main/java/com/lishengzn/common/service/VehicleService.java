package com.lishengzn.common.service;


public interface VehicleService {
    void vehicleOnline(String ip);
    void vehicleOffline(String ip);
    void allVehiclesOnline();
    void allVehiclesOffline();
    void getRoute(String startNodeId, String endNodeId);
}

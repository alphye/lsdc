package com.lishengzn.lsdc.communication.vehicle.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.lishengzn.common.entity.Vehicle;
import com.lishengzn.common.entity.response.statusapi.ResponseBlock;
import com.lishengzn.common.pool.ObjectPool;
import com.lishengzn.common.service.VehicleControlService;
import com.lishengzn.common.service.VehiclePoolService;
import com.lishengzn.common.socket.ClientOfVehicle;
import com.lishengzn.lsdc.communication.service.APIStatusMessageSenderService;
import com.lishengzn.lsdc.communication.vehicle.ClientOfVehicleFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

public class VehicleControlServiceImpl implements VehicleControlService {
    private static final Logger LOG = LoggerFactory.getLogger(VehicleControlServiceImpl.class);

    @Autowired
    private VehiclePoolService vehiclePoolService;
    @Override
    public void vehicleOnline(String ip){
        LOG.debug("车辆上线：{}",ip);
        Vehicle vehicle =vehiclePoolService.createVehicle(ip);
        ClientOfVehicle client_status = ClientOfVehicleFactory.createStatusAPIClient(vehicle);
        ObjectPool.addClientOfVehicle(vehicle.getVehicleIp(),client_status, client_status.getClientType());
        client_status .initialize();

        ClientOfVehicle client_navi = ClientOfVehicleFactory.createNaviClient(vehicle);
        ObjectPool.addClientOfVehicle(vehicle.getVehicleIp(),client_navi, client_navi.getClientType());
        client_navi .initialize();
    }

    @Override
    public void allVehiclesOnline() {
        List<String> ips = vehiclePoolService.getVehicleIps();
        LOG.debug("所有车辆上线");
        for(String  ip : ips){
            vehicleOnline(ip);
        }
        System.gc();
    }

    @Override
    public void vehicleOffline(String ip) {
        LOG.info("车辆下线：{}",ip);
        vehiclePoolService.removeVehicle(ip);
        ClientOfVehicle client_status =ObjectPool.getClientOfVehicle(ip, ClientOfVehicle.ClientType.statusAPi);
        if(client_status != null){
            client_status.close();
        }
        ClientOfVehicle client_navi =ObjectPool.getClientOfVehicle(ip, ClientOfVehicle.ClientType.vehicleNavi);
        if(client_navi != null){
            client_navi.close();
        }
    }

    @Override
    public void allVehiclesOffline() {
        List<String> ips = vehiclePoolService.getVehicleIps();
        LOG.debug("所有车辆下线");
        for(String ip : ips){
            vehicleOffline(ip);
        }
        System.gc();
    }
    @Override
    public void showVehicle(){
        Vehicle vehicle = vehiclePoolService.getVehicle("127.0.0.1");
        LOG.debug("vehicle:{}", JSONObject.toJSONString(vehicle));
    }

    @Override
    public void showBlockStatus(){
        APIStatusMessageSenderService senderService = (APIStatusMessageSenderService)ObjectPool.getClientOfVehicle("127.0.0.1", ClientOfVehicle.ClientType.statusAPi).getMessageSenderService();
        ResponseBlock block =senderService.queryBlockStatus();
        LOG.debug("block status:{}", JSONObject.toJSONString(block));
    }
}

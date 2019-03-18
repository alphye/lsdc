package com.lishengzn.lsdc.communication.vehicle;

import com.lishengzn.common.communication.AbstractCommunicateAdapter;
import com.lishengzn.common.constants.SocketConstants;
import com.lishengzn.common.entity.Vehicle;
import com.lishengzn.common.socket.ClientOfVehicle;
import com.lishengzn.lsdc.communication.service.impl.NaviReceiveServiceImpl;
import com.lishengzn.lsdc.communication.service.impl.NaviSenderServiceImpl;
import com.lishengzn.lsdc.communication.service.impl.APIStatusReceiveServiceImpl;
import com.lishengzn.lsdc.communication.service.impl.APIStatusSenderServiceImpl;
import com.lishengzn.common.pool.ObjectPool;

public class ClientOfVehicleFactory {
    public static ClientOfVehicle createStatusAPIClient(Vehicle vehicle){
        ClientOfVehicle.ClientType clientType = ClientOfVehicle.ClientType.statusAPi;
        ClientOfVehicle lastClient=null;
        if((lastClient = ObjectPool.getClientOfVehicle(vehicle.getVehicleIp(), clientType))!=null){
            lastClient.close();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        AbstractCommunicateAdapter messageSendService = new APIStatusSenderServiceImpl();
        AbstractCommunicateAdapter messageReceiveService = new APIStatusReceiveServiceImpl();
        ClientOfVehicle client = new ClientOfVehicle(vehicle.getVehicleIp(), SocketConstants.SERVER_PORT_STATUS_API,messageSendService,messageReceiveService,clientType,vehicle);
        return  client;
    }

    public static ClientOfVehicle createNaviClient(Vehicle vehicle){
        ClientOfVehicle.ClientType clientType = ClientOfVehicle.ClientType.vehicleNavi;
        ClientOfVehicle lastClient=null;
        if((lastClient = ObjectPool.getClientOfVehicle(vehicle.getVehicleIp(), clientType))!=null){
            lastClient.close();
            try {
                Thread.sleep(101);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        AbstractCommunicateAdapter messageSendService = new NaviSenderServiceImpl();
        AbstractCommunicateAdapter messageReceiveService = new NaviReceiveServiceImpl();
        ClientOfVehicle client = new ClientOfVehicle(vehicle.getVehicleIp(), SocketConstants.SERVER_PORT_VEHICLE_NAVI,messageSendService,messageReceiveService,clientType,vehicle);
        return  client;
    }
}

package com.lishengzn.lsdc.kernel.navi.vehicle;

import com.lishengzn.common.communication.AbstractCommunicateAdapter;
import com.lishengzn.common.constants.SocketConstants;
import com.lishengzn.common.entity.Vehicle;
import com.lishengzn.common.socket.ClientOfVehicle;
import com.lishengzn.lsdc.kernel.navi.communication.service.impl.NaviReceiveServiceImpl;
import com.lishengzn.lsdc.kernel.navi.communication.service.impl.NaviSenderServiceImpl;
import com.lishengzn.common.pool.ObjectPool;

public class ClientOfVehicleFactory {
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
        ObjectPool.addClientOfVehicle(vehicle.getVehicleIp(),client, clientType);
        client .initialize();
        return  client;
    }
}

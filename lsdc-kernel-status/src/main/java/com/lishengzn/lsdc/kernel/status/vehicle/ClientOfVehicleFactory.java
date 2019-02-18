package com.lishengzn.lsdc.kernel.status.vehicle;

import com.lishengzn.common.communication.AbstractCommunicateAdapter;
import com.lishengzn.common.constants.SocketConstants;
import com.lishengzn.common.entity.Vehicle;
import com.lishengzn.common.socket.ClientOfVehicle;
import com.lishengzn.lsdc.kernel.status.communication.service.impl.StatusAPIReceiveServiceImpl;
import com.lishengzn.lsdc.kernel.status.communication.service.impl.StatusAPISenderServiceImpl;
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
        AbstractCommunicateAdapter messageSendService = new StatusAPISenderServiceImpl();
        AbstractCommunicateAdapter messageReceiveService = new StatusAPIReceiveServiceImpl();
        ClientOfVehicle client = new ClientOfVehicle(vehicle.getVehicleIp(), SocketConstants.SERVER_PORT_STATUS_API,messageSendService,messageReceiveService,clientType,vehicle);
        ObjectPool.addClientOfVehicle(vehicle.getVehicleIp(),client, clientType);
        client .initialize();
        return  client;
    }
}

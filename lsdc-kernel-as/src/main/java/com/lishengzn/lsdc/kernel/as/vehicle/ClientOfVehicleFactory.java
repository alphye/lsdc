package com.lishengzn.lsdc.kernel.as.vehicle;

import com.lishengzn.common.communication.AbstractCommunicateAdapter;
import com.lishengzn.common.constants.SocketConstants;
import com.lishengzn.common.socket.ClientOfVehicle;
import com.lishengzn.lsdc.kernel.as.communication.service.impl.StatusAPIReceiveServiceImpl;
import com.lishengzn.lsdc.kernel.as.communication.service.impl.StatusAPISenderServiceImpl;
import com.lishengzn.common.pool.ObjectPool;

public class ClientOfVehicleFactory {
    public static ClientOfVehicle createStatusAPIClient(String ip){
        ClientOfVehicle.ClientType clientType = ClientOfVehicle.ClientType.statusAPi;
        ClientOfVehicle lastClient=null;
        if((lastClient = ObjectPool.getClientOfVehicle(ip, clientType))!=null){
            lastClient.close();
        }
        AbstractCommunicateAdapter messageSendService = new StatusAPISenderServiceImpl();
        AbstractCommunicateAdapter messageReceiveService = new StatusAPIReceiveServiceImpl();
        ClientOfVehicle client = new ClientOfVehicle(ip, SocketConstants.SERVER_PORT_STATUS_API,messageSendService,messageReceiveService,clientType);
        client .initialize();
        ObjectPool.addClientOfVehicle(ip,client, clientType);
        return  client;
    }
}

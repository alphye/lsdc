package com.lishengzn.lsdc.kernel.navi.vehicle;

import com.lishengzn.common.communication.AbstractCommunicateAdapter;
import com.lishengzn.common.constants.SocketConstants;
import com.lishengzn.common.entity.Vehicle;
import com.lishengzn.common.socket.ClientOfVehicle;
import com.lishengzn.lsdc.kernel.navi.communication.service.impl.NaviReceiveServiceImpl;
import com.lishengzn.lsdc.kernel.navi.communication.service.impl.NaviSenderServiceImpl;
import com.lishengzn.common.pool.ObjectPool;

public class ClientOfVehicleFactory {
    public static ClientOfVehicle createStatusAPIClient(String ip){
        ClientOfVehicle.ClientType clientType = ClientOfVehicle.ClientType.statusAPi;
        ClientOfVehicle lastClient=null;
        if((lastClient = ObjectPool.getClientOfVehicle(ip, clientType))!=null){
            lastClient.close();
        }
        AbstractCommunicateAdapter messageSendService = new NaviSenderServiceImpl();
        AbstractCommunicateAdapter messageReceiveService = new NaviReceiveServiceImpl();
        Vehicle vehicle = ObjectPool.createVehicle(ip);
        ClientOfVehicle client = new ClientOfVehicle(ip, SocketConstants.SERVER_PORT_STATUS_API,messageSendService,messageReceiveService,clientType,vehicle);
        client .initialize();
        ObjectPool.addClientOfVehicle(ip,client, clientType);
        return  client;
    }
}
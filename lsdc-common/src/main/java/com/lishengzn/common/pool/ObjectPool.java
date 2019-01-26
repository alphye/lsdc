package com.lishengzn.common.pool;

import com.lishengzn.common.socket.ClientOfVehicle;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ObjectPool {
    public static final ExecutorService messageReceiveThreadPool = Executors.newCachedThreadPool();
    public static final ExecutorService messageSendThreadPool = Executors.newCachedThreadPool();
    private static final Map<String, ClientOfVehicle> CLIENTOFVEHICLEPOOL_STATUSAPI = new ConcurrentHashMap<>();
    public static final List<String> vehicleIps = new ArrayList<>();
    public static void addClientOfVehicle(String key, ClientOfVehicle client, ClientOfVehicle.ClientType clientType){
        if(clientType.equals(ClientOfVehicle.ClientType.statusAPi)){
            CLIENTOFVEHICLEPOOL_STATUSAPI.put(key,client);
        }
    }
    public static ClientOfVehicle removeClientOfVehicle(String key, ClientOfVehicle.ClientType clientType){
        if(clientType.equals(ClientOfVehicle.ClientType.statusAPi)){
           return CLIENTOFVEHICLEPOOL_STATUSAPI.remove(key);
        }
        return null;
    }

    public static ClientOfVehicle getClientOfVehicle(String key, ClientOfVehicle.ClientType clientType){
        if(clientType.equals(ClientOfVehicle.ClientType.statusAPi)){
            return CLIENTOFVEHICLEPOOL_STATUSAPI.get(key);
        }
        return null;
    }

    public static void readVehicleIps(){
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(ObjectPool.class.getClassLoader().getResourceAsStream("config/vehicleips")));
            String ip ="";
            while(!StringUtils.isEmpty(ip=bufferedReader.readLine())){
                vehicleIps.add(ip);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

package com.lishengzn.common.pool;

import com.lishengzn.common.socket.ClientOfVehicle;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.util.Objects.requireNonNull;

public class ObjectPool {
    /** 接收小车信息的线程池*/
    public static final ExecutorService messageReceiveThreadPool = Executors.newCachedThreadPool();

    /** 向小车发送信息的线程池*/
    public static final ExecutorService messageSendThreadPool = Executors.newCachedThreadPool();

    /** 连接小车的客户端池 status 端口*/
    private static final Map<String, ClientOfVehicle> CLIENTOFVEHICLEPOOL_STATUS = new ConcurrentHashMap<>();
    /** 连接小车的客户端池 navi 端口*/
    private static final Map<String, ClientOfVehicle> CLIENTOFVEHICLEPOOL_NAVI = new ConcurrentHashMap<>();


    /**向客户端池新增加一个元素
     * @param key 客户端的key
     * @param client 客户端
     * @param clientType 客户端端口类型
     */
    public static void addClientOfVehicle(String key, ClientOfVehicle client, ClientOfVehicle.ClientType clientType){
        requireNonNull(key);
        requireNonNull(client);
        requireNonNull(clientType);
        if(clientType.equals(ClientOfVehicle.ClientType.statusAPi)){
            CLIENTOFVEHICLEPOOL_STATUS.put(key,client);
        }
        else if(clientType.equals(ClientOfVehicle.ClientType.vehicleNavi)){
            CLIENTOFVEHICLEPOOL_NAVI.put(key,client);
        }
    }
    /**从客户端池删除一个元素
     * @param key 客户端的key
     * @param clientType 客户端端口类型
     */
    public static ClientOfVehicle removeClientOfVehicle(String key, ClientOfVehicle.ClientType clientType){
        requireNonNull(key);
        requireNonNull(clientType);
        if(clientType.equals(ClientOfVehicle.ClientType.statusAPi)){
           return CLIENTOFVEHICLEPOOL_STATUS.remove(key);
        }
        else if(clientType.equals(ClientOfVehicle.ClientType.vehicleNavi)){
            return CLIENTOFVEHICLEPOOL_NAVI.remove(key);
        }
        return null;
    }

    /**从客户端池获取一个元素
     * @param key 客户端的key
     * @param clientType 客户端端口类型
     */
    public static ClientOfVehicle getClientOfVehicle(String key, ClientOfVehicle.ClientType clientType){
        requireNonNull(key);
        requireNonNull(clientType);
        if(clientType.equals(ClientOfVehicle.ClientType.statusAPi)){
            return CLIENTOFVEHICLEPOOL_STATUS.get(key);
        }
        else if(clientType.equals(ClientOfVehicle.ClientType.vehicleNavi)){
            return CLIENTOFVEHICLEPOOL_NAVI.get(key);
        }
        return null;
    }
}

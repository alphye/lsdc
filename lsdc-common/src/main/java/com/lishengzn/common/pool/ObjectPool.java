package com.lishengzn.common.pool;

import com.lishengzn.common.entity.Vehicle;
import com.lishengzn.common.socket.ClientOfVehicle;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

public class ObjectPool {
    /** 接收小车信息的线程池*/
    public static final ExecutorService messageReceiveThreadPool = Executors.newCachedThreadPool();

    /** 向小车发送信息的线程池*/
    public static final ExecutorService messageSendThreadPool = Executors.newCachedThreadPool();

    /** 连接小车的客户端池*/
    private static final Map<String, ClientOfVehicle> CLIENTOFVEHICLEPOOL = new ConcurrentHashMap<>();


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
            CLIENTOFVEHICLEPOOL.put(key,client);
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
           return CLIENTOFVEHICLEPOOL.remove(key);
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
            return CLIENTOFVEHICLEPOOL.get(key);
        }
        return null;
    }
}

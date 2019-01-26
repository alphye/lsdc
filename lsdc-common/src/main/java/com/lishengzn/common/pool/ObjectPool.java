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
    private static final Map<String, ClientOfVehicle> CLIENTOFVEHICLEPOOL_STATUSAPI = new ConcurrentHashMap<>();

    /** 所有要连接的小车的IP*/
    private static final List<String> vehicleIps = new ArrayList<>();

    private static final Map<String,Vehicle> vehiclePool = new ConcurrentHashMap<>();
    private static final List<Vehicle> vehicleList = Collections.synchronizedList(new ArrayList<>());


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
            CLIENTOFVEHICLEPOOL_STATUSAPI.put(key,client);
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
           return CLIENTOFVEHICLEPOOL_STATUSAPI.remove(key);
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
            return CLIENTOFVEHICLEPOOL_STATUSAPI.get(key);
        }
        return null;
    }
    public static Vehicle createVehicle(String ip){
        requireNonNull(ip);
        Vehicle vehicle = new Vehicle();
        vehicle.setVehicleIp(ip);
        addVehicle(vehicle);
        return vehicle;
    }
    public static void addVehicle(Vehicle vehicle){
        requireNonNull(vehicle.getVehicleIp(),"车辆IP不能为空");
        vehiclePool.put(vehicle.getVehicleIp(),vehicle);
        vehicleList.add(vehicle);
    }

    public static Vehicle removeVehicle(String ip){
        requireNonNull(ip);
        Vehicle vehicle=vehiclePool.remove(ip);
        vehicleList.remove(vehicle);
        return vehicle;
    }
    public static Vehicle getVehicle(String ip){
        requireNonNull(ip);
        return vehiclePool.get(ip);
    }
    public static List<Vehicle> getVehicles(Predicate<Vehicle> predicate){
        requireNonNull(predicate);
        List<Vehicle> vehicles= vehicleList.stream().filter(predicate).collect(Collectors.toList());
        return vehicles;
    }
    /**
     * 读取已配置的所有小车IP
     */
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

    public static List<String> getVehicleIps(){
        // 这里返回一个新的list，防止外界拿到原list，对其操作。
        List<String> list =new ArrayList<String>();
        list.addAll(vehicleIps);
        return list;
    }
}

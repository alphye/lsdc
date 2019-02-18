package com.lishengzn.lsdc.kernel.service.object.impl;

import com.lishengzn.common.entity.Vehicle;
import com.lishengzn.common.pool.ObjectPool;
import com.lishengzn.common.service.VehiclePoolService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

@Service
public class VehiclePoolServiceImpl implements VehiclePoolService {

    /** 所有要连接的小车的IP*/
    private static final List<String> vehicleIps = new ArrayList<>();

    private static final Map<String,Vehicle> vehiclePool = new ConcurrentHashMap<>();
    private static final List<Vehicle> vehicleList = Collections.synchronizedList(new ArrayList<>());

    @Override
    public Vehicle createVehicle(String ip){
        requireNonNull(ip);
        Vehicle vehicle = new Vehicle();
        vehicle.setVehicleIp(ip);
        addVehicle(vehicle);
        return vehicle;
    }

    @Override
    public List<Vehicle> createVehicles(List<String> ips) {
        List<Vehicle> vehicles = vehicleIps.stream().map(this::createVehicle).collect(Collectors.toList());
        vehicles.forEach(this::addVehicle);
        return vehicles;
    }

    @Override
    public  void addVehicle(Vehicle vehicle){
        requireNonNull(vehicle.getVehicleIp(),"车辆IP不能为空");
        // 需判断list中是否已经有这个vehicle，如有，则要是删除
        Vehicle vehicleExists = vehiclePool.get(vehicle.getVehicleIp());
        vehiclePool.put(vehicle.getVehicleIp(),vehicle);
        if(vehicleExists!=null){
            vehicleList.remove(vehicleExists);
        }
        vehicleList.add(vehicle);
    }

    @Override
    public  Vehicle removeVehicle(String ip){
        requireNonNull(ip);
        Vehicle vehicle=vehiclePool.remove(ip);
        vehicleList.remove(vehicle);
        return vehicle;
    }

    @Override
    public  Vehicle getVehicle(String ip){
        requireNonNull(ip);
        return vehiclePool.get(ip);
    }

    @Override
    public  List<Vehicle> getVehicles(Predicate<Vehicle> predicate){
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

    @Override
    public  List<String> getVehicleIps(){
        // 这里返回一个新的list，防止外界拿到原list，对其操作。
        List<String> list =new ArrayList<String>();
        list.addAll(vehicleIps);
        return list;
    }
}

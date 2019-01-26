package com.lishengzn.common.entity.response.statusapi;

import com.lishengzn.common.entity.response.ResponseEntity;

/**
 * 回包实体：查询机器人信息
 */
public class ResponseVehicleInfo extends ResponseEntity {
    /**机器人 ID; 可缺省：是*/
    private String id;

    /**机器人名称; 可缺省：是*/
    private String vehicle_id;

    /**RoboKit 版本号; 可缺省：是*/
    private String version;

    /**机器人模型名; 可缺省：是*/
    private String model;

    /**固件版本号; 可缺省：是*/
    private String dsp_version;

    /**陀螺仪版本号; 可缺省：是*/
    private String gyro_version;

    /**地图版本号; 可缺省：是*/
    private String map_version;

    /**模型版本号; 可缺省：是*/
    private String model_version;

    /**网络协议版本号; 可缺省：是*/
    private String netprotocol_version;

    /**Modbus 协议版本号; 可缺省：是*/
    private String modbus_version;

    /**当前地图名; 可缺省：是*/
    private String current_map;

    /**当前地图 MD5 值; 可缺省：是*/
    private String current_map_md5;

    /**当前模型 MD5 值; 可缺省：是*/
    private String model_md5;

    /**当前连接 Wifi 的 SSID（机器人需要支持连接 Wifi 并且已经连接，否则为空）; 可缺省：是*/
    private String ssid;

    /**当前连接 Wifi 的信号强度，0-100 百分比（机器人需要支持连接 Wifi 并且已经连接，否则为0）; 可缺省：是*/
    private double rssi;

    /**当前 IP（除 192.168.192.5 的另一个 IP 地址）; 可缺省：是*/
    private String current_ip;


    public ResponseVehicleInfo() {
    }

    public ResponseVehicleInfo(String id, String vehicle_id, String version, String model, String dsp_version, String gyro_version, String map_version, String model_version, String netprotocol_version, String modbus_version, String current_map, String current_map_md5, String model_md5, String ssid, double rssi, String current_ip, int ret_code, String err_msg) {
        this.id = id;
        this.vehicle_id = vehicle_id;
        this.version = version;
        this.model = model;
        this.dsp_version = dsp_version;
        this.gyro_version = gyro_version;
        this.map_version = map_version;
        this.model_version = model_version;
        this.netprotocol_version = netprotocol_version;
        this.modbus_version = modbus_version;
        this.current_map = current_map;
        this.current_map_md5 = current_map_md5;
        this.model_md5 = model_md5;
        this.ssid = ssid;
        this.rssi = rssi;
        this.current_ip = current_ip;
        this.ret_code = ret_code;
        this.err_msg = err_msg;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVehicle_id() {
        return vehicle_id;
    }

    public void setVehicle_id(String vehicle_id) {
        this.vehicle_id = vehicle_id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getDsp_version() {
        return dsp_version;
    }

    public void setDsp_version(String dsp_version) {
        this.dsp_version = dsp_version;
    }

    public String getGyro_version() {
        return gyro_version;
    }

    public void setGyro_version(String gyro_version) {
        this.gyro_version = gyro_version;
    }

    public String getMap_version() {
        return map_version;
    }

    public void setMap_version(String map_version) {
        this.map_version = map_version;
    }

    public String getModel_version() {
        return model_version;
    }

    public void setModel_version(String model_version) {
        this.model_version = model_version;
    }

    public String getNetprotocol_version() {
        return netprotocol_version;
    }

    public void setNetprotocol_version(String netprotocol_version) {
        this.netprotocol_version = netprotocol_version;
    }

    public String getModbus_version() {
        return modbus_version;
    }

    public void setModbus_version(String modbus_version) {
        this.modbus_version = modbus_version;
    }

    public String getCurrent_map() {
        return current_map;
    }

    public void setCurrent_map(String current_map) {
        this.current_map = current_map;
    }

    public String getCurrent_map_md5() {
        return current_map_md5;
    }

    public void setCurrent_map_md5(String current_map_md5) {
        this.current_map_md5 = current_map_md5;
    }

    public String getModel_md5() {
        return model_md5;
    }

    public void setModel_md5(String model_md5) {
        this.model_md5 = model_md5;
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public double getRssi() {
        return rssi;
    }

    public void setRssi(double rssi) {
        this.rssi = rssi;
    }

    public String getCurrent_ip() {
        return current_ip;
    }

    public void setCurrent_ip(String current_ip) {
        this.current_ip = current_ip;
    }

}

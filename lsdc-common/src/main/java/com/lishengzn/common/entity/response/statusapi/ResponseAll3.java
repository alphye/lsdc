package com.lishengzn.common.entity.response.statusapi;

import com.lishengzn.common.entity.response.ResponseEntity;

public class ResponseAll3 extends ResponseEntity {
    /**累计行驶里程, 单位 m; 可缺省：是*/
    private double odo;

    /**本次运行时间(开机后到当前的时间), 单位 ms; 可缺省：是*/
    private long time;

    /**累计运行时间, 单位 ms; 可缺省：是*/
    private long total_time;

    /**控制器温度, 单位 ℃; 可缺省：是*/
    private double controller_temp;

    /**控制器湿度, 单位 %; 可缺省：是*/
    private double controller_humi;

    /**控制器电压, 单位 V; 可缺省：是*/
    private double controller_voltage;

    /**机器人电池电量, 范围 [0, 1]; 可缺省：是*/
    private double battery_level;

    /**机器人电池温度, 单位 ℃; 可缺省：是*/
    private double battery_temp;

    /**电池是否正在充电; 可缺省：是*/
    private boolean charging;

    /**电压, 单位 V; 可缺省：是*/
    private double voltage;

    /**电流, 单位 A; 可缺省：是*/
    private double current;

    /**是否在手动充电(仅SRC-2000支持); 可缺省：是*/
    private boolean manual_charge;

    /**是否在自动充电(仅SRC-2000支持); 可缺省：是*/
    private boolean auto_charge;

    /**电池循环次数; 可缺省：是*/
    private int battery_cycle;

    /** 0 = FAILED(定位失败), 1 = SUCCESS(定位正确), 2 = RELOCING(正在重定位), 3=COMPLETED(定位完成)*/
    private int reloc_status;

    /** 0 = FAILED(载入地图失败), 1 = SUCCESS(载入地图成功), 2 = LOADING(正在载入地图)*/
    private int loadmap_status;

    /** 0 = 没有扫图, 1 = 正在扫图, 2 = 正在实时扫图*/
    private int slam_status;


    public double getOdo() {
        return odo;
    }

    public void setOdo(double odo) {
        this.odo = odo;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getTotal_time() {
        return total_time;
    }

    public void setTotal_time(long total_time) {
        this.total_time = total_time;
    }

    public double getController_temp() {
        return controller_temp;
    }

    public void setController_temp(double controller_temp) {
        this.controller_temp = controller_temp;
    }

    public double getController_humi() {
        return controller_humi;
    }

    public void setController_humi(double controller_humi) {
        this.controller_humi = controller_humi;
    }

    public double getController_voltage() {
        return controller_voltage;
    }

    public void setController_voltage(double controller_voltage) {
        this.controller_voltage = controller_voltage;
    }

    public double getBattery_level() {
        return battery_level;
    }

    public void setBattery_level(double battery_level) {
        this.battery_level = battery_level;
    }

    public double getBattery_temp() {
        return battery_temp;
    }

    public void setBattery_temp(double battery_temp) {
        this.battery_temp = battery_temp;
    }

    public boolean isCharging() {
        return charging;
    }

    public void setCharging(boolean charging) {
        this.charging = charging;
    }

    public double getVoltage() {
        return voltage;
    }

    public void setVoltage(double voltage) {
        this.voltage = voltage;
    }

    public double getCurrent() {
        return current;
    }

    public void setCurrent(double current) {
        this.current = current;
    }

    public boolean isManual_charge() {
        return manual_charge;
    }

    public void setManual_charge(boolean manual_charge) {
        this.manual_charge = manual_charge;
    }

    public boolean isAuto_charge() {
        return auto_charge;
    }

    public void setAuto_charge(boolean auto_charge) {
        this.auto_charge = auto_charge;
    }

    public int getBattery_cycle() {
        return battery_cycle;
    }

    public void setBattery_cycle(int battery_cycle) {
        this.battery_cycle = battery_cycle;
    }

    public int getReloc_status() {
        return reloc_status;
    }

    public void setReloc_status(int reloc_status) {
        this.reloc_status = reloc_status;
    }

    public int getLoadmap_status() {
        return loadmap_status;
    }

    public void setLoadmap_status(int loadmap_status) {
        this.loadmap_status = loadmap_status;
    }

    public int getSlam_status() {
        return slam_status;
    }

    public void setSlam_status(int slam_status) {
        this.slam_status = slam_status;
    }
}

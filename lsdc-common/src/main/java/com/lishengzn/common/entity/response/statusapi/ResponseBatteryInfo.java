package com.lishengzn.common.entity.response.statusapi;

import com.lishengzn.common.entity.response.ResponseEntity;

public class ResponseBatteryInfo extends ResponseEntity {
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
}

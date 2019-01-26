package com.lishengzn.common.entity.response.statusapi;

import com.lishengzn.common.entity.response.ResponseEntity;

public class ResponseVehicleRunInfo extends ResponseEntity {
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
}

package com.lishengzn.common.entity.response.statusapi;

public class LaserDeviceInfo {
    private String device_name;
    private double max_angle;
    private double max_range;
    private double min_angle;
    private double min_range;
    private double pub_step;
    private double real_step;
    private double scan_freq;
    private long time_increment;

    public String getDevice_name() {
        return device_name;
    }

    public void setDevice_name(String device_name) {
        this.device_name = device_name;
    }

    public double getMax_angle() {
        return max_angle;
    }

    public void setMax_angle(double max_angle) {
        this.max_angle = max_angle;
    }

    public double getMax_range() {
        return max_range;
    }

    public void setMax_range(double max_range) {
        this.max_range = max_range;
    }

    public double getMin_angle() {
        return min_angle;
    }

    public void setMin_angle(double min_angle) {
        this.min_angle = min_angle;
    }

    public double getMin_range() {
        return min_range;
    }

    public void setMin_range(double min_range) {
        this.min_range = min_range;
    }

    public double getPub_step() {
        return pub_step;
    }

    public void setPub_step(double pub_step) {
        this.pub_step = pub_step;
    }

    public double getReal_step() {
        return real_step;
    }

    public void setReal_step(double real_step) {
        this.real_step = real_step;
    }

    public double getScan_freq() {
        return scan_freq;
    }

    public void setScan_freq(double scan_freq) {
        this.scan_freq = scan_freq;
    }

    public long getTime_increment() {
        return time_increment;
    }

    public void setTime_increment(long time_increment) {
        this.time_increment = time_increment;
    }
}

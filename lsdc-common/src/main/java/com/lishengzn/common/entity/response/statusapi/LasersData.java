package com.lishengzn.common.entity.response.statusapi;

import java.util.List;

public class LasersData {
    List<LaserBeamData> beams;
    LaserDeviceInfo device_info;
    LaserHeader header;
    LaserInstall_info install_info;

    public List<LaserBeamData> getBeams() {
        return beams;
    }

    public void setBeams(List<LaserBeamData> beams) {
        this.beams = beams;
    }

    public LaserDeviceInfo getDevice_info() {
        return device_info;
    }

    public void setDevice_info(LaserDeviceInfo device_info) {
        this.device_info = device_info;
    }

    public LaserHeader getHeader() {
        return header;
    }

    public void setHeader(LaserHeader header) {
        this.header = header;
    }

    public LaserInstall_info getInstall_info() {
        return install_info;
    }

    public void setInstall_info(LaserInstall_info install_info) {
        this.install_info = install_info;
    }
}

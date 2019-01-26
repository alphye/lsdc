package com.lishengzn.common.entity.response.statusapi;

import com.lishengzn.common.entity.response.ResponseEntity;

import java.util.List;

public class ResponseLasersInfo extends ResponseEntity {
    /** 激光点云数据*/
    private List<LasersData> lasers;

    public List<LasersData> getLasers() {
        return lasers;
    }

    public void setLasers(List<LasersData> lasers) {
        this.lasers = lasers;
    }
}

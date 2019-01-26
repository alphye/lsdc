package com.lishengzn.common.entity.response.statusapi;

import com.lishengzn.common.entity.response.ResponseEntity;

public class ResponseSlamStatus extends ResponseEntity {

    /** 0 = 没有扫图, 1 = 正在扫图, 2 = 正在实时扫图*/
    private int slam_status;

    public int getSlam_status() {
        return slam_status;
    }

    public void setSlam_status(int slam_status) {
        this.slam_status = slam_status;
    }
}

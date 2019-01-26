package com.lishengzn.common.entity.response.statusapi;

import com.lishengzn.common.entity.response.ResponseEntity;

public class ResponseLoadMapStatus extends ResponseEntity {

    /** 0 = FAILED(载入地图失败), 1 = SUCCESS(载入地图成功), 2 = LOADING(正在载入地图)*/
    private int loadmap_status;

    public int getLoadmap_status() {
        return loadmap_status;
    }

    public void setLoadmap_status(int loadmap_status) {
        this.loadmap_status = loadmap_status;
    }
}

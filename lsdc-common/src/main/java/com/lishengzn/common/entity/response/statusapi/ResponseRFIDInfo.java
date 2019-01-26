package com.lishengzn.common.entity.response.statusapi;

import com.lishengzn.common.entity.response.ResponseEntity;

import java.util.List;

public class ResponseRFIDInfo extends ResponseEntity {
    /** 扫描到的所有 RFID 标签 id, 如果没扫描到 RFID 标签, 则为空数组*/
    private List<Integer> rfids;

    public List<Integer> getRfids() {
        return rfids;
    }

    public void setRfids(List<Integer> rfids) {
        this.rfids = rfids;
    }
}

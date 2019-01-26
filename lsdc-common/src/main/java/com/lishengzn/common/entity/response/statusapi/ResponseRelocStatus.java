package com.lishengzn.common.entity.response.statusapi;

import com.lishengzn.common.entity.response.ResponseEntity;

public class ResponseRelocStatus extends ResponseEntity {

    /** 0 = FAILED(定位失败), 1 = SUCCESS(定位正确), 2 = RELOCING(正在重定位), 3=COMPLETED(定位完成)*/
    private int reloc_status;

    public int getReloc_status() {
        return reloc_status;
    }

    public void setReloc_status(int reloc_status) {
        this.reloc_status = reloc_status;
    }
}

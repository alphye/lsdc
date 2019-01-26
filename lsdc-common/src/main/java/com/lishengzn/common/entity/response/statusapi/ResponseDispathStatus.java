package com.lishengzn.common.entity.response.statusapi;

import com.lishengzn.common.entity.response.ResponseEntity;

public class ResponseDispathStatus extends ResponseEntity {

    /** 0 = 单机模式, 1 = 调度模式, 2 = 调度模式但与调度系统失去连接*/
    private int dispatch_mode;

    public int getDispatch_mode() {
        return dispatch_mode;
    }

    public void setDispatch_mode(int dispatch_mode) {
        this.dispatch_mode = dispatch_mode;
    }
}

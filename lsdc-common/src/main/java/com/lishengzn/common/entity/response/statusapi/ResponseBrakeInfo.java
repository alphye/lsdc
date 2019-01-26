package com.lishengzn.common.entity.response.statusapi;

import com.lishengzn.common.entity.response.ResponseEntity;

public class ResponseBrakeInfo extends ResponseEntity {
    /**机器人是否抱闸, 如果该字段不存在, 说明机器人没有抱闸功能; 可缺省：是*/
    private boolean brake;

    public boolean isBrake() {
        return brake;
    }

    public void setBrake(boolean brake) {
        this.brake = brake;
    }
}

package com.lishengzn.common.entity.response.statusapi;

import com.lishengzn.common.entity.response.ResponseEntity;

public class ResponseJackStatus extends ResponseEntity {
    /**系统状态, 0=急停状态, 1=正常运行; 可缺省：是*/
    private int jack_mode;

    /**顶升状态, 0=上升中, 1=上升到位, 2=下降中, 3=下降到位, 4=停止; 可缺省：是*/
    private int jack_state;

    /**是否启用且连接成功; 可缺省：是*/
    private boolean jack_enable;

    /**错误码; 可缺省：是*/
    private int jack_error_code;

    /**顶升机构上是否有料, true = 有料, false = 空; 可缺省：是*/
    private boolean jack_isFull;

    /**顶升机构电机速率, 取值范围 30 ~ 100 超过 100 按 100 计算，低于 30 按 30 计算; 可缺省：*/
    private double jack_speed_level;

    public int getJack_mode() {
        return jack_mode;
    }

    public void setJack_mode(int jack_mode) {
        this.jack_mode = jack_mode;
    }

    public int getJack_state() {
        return jack_state;
    }

    public void setJack_state(int jack_state) {
        this.jack_state = jack_state;
    }

    public boolean isJack_enable() {
        return jack_enable;
    }

    public void setJack_enable(boolean jack_enable) {
        this.jack_enable = jack_enable;
    }

    public int getJack_error_code() {
        return jack_error_code;
    }

    public void setJack_error_code(int jack_error_code) {
        this.jack_error_code = jack_error_code;
    }

    public boolean isJack_isFull() {
        return jack_isFull;
    }

    public void setJack_isFull(boolean jack_isFull) {
        this.jack_isFull = jack_isFull;
    }

    public double getJack_speed_level() {
        return jack_speed_level;
    }

    public void setJack_speed_level(double jack_speed_level) {
        this.jack_speed_level = jack_speed_level;
    }
}

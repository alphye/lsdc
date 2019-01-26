package com.lishengzn.common.entity.response.statusapi;

import com.lishengzn.common.entity.response.ResponseEntity;

public class ResponseRollerStatus extends ResponseEntity {
    /**左上料标志位, true = 正在左上料, false = 上料完成, 从 true 变为 false, 且roller_error_code 为 0, 表示上料完成; 可缺省：是*/
    private boolean roller_left_load;

    /**左下料标志位, true = 正在左下料, false = 下料完成, 从 true 变为 false, 且roller_error_code 为 0, 表示下料完成; 可缺省：是*/
    private boolean roller_left_unload;

    /**右上料标志位, true = 正在右上料, false = 上料完成, 从 true 变为 false, 且roller_error_code 为 0, 表示上料完成; 可缺省：是*/
    private boolean roller_right_load;

    /**右下料标志位, true = 正在右下料, false = 下料完成, 从 true 变为 false, 且roller_error_code 为 0, 表示下料完成; 可缺省：是*/
    private boolean roller_right_unload;

    /**是否启用且连接成功; 可缺省：是*/
    private boolean roller_enable;

    /**错误码; 可缺省：是*/
    private int roller_error_code;

    /**辊筒或皮带上是否有料, true = 有料, false = 空; 可缺省：是*/
    private boolean roller_isFull;

    /**光电管 1 信号; 可缺省：是*/
    private boolean roller_sig1;

    /**光电管 2 信号; 可缺省：是*/
    private boolean roller_sig2;

    /**光电管 3 信号; 可缺省：是*/
    private boolean roller_sig3;

    /**辊筒的速度等级, 0 = 100%, 1 = 75%, 2 = 50%; 可缺省：是*/
    private int roller_speed_level;

    public boolean isRoller_left_load() {
        return roller_left_load;
    }

    public void setRoller_left_load(boolean roller_left_load) {
        this.roller_left_load = roller_left_load;
    }

    public boolean isRoller_left_unload() {
        return roller_left_unload;
    }

    public void setRoller_left_unload(boolean roller_left_unload) {
        this.roller_left_unload = roller_left_unload;
    }

    public boolean isRoller_right_load() {
        return roller_right_load;
    }

    public void setRoller_right_load(boolean roller_right_load) {
        this.roller_right_load = roller_right_load;
    }

    public boolean isRoller_right_unload() {
        return roller_right_unload;
    }

    public void setRoller_right_unload(boolean roller_right_unload) {
        this.roller_right_unload = roller_right_unload;
    }

    public boolean isRoller_enable() {
        return roller_enable;
    }

    public void setRoller_enable(boolean roller_enable) {
        this.roller_enable = roller_enable;
    }

    public int getRoller_error_code() {
        return roller_error_code;
    }

    public void setRoller_error_code(int roller_error_code) {
        this.roller_error_code = roller_error_code;
    }

    public boolean isRoller_isFull() {
        return roller_isFull;
    }

    public void setRoller_isFull(boolean roller_isFull) {
        this.roller_isFull = roller_isFull;
    }

    public boolean isRoller_sig1() {
        return roller_sig1;
    }

    public void setRoller_sig1(boolean roller_sig1) {
        this.roller_sig1 = roller_sig1;
    }

    public boolean isRoller_sig2() {
        return roller_sig2;
    }

    public void setRoller_sig2(boolean roller_sig2) {
        this.roller_sig2 = roller_sig2;
    }

    public boolean isRoller_sig3() {
        return roller_sig3;
    }

    public void setRoller_sig3(boolean roller_sig3) {
        this.roller_sig3 = roller_sig3;
    }

    public int getRoller_speed_level() {
        return roller_speed_level;
    }

    public void setRoller_speed_level(int roller_speed_level) {
        this.roller_speed_level = roller_speed_level;
    }
}

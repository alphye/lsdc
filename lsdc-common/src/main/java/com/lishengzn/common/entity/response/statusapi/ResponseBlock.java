package com.lishengzn.common.entity.response.statusapi;

import com.lishengzn.common.entity.response.ResponseEntity;

public class ResponseBlock extends ResponseEntity {
    /**机器人是否被阻挡; 可缺省：是*/
    private boolean blocked;

    /**被阻挡的原因, 0 = Ultrasonic (超声检测到被阻挡), 1 = Laser (激光检测到被阻挡), 2 = Fallingdown (防跌落传感器检测到被阻挡),
     *  3 = Collision (碰撞传感器检测到被阻挡), 4 = Infrared (红外传感器检测到被阻挡), 5 = Lock（锁车开关），6 = 动态障碍物，
     *  7 = 虚拟激光点;
     *  可缺省：是*/
    private int block_reason;

    /**最近障碍物位置的 x 坐标, 单位 m; 可缺省：是*/
    private double block_x;

    /**最近障碍物位置的 y 坐标, 单位 m; 可缺省：是*/
    private double block_y;

    /**发生阻挡的超声 id 号, 仅当 block_reason = 0 时有意义; 可缺省：是*/
    private int block_ultrasonic_id;

    /**发生阻挡的 DI 的 id 号, 仅当 block_reason = 2、3、4 时有意义; 可缺省：是*/
    private int block_di;


    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public int getBlock_reason() {
        return block_reason;
    }

    public void setBlock_reason(int block_reason) {
        this.block_reason = block_reason;
    }

    public double getBlock_x() {
        return block_x;
    }

    public void setBlock_x(double block_x) {
        this.block_x = block_x;
    }

    public double getBlock_y() {
        return block_y;
    }

    public void setBlock_y(double block_y) {
        this.block_y = block_y;
    }

    public int getBlock_ultrasonic_id() {
        return block_ultrasonic_id;
    }

    public void setBlock_ultrasonic_id(int block_ultrasonic_id) {
        this.block_ultrasonic_id = block_ultrasonic_id;
    }

    public int getBlock_di() {
        return block_di;
    }

    public void setBlock_di(int block_di) {
        this.block_di = block_di;
    }
}

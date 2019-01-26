package com.lishengzn.common.entity.request.other;

public class RequestSetDO {
    /**DO 的 id 号, 从 0 开始; 可缺省：否*/
    private int id;

    /**true 为高电平, false 为低电平; 可缺省：否*/
    private boolean status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}

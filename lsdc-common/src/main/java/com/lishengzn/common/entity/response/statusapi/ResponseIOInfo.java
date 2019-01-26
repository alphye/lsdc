package com.lishengzn.common.entity.response.statusapi;

import com.lishengzn.common.entity.response.ResponseEntity;

import java.util.List;

public class ResponseIOInfo extends ResponseEntity {
    /**DI 数据, boolean 表示高低电平, 从 0 开始; 可缺省：是*/
    private List<Boolean> DI;

    /**DO 数据, boolean 表示高低电平, 从 0 开始; 可缺省：是*/
    private List<Boolean> DO;

    /**DI 对应位置是否启动, false = 禁用, true = 启用; 可缺省：是*/
    private List<Boolean> DI_valid;

    public List<Boolean> getDI() {
        return DI;
    }

    public void setDI(List<Boolean> DI) {
        this.DI = DI;
    }

    public List<Boolean> getDO() {
        return DO;
    }

    public void setDO(List<Boolean> DO) {
        this.DO = DO;
    }

    public List<Boolean> getDI_valid() {
        return DI_valid;
    }

    public void setDI_valid(List<Boolean> DI_valid) {
        this.DI_valid = DI_valid;
    }
}

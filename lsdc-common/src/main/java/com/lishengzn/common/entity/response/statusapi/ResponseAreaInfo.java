package com.lishengzn.common.entity.response.statusapi;

import com.lishengzn.common.entity.response.ResponseEntity;

import java.util.List;

public class ResponseAreaInfo extends ResponseEntity {
    /** 机器人所在区域 id 的数组(由于地图上的区域是可以重叠的, 所以机器人可能同时在多个区域)，数组可能为空*/
    private List<String> area_ids;

    public List<String> getArea_ids() {
        return area_ids;
    }

    public void setArea_ids(List<String> area_ids) {
        this.area_ids = area_ids;
    }
}

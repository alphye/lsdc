package com.lishengzn.common.entity.response.statusapi;

import com.lishengzn.common.entity.response.ResponseEntity;

import java.util.List;

public class ResponseMapInfo extends ResponseEntity {
    /**当前载入的地图; 可缺省：是*/
    private String current_map;

    /** 所有储存在机器人上的地图名组成的数组; 可缺省：是*/
    private List<String> maps;

    public String getCurrent_map() {
        return current_map;
    }

    public void setCurrent_map(String current_map) {
        this.current_map = current_map;
    }

    public List<String> getMaps() {
        return maps;
    }

    public void setMaps(List<String> maps) {
        this.maps = maps;
    }
}

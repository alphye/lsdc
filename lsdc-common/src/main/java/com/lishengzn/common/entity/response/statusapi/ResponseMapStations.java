package com.lishengzn.common.entity.response.statusapi;

import com.lishengzn.common.entity.response.ResponseEntity;

import java.util.List;

public class ResponseMapStations extends ResponseEntity {
    /** 站点数组, 若地图中没有站点, 则为空数组 */
    private List<MapStation> stations;

    public List<MapStation> getStations() {
        return stations;
    }

    public void setStations(List<MapStation> stations) {
        this.stations = stations;
    }
}

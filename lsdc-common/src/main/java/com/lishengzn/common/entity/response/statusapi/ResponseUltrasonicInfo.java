package com.lishengzn.common.entity.response.statusapi;

import com.lishengzn.common.entity.response.ResponseEntity;

import java.util.List;

public class ResponseUltrasonicInfo extends ResponseEntity {
    List<UltrasonicNode> ultrasonic_nodes;

    public List<UltrasonicNode> getUltrasonic_nodes() {
        return ultrasonic_nodes;
    }

    public void setUltrasonic_nodes(List<UltrasonicNode> ultrasonic_nodes) {
        this.ultrasonic_nodes = ultrasonic_nodes;
    }
}

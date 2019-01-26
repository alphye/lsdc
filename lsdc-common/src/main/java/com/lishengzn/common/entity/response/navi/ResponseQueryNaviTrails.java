package com.lishengzn.common.entity.response.navi;

/** 获取路径导航的路径
 * 该请求只返回使用路径导航去到目标点所规划的路径, 并不实际执行导航
 **/
public class ResponseQueryNaviTrails {
    /** 目标站点的 id*/
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

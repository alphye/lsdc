package com.lishengzn.common.entity.response.statusapi;

import com.lishengzn.common.entity.response.ResponseEntity;

public class ResponseParams extends ResponseEntity {
    /**参数所属的插件名, 如果缺省, 表示查询所有插件的所有参数; 可缺省：是*/
    private String plugin;

    /**参数名, 如果 plugin 存在，但 param 缺省，代表查询该插件的所有参数。否侧查询该插件的指定参数; 可缺省：是*/
    private String param;

    public String getPlugin() {
        return plugin;
    }

    public void setPlugin(String plugin) {
        this.plugin = plugin;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }
}

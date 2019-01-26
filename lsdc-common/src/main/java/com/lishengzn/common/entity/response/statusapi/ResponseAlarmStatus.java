package com.lishengzn.common.entity.response.statusapi;

import com.lishengzn.common.entity.response.ResponseEntity;

import java.util.List;
import java.util.Map;

public class ResponseAlarmStatus extends ResponseEntity {
    /**告警码 Fatal 的数组, 所有出现的 Fatal 告警都会出现在数据中, object 格式见下文; 可缺省：是*/
    private List<Map> atals;

    /**告警码 Error 的数组, 所有出现的 Error 告警都会出现在数据中, object 格式见下文; 可缺省：是*/
    private List<Map> errors;

    /**告警码 Warning 的数组, 所有出现的 Warning 告警都会出现在数据中, object 格式见下文; 可缺省：是*/
    private List<Map> warnings;

    /**告警码 Warning 的数组, 所有出现的 Notice 告警都会出现在数据中, object 格式见下文; 可缺省：是*/
    private List<Map> notices;

    public List<Map> getAtals() {
        return atals;
    }

    public void setAtals(List<Map> atals) {
        this.atals = atals;
    }

    public List<Map> getErrors() {
        return errors;
    }

    public void setErrors(List<Map> errors) {
        this.errors = errors;
    }

    public List<Map> getWarnings() {
        return warnings;
    }

    public void setWarnings(List<Map> warnings) {
        this.warnings = warnings;
    }

    public List<Map> getNotices() {
        return notices;
    }

    public void setNotices(List<Map> notices) {
        this.notices = notices;
    }
}
/**{"50000":1497698400,"desc":"xxxxx","times":1} // key 为报警码, 对应的值为自 Thu Jan 01 08:00:00 1970 至报警码产生时经过的秒数, desc 为错误码对应的英文描述, times 为次数*/

package com.lishengzn.common.entity.response;

public class ResponseEntity {
    /**API 错误码; 可缺省：是*/
    protected int ret_code;

    /**错误信息; 可缺省：是*/
    protected String err_msg;

    public int getRet_code() {
        return ret_code;
    }

    public void setRet_code(int ret_code) {
        this.ret_code = ret_code;
    }

    public String getErr_msg() {
        return err_msg;
    }

    public void setErr_msg(String err_msg) {
        this.err_msg = err_msg;
    }
}

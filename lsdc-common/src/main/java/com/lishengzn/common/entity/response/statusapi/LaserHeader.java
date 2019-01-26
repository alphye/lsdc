package com.lishengzn.common.entity.response.statusapi;

public class LaserHeader {
    private String data_nsec;
    private String frame_id;
    private String pub_nsec;
    private String seq;

    public String getData_nsec() {
        return data_nsec;
    }

    public void setData_nsec(String data_nsec) {
        this.data_nsec = data_nsec;
    }

    public String getFrame_id() {
        return frame_id;
    }

    public void setFrame_id(String frame_id) {
        this.frame_id = frame_id;
    }

    public String getPub_nsec() {
        return pub_nsec;
    }

    public void setPub_nsec(String pub_nsec) {
        this.pub_nsec = pub_nsec;
    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }
}

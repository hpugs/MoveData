package com.hpugs.movedata.modle.excel;

public class CustomerResp {

    public CustomerResp(String external_userid) {
        this.external_userid = external_userid;
    }

    public CustomerResp(String external_userid, Integer errcode) {
        this.external_userid = external_userid;
        this.errcode = errcode;
    }

    private String external_userid;

    private Integer errcode;

    public String getExternal_userid() {
        return external_userid;
    }

    public void setExternal_userid(String external_userid) {
        this.external_userid = external_userid;
    }

    public Integer getErrcode() {
        return errcode;
    }

    public void setErrcode(Integer errcode) {
        this.errcode = errcode;
    }
}

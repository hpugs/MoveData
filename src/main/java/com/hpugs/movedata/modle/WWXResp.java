package com.hpugs.movedata.modle;

import com.hpugs.movedata.modle.excel.CustomerResp;

import java.util.List;

public class WWXResp {

    private Integer errcode;

    private String errmsg;

    private List<String> external_userid;

    private List<CustomerResp> customer;

    public Integer getErrcode() {
        return errcode;
    }

    public void setErrcode(Integer errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public List<String> getExternal_userid() {
        return external_userid;
    }

    public void setExternal_userid(List<String> external_userid) {
        this.external_userid = external_userid;
    }

    public List<CustomerResp> getCustomer() {
        return customer;
    }

    public void setCustomer(List<CustomerResp> customer) {
        this.customer = customer;
    }
}

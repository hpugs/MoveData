package com.hpugs.movedata.modle.excel;

public class ExternalUserDTO {

    private String userId;

    private String externalUserId;

    private Integer errCode = -1;

    public ExternalUserDTO() {
    }

    public ExternalUserDTO(String userId, String externalUserId, Integer errCode) {
        this.userId = userId;
        this.externalUserId = externalUserId;
        this.errCode = errCode;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getExternalUserId() {
        return externalUserId;
    }

    public void setExternalUserId(String externalUserId) {
        this.externalUserId = externalUserId;
    }

    public Integer getErrCode() {
        return errCode;
    }

    public void setErrCode(Integer errCode) {
        this.errCode = errCode;
    }
}

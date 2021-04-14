package com.lce.merchantmini.domain.enums;

public enum MerCodeEnum {
    NOT_MATCH("30001","权限不足，无法进行提现操作"),
    PARAMS_NOT_MATCH("30002","参数类型不匹配"),
    SIGN_FAIL("30003","获取签名失败"),
    COMMUNICATE_FAIL("30004","与第三方接口通信失败"),
    AMOUNT_LIMIT_ERROR("30005","单笔提现最低要0.3,最高不超过5000")
    ;
    private String code;
    private String msg;

    MerCodeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

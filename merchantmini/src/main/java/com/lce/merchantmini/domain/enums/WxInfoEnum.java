package com.lce.merchantmini.domain.enums;

public enum WxInfoEnum {

    CODE2SESSION_GRANT_TYPE("grant_type","authorization_code"),
    CODE2SESSION_URL("url","https://api.weixin.qq.com/sns/jscode2session")
    ;
    private String key;
    private String value;

    WxInfoEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

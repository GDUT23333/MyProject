package com.lce.merchantmini.domain.enums;

public enum WxPayDrawEnum {

    SUCCESS("SUCCESS"),
    NOCHECK("NO_CHECK"),
    FAIL("FAIL")
    ;
    private String value;

    WxPayDrawEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}

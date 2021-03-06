package com.mjh.www.bean;

/**
 * 封装后台返回值和返回信息
 */
public class Msg {
    private String result;
    private Object message;

    public Msg(String result, Object message) {
        this.result = result;
        this.message = message;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }
}

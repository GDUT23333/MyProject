package com.lce.merchantmini.domain.enums;

/**
 * @Author: Ember
 * @Date: 2021/3/17 17:21
 * @Description: 公众号枚举
 */
public enum GzhEnums {
    /**
     *url
     */
    GET_OPENID_URL("openid"," https://api.weixin.qq.com/sns/oauth2/access_token"),
    GET_ACCESS_TOKEN_URL("accessToken","https://api.weixin.qq.com/cgi-bin/token"),
    SEND_URL("url","https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="),
    AUTHORIZE_URL("authorize","https://open.weixin.qq.com/connect/oauth2/authorize"),
    GET_USER_INFO_URL("user_info","https://api.weixin.qq.com/sns/userinfo"),
    GET_QRTICKET_URL("qr","https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token="),
    /**
     * 获取code的参数
     */
    RESPONSE_TYPE("response_type","code"),
    SCOPE("scope","snsapi_userinfo"),
    STATE("state","123#wechat_redirect"),

    /**
     * 获得access_token的参数
     */
    GRANT_TYPE("grant_type","client_credential"),

    /**
     * 获取openid的参数
     */
    OPENID_GRANT_TYPE("grant_type","authorization_code"),

    /**
     * 获取用户信息的参数
     */
    LANG("lang","zh_CN"),

    /**
     * 模板id
     */
    SIGN_UP_SUCCESS_TEMPLATE_ID("template_id","2OOo-2tUR8opBDn-e9CSDZnC20SEOqGZ67yylMUP_r8"),
    DRAW_CASH_SUCCESS_TEMPLATE_ID("template_id","DZzE3VeDaqt8JaCcwv96cFoTvyryRsc7dfi2vBSg7TQ"),
    DRAW_CASH_FAILED_TEMPLATE_ID("template_id","LJl6NN_3zql9y5QvSoqJzf1UkaIsQIIMOczUK1NeGhk"),
    ANSWER_PROGRESS_TEMPLATE_ID("template_id","5S1NNvGeqO4m5kIc4UkqFNTduEwy75gaNpDyQXzTCfg"),

    /**
     * 异常
     */
    GET_TOKEN_ERROR("60001","获取token失败"),
    GET_GZH_OPENID_ERROR("60003","获取公众号openid失败,联系后端"),
    PARAMETER_NOT_MATCH("60002","参数不匹配"),
    GET_CODE_FAILED("60004","获取授权code失败"),
    NOT_SOCKET("1","未绑定公众号,请进行绑定")
    ;
    private String key;
    private String value;

    GzhEnums(String key, String value) {
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

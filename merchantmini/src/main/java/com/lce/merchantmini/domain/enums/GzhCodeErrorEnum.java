package com.lce.merchantmini.domain.enums;

/**
 * @Author: Ember
 * @Date: 2021/3/17 16:03
 * @Description: 获取code错误信息返回
 */
public enum GzhCodeErrorEnum {
    /**
     * 用户授权错误返回码
     */
    NOT_MATCH_URI("10003","redirect_uri域名配置不一致"),
    GZH_FORBIDDEN("10004","此公众号被封禁"),
    NOT_HAVE_SCOPE("10005","此公众号并没有这些scope权限"),
    NOT_ATTENTION("10006","必须关注此测试号"),
    BUSY_TRY("10009","操作太频繁了，请稍后重试"),
    SCOPE_IS_EMPTY("10010","scope不能为空"),
    REDIRECT_URI_EMPTY("10011","redirect_uri不能为空"),
    APPID_EMPTY("10012","appid不能为空"),
    STATE_EMPTY("10013","state不能为空"),
    NOT_AUTHORISE("10015","公众号未授权第三方平台，请检查授权状态"),
    NOT_SUPPORT("10016","不支持微信开放平台的Appid,请使用公众号Appid")
    ;
    private String key;
    private String value;

    public static GzhCodeErrorEnum getInstanceByCode(String code){
        for(GzhCodeErrorEnum gzhCodeErrorEnum:values()){
            if(code.equals(gzhCodeErrorEnum.getKey())){
                return gzhCodeErrorEnum;
            }
        }
        return null;
    }
    GzhCodeErrorEnum(String key, String value) {
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

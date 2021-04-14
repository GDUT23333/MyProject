package com.lce.merchantmini.domain.enums;

/**
 * @Author: Ember
 * @Date: 2021/3/19 19:35
 * @Description: 公众号场景
 */
public enum GzhSceneEnums {
    /**
     * 参数与超时时间
     * 1.EXPIRE_SECOND:临时二维码超时时间，单位为秒，这里设置10分钟过期
     * 2.QR_SCENE:临时二维码
     * 3.QR_LIMIT_SCENE:永久二维码
     */

    EXPIRE_SECOND("expireSecond",600),
    QR_SCENE("QR_SCENE","QR_SCENE"),
    QR_LIMIT_SCENE("QR_LIMIG_SCENE","QR_LIMIT_SCENE"),
    /**
     * 场景值:EventKey
     * 1. ENABLE_NOTIFY:开启活动通知
     */

    ENABLE_NOTIFY("enableNotify","enableNotify;"),

    /**
     * 进入方式:Event
     * 1. SCAN:扫二维码进入
     */

    SCAN("scan","SCAN")
    ;
    private String key;
    private Object value;

    GzhSceneEnums(String key, Object value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}

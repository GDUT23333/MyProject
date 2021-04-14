package com.lce.merchantmini.utils;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author: Ember
 * @Date: 2021/3/17 17:21
 * @Description: 公众号配置工具
 */
@Component
@ConfigurationProperties(prefix = "gzh")
public class MyGzhConfigUtils {
    private String appId;
    private String appSecret;
    private String redirectUrl;
    public MyGzhConfigUtils(String appId, String appSecret) {
        this.appId = appId;
        this.appSecret = appSecret;
    }

    public MyGzhConfigUtils() {
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }
}

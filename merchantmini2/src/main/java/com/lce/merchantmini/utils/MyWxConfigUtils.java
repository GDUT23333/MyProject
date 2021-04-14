package com.lce.merchantmini.utils;

import com.github.wxpay.sdk.WXPayConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.*;

/**
 * 提现配置
 */
@Component
@ConfigurationProperties(prefix = "weixin")
public class MyWxConfigUtils implements WXPayConfig {

    private String appID;
    private String mchID;
    private String key;
    private String spbillCreateIP;
    private String appSecret;
    private String tradeType;
    private String merPayNotifyUrl;
    private String drawNotifyUrl;
    private String location;
    private byte[] certData;

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getMerPayNotifyUrl() {
        return merPayNotifyUrl;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setMerPayNotifyUrl(String merPayNotifyUrl) {
        this.merPayNotifyUrl = merPayNotifyUrl;
    }

    @Override
    public String getAppID() {
        return this.appID;
    }

    @Override
    public String getMchID() {
        return this.mchID;
    }

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public InputStream getCertStream() {
        return new ByteArrayInputStream(getCertData());
    }

    @Override
    public int getHttpConnectTimeoutMs() {
        return 8000;
    }

    @Override
    public int getHttpReadTimeoutMs() {
        return 10000;
    }

    public void setAppID(String appID) {
        this.appID = appID;
    }

    public String getDrawNotifyUrl() {
        return drawNotifyUrl;
    }

    public void setDrawNotifyUrl(String drawNotifyUrl) {
        this.drawNotifyUrl = drawNotifyUrl;
    }

    public String getlocation() {
        return location;
    }

    public void setlocation(String location) {
        this.location = location;
    }

    public void setKey(String key) {
        this.key = key;
    }


    public void setMchID(String mchID) {
        this.mchID = mchID;
    }

    public String getSpbillCreateIP() {
        return spbillCreateIP;
    }

    public void setSpbillCreateIP(String spbillCreateIP) {
        this.spbillCreateIP = spbillCreateIP;
    }

    /**
     * 采用从配置文件中注入，注入时没有CertData,所以get方法要重写
     * @return
     */
    public byte[] getCertData() {
        File cert = new File(this.location);
        try {
            InputStream inputStream = new FileInputStream(cert);
            //InputStream inputStream = this.getClass().getResourceAsStream(this.location);
            this.certData = new byte[(int) cert.length()];
            //this.certData = new byte[inputStream.available()];
            inputStream.read(this.certData);
            inputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this.certData;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public void setCertData(byte[] certData) {
        this.certData = certData;
    }
}

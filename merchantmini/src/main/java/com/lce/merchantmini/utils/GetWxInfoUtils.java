package com.lce.merchantmini.utils;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 获取微信小程序配置信息工具
 */
@Component
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetWxInfoUtils {
    //appId
    @Value("${weixin.appID}")
    private String appID;
    //商家id
    @Value("${weixin.mchID}")
    private String mchID;
    //密钥
    @Value("${weixin.key}")
    private String key;
    //支付方式
    @Value("${weixin.tradeType}")
    private String tradeType;
    //支付回调函数
    @Value("${weixin.payNotifyUrl}")
    private String payNotifyUrl;
    //退款回调函数
    @Value("${weixin.refundNotifyUrl}")
    private String refundNotifyUrl;
    //本机ip
    @Value("${weixin.spbillCreateIP}")
    private String spbillCreateIP;
    //小程序的secret
    @Value("${weixin.appSecret}")
    private String appSecret;
    //证书位置
    @Value("${weixin.location}")
    private String location;
}

package com.lce.merchantmini.service;

import com.lce.merchantmini.domain.dso.GzhQRCode;

import javax.servlet.http.HttpServletResponse;

/**
 * @Author: Ember
 * @Date: 2021/3/15 17:19
 * @Description: 公众号服务
 */
public interface GzhService {
    /**
     * 重定向第三方接口
     * @param response response
     */
    public void authorize(HttpServletResponse response);

    /**
     * 获取code
     * @param code
     * @param state
     */
    public void getCode(String code,String state);

    /**
     * 生成临时二维码
     * @param activityId
     * @param status
     * @return
     */
    public String createTicket(Integer activityId,Integer status);

    /**
     * 获取access_token
     * @return
     */
    public String  getGzhAccessToken();
    /**
     * 接收事件推送
     * @param xml
     */
    public String receive(String xml);

    /**
     * 生成永久二维码
     * @return
     */
    public String createLimitTicket();

}

package com.lce.merchantmini.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.wxpay.sdk.WXPayUtil;
import com.lce.common.exception.ErrorException;
import com.lce.common.util.HttpClientUtil;
import com.lce.merchantmini.domain.dso.*;
import com.lce.merchantmini.domain.enums.GzhEnums;
import com.lce.merchantmini.domain.enums.GzhSceneEnums;
import com.lce.merchantmini.mapper.GzhMapper;
import com.lce.merchantmini.service.GzhService;
import com.lce.merchantmini.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Ember
 * @Date: 2021/3/15 17:20
 * @Description: 公众号服务
 */
@Service
@Slf4j
public class GzhServiceImpl implements GzhService {

    private MyGzhConfigUtils configUtils;
    private GzhMapper gzhMapper;
    private QRCodeUtils qrCodeUtils;
    private ReplyParseXmlUtils replyParseXmlUtils;
    @Autowired
    public void setConfigUtils(MyGzhConfigUtils configUtils) {
        this.configUtils = configUtils;
    }

    @Autowired
    public void setQrCodeUtils(QRCodeUtils qrCodeUtils) {
        this.qrCodeUtils = qrCodeUtils;
    }

    @Autowired
    public void setGzhMapper(GzhMapper gzhMapper) {
        this.gzhMapper = gzhMapper;
    }

    @Autowired
    public void setReplyParseXmlUtils(ReplyParseXmlUtils replyParseXmlUtils) {
        this.replyParseXmlUtils = replyParseXmlUtils;
    }

    /**
     * 获取code
     * @return
     */
    @Override
    public void authorize(HttpServletResponse response) {
        String url = GzhEnums.AUTHORIZE_URL.getValue();
        String redirectUrl = configUtils.getRedirectUrl();
        try {
            //官方接口需要对回调Url进行转码
            String encodeUri = URLEncoder.encode(redirectUrl,"utf-8");
            Map<String,String> params = new HashMap<>(4);
            params.put("appid",configUtils.getAppId());
            params.put("redirect_uri",encodeUri);
            params.put(GzhEnums.RESPONSE_TYPE.getKey(),GzhEnums.RESPONSE_TYPE.getValue());
            params.put(GzhEnums.SCOPE.getKey(),GzhEnums.SCOPE.getValue());
            params.put(GzhEnums.STATE.getKey(),GzhEnums.STATE.getValue());
            String uri = VerifyCodeUtils.processCodeRedirectUrl(params,url);
            response.sendRedirect(uri);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            log.error("转码失败:{}"+redirectUrl);
        } catch (IOException e) {
            log.error("重定向失败");
            e.printStackTrace();
        }
    }

    /**
     * 解析code、获取access_token与openid,再获取unionid
     * @param code
     * @param state
     */
    @Override
    public void getCode(String code, String state) {
        if(VerifyCodeUtils.verifyCode(code)){
            try {
                //code获取成功，使用code来获取access_token和openid
                String url = GzhEnums.GET_OPENID_URL.getValue();
                Map<String,String> params = new HashMap<String,String>(4);
                params.put("appid",configUtils.getAppId());
                params.put("secret",configUtils.getAppSecret());
                params.put("code",code);
                params.put(GzhEnums.OPENID_GRANT_TYPE.getKey(),GzhEnums.OPENID_GRANT_TYPE.getValue());
                String result = HttpClientUtil.doGet(url, params);

                JSONObject object = JSON.parseObject(result);
                String errCode = object.getString("errcode");
                if(!DivStringUtils.isEmpty(errCode)){
                    String errMsg = object.getString("errmsg");
                    log.error("获取code失败,errcode:{},errmsg:{}",errCode,errMsg);
                    throw new ErrorException(GzhEnums.GET_CODE_FAILED.getKey(),GzhEnums.GET_CODE_FAILED.getValue());
                }
                else{
                    ObjectMapper mapper = new ObjectMapper();
                    GzhCode gzhCode = mapper.readValue(result, GzhCode.class);
                    getUserInfo(gzhCode);
                }
            } catch (JsonProcessingException e) {
                log.error(e.getMessage());
                e.printStackTrace();
            }catch (Exception e){
                log.error(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    /**
     * 根据code获取用户信息
     * @param gzhCode
     */
    public void getUserInfo(GzhCode gzhCode){
        //使用code拉取用户信息
        String infoUrl = GzhEnums.GET_USER_INFO_URL.getValue();
        Map<String,String> infoParams = new HashMap<String,String>(2);
        //这个access_token跟一般的不一样
        infoParams.put("access_token",gzhCode.getAccess_token());
        infoParams.put("openid",gzhCode.getOpenid());
        infoParams.put(GzhEnums.LANG.getKey(),GzhEnums.LANG.getValue());
        String infoResult = HttpClientUtil.doGet(infoUrl, infoParams);
        JSONObject infoObject = JSON.parseObject(infoResult);
        String infoErrCode = infoObject.getString("errcode");
        if(!DivStringUtils.isEmpty(infoErrCode)){
            String infoErrMsg = infoObject.getString("errmsg");
            log.info("获取用户信息失败,openid:{},errcode:{},errmsg:{}",gzhCode.getOpenid(),infoErrCode,infoErrMsg);
            throw new ErrorException(GzhEnums.GET_GZH_OPENID_ERROR.getKey(),GzhEnums.GET_GZH_OPENID_ERROR.getValue());
        }else{
            try {
                ObjectMapper mapper = new ObjectMapper();
                GzhUserInfo gzhUserInfo = null;
                gzhUserInfo = mapper.readValue(infoResult, GzhUserInfo.class);
                //进行绑定
                this.gzhMapper.socketGzh(gzhUserInfo);
            } catch (JsonProcessingException e) {
                log.error(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取access_token
     * @return
     */
    @Override
    public String  getGzhAccessToken(){
        try {
            String url = GzhEnums.GET_ACCESS_TOKEN_URL.getValue();
            Map<String,String> params = new HashMap<>(3);
            params.put("grant_type",GzhEnums.GRANT_TYPE.getValue());
            params.put("appid",configUtils.getAppId());
            params.put("secret",configUtils.getAppSecret());
            String result = HttpClientUtil.doGet(url, params);
            JSONObject jsonObject = JSONObject.parseObject(result);
            String errCode = jsonObject.getString("errcode");
            if(!DivStringUtils.isEmpty(errCode)){
                String errMsg = jsonObject.getString("errmsg");
                log.error("获取access_token失败,errcode:{},errmsg:{}",errCode,errMsg);
                throw new ErrorException(errCode,errMsg);
            }else{
                ObjectMapper mapper = new ObjectMapper();
                GzhAccessToken token = mapper.readValue(result, GzhAccessToken.class);
                return token.getAccess_token();
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        throw new ErrorException(GzhEnums.GET_TOKEN_ERROR.getKey(),GzhEnums.GET_TOKEN_ERROR.getValue());
    }

    /**
     * 接收消息
     * @param xml
     */
    @Override
    public String receive(String xml) {
        try {
            //借用WXPayUtil解析文本
            Map<String, String> params = WXPayUtil.xmlToMap(xml);
            String toXml = replyParseXmlUtils.replyTextParseXml(params);
            return toXml;
        } catch (Exception e) {
            log.error("xml解析失败:{}",e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取带参数的临时二维码
     * @param token
     * @return
     */
    public GzhQRCode createTicket(String token,Integer activityId,Integer status)  {
        String url = GzhEnums.GET_QRTICKET_URL.getValue() + token;
        String sceneStr = DivStringUtils.handleSceneStr(activityId,status, (String) GzhSceneEnums.ENABLE_NOTIFY.getValue());
        QRTicket qrScene = new QRTicket( (String) GzhSceneEnums.QR_SCENE.getValue(),  Integer.parseInt(GzhSceneEnums.EXPIRE_SECOND.getValue().toString()) ,sceneStr);
        String qrJson = JSONObject.toJSON(qrScene).toString();
        String result = HttpClientUtil.doPostJson(url,qrJson );
        JSONObject jsonObject = JSONObject.parseObject(result);
        String errCode = jsonObject.getString("errcode");
        if(!DivStringUtils.isEmpty(errCode)){
            String errMsg = jsonObject.getString("errmsg");
            log.error("获取access_token失败,errcode:{},errmsg{}",errCode,errMsg);
            throw new ErrorException(errCode,errMsg);
        }
        GzhQRCode gzhQRCode = JSONObject.parseObject(result, GzhQRCode.class);
        return gzhQRCode;
    }

    /**
     * 创建临时二维码
     * @param activityId
     * @return
     */
    @Override
    public String createTicket(Integer activityId,Integer status) {

        GzhQRCode ticket = createTicket(getGzhAccessToken(),activityId,status);
        return qrCodeUtils.createQR(ticket.getUrl());
    }
    /**TODO
     * 获取带参数的永久二维码
     * @param token
     * @return
     */
    public GzhQRCode createLimitTicket(String token)  {
        String url = GzhEnums.GET_QRTICKET_URL.getValue() + token;
        //TODO
        //未实现参数自定义
        QRLimitTicket qrScene = new QRLimitTicket( (String) GzhSceneEnums.QR_LIMIT_SCENE.getValue(),GzhSceneEnums.ENABLE_NOTIFY.getValue().toString());
        String qrJson = JSONObject.toJSON(qrScene).toString();
        String result = HttpClientUtil.doPostJson(url,qrJson );
        JSONObject jsonObject = JSONObject.parseObject(result);
        String errCode = jsonObject.getString("errcode");
        if(!DivStringUtils.isEmpty(errCode)){
            String errMsg = jsonObject.getString("errmsg");
            log.error("获取access_token失败,errcode:{},errmsg{}",errCode,errMsg);
            throw new ErrorException(errCode,errMsg);
        }
        GzhQRCode gzhQRCode = JSONObject.parseObject(result, GzhQRCode.class);
        return gzhQRCode;
    }
    /**
     * 创建永久二维码
     * @return
     */
    @Override
    public String createLimitTicket() {
        GzhQRCode ticket = createLimitTicket(getGzhAccessToken());
        return qrCodeUtils.createQR(ticket.getUrl());
    }
}

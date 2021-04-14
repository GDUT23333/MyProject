package com.lce.merchantmini.utils;

import cn.hutool.json.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lce.common.exception.ErrorException;
import com.lce.common.util.HttpClientUtil;
import com.lce.merchantmini.domain.dso.GzhToken;
import com.lce.merchantmini.domain.dto.gzh.*;
import com.lce.merchantmini.domain.enums.GzhEnums;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: Ember
 * @create: 2021-02-26 11:51
 **/
@Slf4j
public class GzhSendMessageFactory {
    private JSONObject waitSend;
    private BaseMessage msg;
    public GzhSendMessageFactory(BaseMessage message) {
        this.waitSend = new JSONObject();
        this.msg = message;
        ObjectMapper mapper = new ObjectMapper();
        //包装url
        if(!DivStringUtils.isEmpty(message.getUrl())){
            waitSend.set("url",message.getUrl());
        }
        if(!DivStringUtils.isNull(message.getMiniProgram().get("appid")) && !DivStringUtils.isNull(message.getMiniProgram().get("pagepath"))){
            try {
                waitSend.set("miniprogram",mapper.writeValueAsString(message.getMiniProgram()));
            } catch (JsonProcessingException e) {
                log.error("miniprogram转化失败");
                e.printStackTrace();
            }
        }
        waitSend.set("touser",msg.getOpenid());
    }
    public void send(String token){
        send(this.msg,token);
    }
    /**
     * 1. SignUpSuccessMsg 通知报名提醒
     * 2. DrawCashSuccessMsg 提现成功提醒
     * 3. DrawCashFailedMsh 提现失败提醒
     * 4. AnswerProgressMsg 提问进展提醒
     * @param items
     */

    private String send(Object items, String accessToken){
        String result = "";
        if(items instanceof SignUpSuccessMsg){
            result = GzhPackageDataUtils.signUpMsgPackage((SignUpSuccessMsg) items,this.waitSend);
        }
        else if(items instanceof DrawCashSuccessMsg){
            result = GzhPackageDataUtils.drawCashSuccessMsgPackage((DrawCashSuccessMsg) items,this.waitSend);
        }
        else if(items instanceof DrawCashFailedMsg){
            result = GzhPackageDataUtils.drawCashFailedMsgPackage((DrawCashFailedMsg) items,this.waitSend);
        }
        else if(items instanceof AnswerProgressMsg){
            result = GzhPackageDataUtils.answerProgressMsgPackage((AnswerProgressMsg) items,this.waitSend);
        }
        else {
            throw new ErrorException(GzhEnums.PARAMETER_NOT_MATCH.getKey(), GzhEnums.PARAMETER_NOT_MATCH.getValue());
        }
        return HttpClientUtil.doPostJson(GzhEnums.SEND_URL.getValue()+accessToken,result);
    }
}

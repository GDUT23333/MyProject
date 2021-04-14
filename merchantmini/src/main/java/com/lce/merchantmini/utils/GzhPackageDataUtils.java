package com.lce.merchantmini.utils;

import cn.hutool.json.JSONObject;
import com.lce.merchantmini.domain.dto.gzh.*;
import com.lce.merchantmini.domain.enums.GzhEnums;

/**
 * @author: Ember
 * @create: 2021-02-26 11:51
 **/
public class GzhPackageDataUtils {

    private static JSONObject data = new JSONObject();

    /**
     * 报名提醒
     * @param msg
     * @param waitSend
     * @return
     */
    public static String signUpMsgPackage(SignUpSuccessMsg msg, JSONObject waitSend){
        waitSend.set("template_id", GzhEnums.SIGN_UP_SUCCESS_TEMPLATE_ID.getValue());
        data.set("first",msg.getTitle());
        data.set("keyword1",msg.getActivityName());
        data.set("keyword2",msg.getName());
        data.set("keyword3",msg.getPhone());
        data.set("remark",msg.getRemark());
        waitSend.set("data",data);
        String result = waitSend.toString();
        return result;
    }

    /**
     * 提现成功
     * @param msg
     * @param waitSend
     * @return
     */
    public static String drawCashSuccessMsgPackage(DrawCashSuccessMsg msg,JSONObject waitSend){
        waitSend.set("template_id", GzhEnums.DRAW_CASH_SUCCESS_TEMPLATE_ID.getValue());
        data.set("first",msg.getTitle());
        data.set("keyword1",msg.getApplyTime());
        data.set("keyword2",msg.getDrawWay());
        data.set("keyword3",msg.getTotalAmount());
        data.set("keyword3",msg.getServiceChange());
        data.set("keyword3",msg.getFee());
        data.set("remark",msg.getRemark());
        waitSend.set("data",data);
        String result = waitSend.toString();
        return result;
    }

    /**
     * 问题进展
     * @param msg
     * @param waitSend
     * @return
     */
    public static String answerProgressMsgPackage(AnswerProgressMsg msg,JSONObject waitSend){
        waitSend.set("template_id", GzhEnums.ANSWER_PROGRESS_TEMPLATE_ID.getValue());
        data.set("first",msg.getTitle());
        data.set("keyword1",msg.getContent());
        data.set("keyword2",msg.getType());
        data.set("keyword3",msg.getCreateTime());
        data.set("remark",msg.getRemark());
        waitSend.set("data",data);
        String result = waitSend.toString();
        return result;
    }

    /**
     * 提现失败
     * @param msg
     * @param waitSend
     * @return
     */
    public static String drawCashFailedMsgPackage(DrawCashFailedMsg msg,JSONObject waitSend){
        waitSend.set("template_id", GzhEnums.DRAW_CASH_FAILED_TEMPLATE_ID.getValue());
        data.set("first",msg.getTitle());
        data.set("keyword1",msg.getAmount());
        data.set("keyword2",msg.getCreateTime());
        data.set("remark",msg.getRemark());
        waitSend.set("data",data);
        String result = waitSend.toString();
        return result;
    }
}

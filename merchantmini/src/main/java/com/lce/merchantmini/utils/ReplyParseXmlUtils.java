package com.lce.merchantmini.utils;

import com.lce.merchantmini.domain.enums.GzhSceneEnums;
import com.lce.merchantmini.mapper.GzhMapper;
import com.lce.merchantmini.mapper.MerControlActivityMapper;
import com.lce.merchantmini.service.GzhService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Ember
 * @Date: 2021/3/20 17:32
 * @Description: 生成回复XML工具类
 */
@Component
public class ReplyParseXmlUtils {
    private MerControlActivityMapper mapper;
    private GzhMapper gzhMapper;
    @Autowired
    public void setMapper(MerControlActivityMapper mapper) {
        this.mapper = mapper;
    }

    @Autowired
    public void setGzhMapper(GzhMapper gzhMapper) {
        this.gzhMapper = gzhMapper;
    }

    public String replyTextParseXml(Map<String,String> data){
        //公众号的原始id
        String toUserName = data.get("ToUserName");
        //用户的openid
        String fromUserName = data.get("FromUserName");
        //进入方式
        String event = data.get("event");
        //场景值
        String eventKey = data.get("EventKey");
        String[] splitKey = eventKey.split(";");

        if(splitKey[0].equals(GzhSceneEnums.ENABLE_NOTIFY.getKey()) && event.equals(GzhSceneEnums.SCAN.getValue())){
            Integer activityId = Integer.parseInt(splitKey[1]);
            //进行绑定
            Map<String,Object> socketParam = new HashMap<String,Object>(2);
            socketParam.put("activityId",activityId);
            socketParam.put("gzhOpenId",fromUserName);
            this.gzhMapper.socketGzhByActivityId(socketParam);
            Integer status = Integer.parseInt(splitKey[2]);
            Map<String,Object> params = new HashMap<String,Object>(2);
            params.put("status",status);
            params.put("activityId",activityId);
            //改变活动status
            this.mapper.changeInformStatus(params);
            //获取活动名
            String activityName = this.gzhMapper.getActNameByID(activityId);
            //自动回复
            String toXml = "<xml>\n" +
                    "  <ToUserName><![CDATA["+fromUserName+"]]></ToUserName>\n" +
                    "  <FromUserName><![CDATA["+toUserName+"]]></FromUserName>\n" +
                    "  <CreateTime>"+System.currentTimeMillis()+"</CreateTime>\n" +
                    "  <MsgType><![CDATA[text]]></MsgType>\n" +
                    "  <Content><![CDATA[活动:"+activityName+",开启活动通知成功]]></Content>\n" +
                    "</xml>";
            return toXml;
        }

        return "success";
    }
}

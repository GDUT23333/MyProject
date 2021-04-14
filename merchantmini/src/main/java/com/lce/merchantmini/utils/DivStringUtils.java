package com.lce.merchantmini.utils;

import com.lce.common.util.MyStringUtil;

import java.io.UnsupportedEncodingException;

/**
 * @author: Ember
 * @create: 2021-02-26 11:51
 **/
public class DivStringUtils extends MyStringUtil{
    public static boolean isEmpty(String content){
        return "".equals(content) || MyStringUtil.isNull(content);
    }
    public static boolean isNull(Object data){
        return data == null;
    }

    /**
     * 处理场景值
     * @param activityId
     * @param status
     * @param sceneStr
     * @return
     */
    public static String handleSceneStr(Integer activityId,Integer status,String sceneStr){
        StringBuilder builder = new StringBuilder();
        builder.append(sceneStr);
        builder.append(activityId);
        builder.append(";");
        builder.append(status);
        return builder.toString();
    }
}

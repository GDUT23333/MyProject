package com.lce.merchantmini.utils;

import com.lce.common.exception.ErrorException;
import com.lce.merchantmini.domain.enums.GzhCodeErrorEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.utils.URIBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;


/**
 * @Author: Ember
 * @Date: 2021/3/17 15:51
 * @Description: 检验公众号工具类
 */
@Slf4j
public class VerifyCodeUtils {
    /**
     * 生成回调接口
     * @param params
     * @param url
     * @return
     */
    public static String processCodeRedirectUrl(Map<String,String> params,String url){
        // 创建uri
        URIBuilder builder = null;
        try {
            builder = new URIBuilder(url);
            if (params != null) {
                for (String key : params.keySet()) {
                    builder.addParameter(key, params.get(key));
                }
            }
            URI uri = builder.build();
            return uri.toString();
        } catch (URISyntaxException e) {
            log.error("uri生成失败:{}",url);
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 检验获取code是否正确
     * @param code code
     * @return
     */
    public static boolean verifyCode(String code){
        switch (GzhCodeErrorEnum.getInstanceByCode(code)){
            case NOT_MATCH_URI:
            case BUSY_TRY:
            case APPID_EMPTY:
            case NOT_SUPPORT:
            case STATE_EMPTY:
            case GZH_FORBIDDEN:
            case NOT_ATTENTION:
            case NOT_AUTHORISE:
            case NOT_HAVE_SCOPE:
            case SCOPE_IS_EMPTY:
            case REDIRECT_URI_EMPTY: {
                return false;
            }
            default :{
                return true;
            }
        }
    }
}

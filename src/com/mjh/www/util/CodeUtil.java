package com.mjh.www.util;

import java.util.UUID;

/**
 * 用户生成激活码
 * UUID的randomUUID方法随机产生32位字符串
 */
public class CodeUtil {

    public static String getCode(){
        String code = UUID.randomUUID().toString().replace("-","");
        return code;
    }
}

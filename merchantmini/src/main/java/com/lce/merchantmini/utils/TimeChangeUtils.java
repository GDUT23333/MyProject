package com.lce.merchantmini.utils;

import com.lce.common.util.TimeUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: Ember
 * @create: 2021-03-01 21:43
 * @Description:
 **/

public class TimeChangeUtils extends TimeUtil {
    public static String timeChange(Date time){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        return dateFormat.format(time);
    }
}

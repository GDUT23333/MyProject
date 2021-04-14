package com.lce.merchantmini.utils;

import java.math.BigDecimal;

/**
 * @author Ember
 */
public class BigDecimalUtil {
    /**
     * 提现上限
     * @param amount
     * @return
     */
    public static boolean limitAmount(BigDecimal amount){
        //每天单笔上限为5000，下线为0.3
        BigDecimal fiveThousand = new BigDecimal("500000");
        //amount大于就返回1
        //大于0.3小于5000
        if(amount.compareTo(new BigDecimal("30")) == 1 && fiveThousand.compareTo(amount) == 1){
            return true;
        }
        return false;
    }
    public static BigDecimal correctAmount(BigDecimal amount){
        if(amount == null){
            return new BigDecimal(0);
        }
        return amount;
    }
}

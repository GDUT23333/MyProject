package com.lce.merchantmini.utils;

import com.lce.merchantmini.domain.dto.wallet.WalletDetailBaseMsg;

import java.util.Comparator;
import java.util.Date;

/**
 * @author: Ember
 * @create: 2021-03-03 22:23
 * @Description:
 **/

public class DateCompareUtils implements Comparator {

    @Override
    public int compare(Object o1, Object o2) {
        WalletDetailBaseMsg msg1 = (WalletDetailBaseMsg) o1;
        WalletDetailBaseMsg msg2 = (WalletDetailBaseMsg) o2;
        if(msg1.getCreateTime().compareTo(msg2.getCreateTime()) > 0){
            return -1;
        }
        return 1;
    }
}

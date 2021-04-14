package com.lce.merchantmini.domain.dto.wallet;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author: Ember
 * @create: 2021-03-01 22:17
 * @Description:
 **/

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MoneyLogInfo extends WalletDetailBaseMsg{

    /**
     * 手续费
     */
    private BigDecimal feeCharge;
    /**
     * 操作类型 0:提现 1:充值
     */
    private Integer operationType;
}

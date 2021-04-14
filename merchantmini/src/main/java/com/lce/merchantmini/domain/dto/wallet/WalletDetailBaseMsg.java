package com.lce.merchantmini.domain.dto.wallet;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author: Ember
 * @create: 2021-03-01 22:08
 * @Description:
 **/
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class WalletDetailBaseMsg {
    /**
     * 主键
     */
    private String id;
    /**
     * 类型 -1提现，0充值，1已支付，2退款中，5已退款,6该票委托平台退款，等待平台确定退款
     */
    private Integer moneyType;

    /**
     *时间
     */
    private String createTime;

    /**
     * 金额
     */
    private BigDecimal amount;
    /**
     * 是否锁定金额 0:不锁定 1:锁定
     */
    private Integer isLock;
}

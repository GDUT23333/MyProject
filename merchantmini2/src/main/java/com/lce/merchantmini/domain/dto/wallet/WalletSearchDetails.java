package com.lce.merchantmini.domain.dto.wallet;

import lombok.*;

/**
 * @Author: Ember
 * @Date: 2021/4/8 22:12
 * @Description: 钱包搜索条件
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class WalletSearchDetails {
    /**
     * 商家唯一标识
     */
    private Integer id;

    /**
     * 活动id
     */
    private Integer activityId;
    /**
     * 月数
     */
    private Integer month;

    /**
     * 年份
     */
    private Integer year;

    /**
     * 0:售票 1:退票 2:充值 3:提现 4:全部信息
     */
    private String status;
}

package com.lce.merchantmini.domain.dto.wallet;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * @author: Ember
 * @create: 2021-03-01 22:14
 * @Description:
 **/

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TicketWalletInfo extends WalletDetailBaseMsg{

    /**
     * 活动名称
     */
    private String activityName;
    /**
     * 活动结束时间
     */
    private String acEndTime;
    /**
     * 票的名称
     */
    private String ticketType;
    /**
     * 买家名称
     */
    private String buyer;
    /**
     * 票的退款 退款设置，0代表不可退款，1代表委托平台退款，2代表无条件退款
     */
    private Integer status;
}

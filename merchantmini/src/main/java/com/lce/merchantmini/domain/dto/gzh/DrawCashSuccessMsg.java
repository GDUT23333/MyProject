package com.lce.merchantmini.domain.dto.gzh;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 提现消息模板
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DrawCashSuccessMsg extends BaseMessage{
    /**
     * 申请时间
     */
    private MessageItem applyTime;
    /**
     * 提现方式
     */
    private MessageItem drawWay;
    /**
     * 提现金额
     */
    private MessageItem totalAmount;
    /**
     *手续费用
     */
    private MessageItem fee;
    /**
     * 到账金额
     */
    private MessageItem serviceChange;

}

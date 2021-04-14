package com.lce.merchantmini.domain.dto;

import lombok.*;

import java.math.BigDecimal;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MerMoneyLog {

    //主键
    private Integer id;
    //金额
    private BigDecimal amount;
    //订单id
    private String orderId;
    //创建时间
    private String createTime;
    //状态，0:提现，1:充值
    private Integer status;
    //商家mid
    private Integer mId;
    //商家的openId;
    private String openId;
}

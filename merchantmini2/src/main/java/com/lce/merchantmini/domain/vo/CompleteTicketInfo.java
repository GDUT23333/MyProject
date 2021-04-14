package com.lce.merchantmini.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Author: Ember
 * @Date: 2021/3/18 20:20
 * @Description: 完整票信息
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CompleteTicketInfo extends SimpleTicketInfo{
    private Integer refundType;
    private String refundReason;
}

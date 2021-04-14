package com.lce.merchantmini.domain.vo;

import lombok.*;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import java.math.BigDecimal;

/**
 * 指定商家活动的票，用于商家活动图
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SimpleTicketInfo {

    private Integer tId;
    private String ticketName;

    @Min(1)
    private Integer ticketNumber;

    @DecimalMin("0.00")
    private BigDecimal ticketPrice;

    private Integer ticketRemainNumber;

    private String ticketInstruction;
}

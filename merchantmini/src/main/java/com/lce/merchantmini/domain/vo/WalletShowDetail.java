package com.lce.merchantmini.domain.vo;

import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class WalletShowDetail {
    private BigDecimal allAmount;
    private BigDecimal canDrawCashAmount;
    private Integer mId;
}

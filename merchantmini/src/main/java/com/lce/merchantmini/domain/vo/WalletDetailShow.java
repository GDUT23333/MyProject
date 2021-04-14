package com.lce.merchantmini.domain.vo;

import com.lce.merchantmini.domain.dto.wallet.WalletDetailBaseMsg;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author: Ember
 * @create: 2021-03-01 22:23
 * @Description:
 **/

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class WalletDetailShow {
    private List<WalletDetailBaseMsg> details;
    private BigDecimal totalIncome;
    private BigDecimal totalPay;
}

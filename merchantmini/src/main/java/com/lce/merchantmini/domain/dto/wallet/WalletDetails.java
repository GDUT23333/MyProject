package com.lce.merchantmini.domain.dto.wallet;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @Author: Ember
 * @Date: 2021/4/9 10:10
 * @Description: 钱包账单详情
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class WalletDetails {
    private List<WalletDetailBaseMsg> walletDetailBaseMsgs;
    private List<ActivitySimpleInfo> activitySimpleInfos;
}

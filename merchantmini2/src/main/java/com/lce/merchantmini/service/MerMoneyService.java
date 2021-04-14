package com.lce.merchantmini.service;


import com.lce.common.util.BaseResult;
import com.lce.merchantmini.domain.dto.wallet.*;
import com.lce.merchantmini.domain.vo.WalletDetailShow;
import com.lce.merchantmini.domain.vo.WalletShowDetail;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface MerMoneyService {
    /**
     * 钱包首页展示，可提现金额与全部金额
     * @param mId
     * @return
     */
    public WalletShowDetail getAmountDetail(Integer mId);

    /**
     * 提现
     * @param params
     * @return
     */
    public Map drawCash(Map params);

    /**
     * 充值
     * @param amount
     * @param openid
     * @return
     */
    public Map chargeMoney(String amount,String openid);

    /**
     * 充值
     * @param params
     * @return
     */
    public BaseResult chargeMoney(Map params);

    /**
     * 充值的回调
     * @param request
     * @return
     */
    public String payBack(HttpServletRequest request);

    /**
     * 获取账单详情
     * @param details
     * @return
     */
    public WalletDetails getWalletDetail(WalletSearchDetails details);
    /**
     * 根据状态获取票的情况
     * @param  mId 商家唯一标识，
     * @param  status   status:状态
     * @return
     */
    public List<TicketWalletInfo> getTicketInfoByStatus(Integer mId,String status);


}

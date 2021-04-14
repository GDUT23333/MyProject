package com.lce.merchantmini.controller;

import com.lce.common.util.BaseResult;
import com.lce.common.util.DataResult;
import com.lce.merchantmini.domain.dto.wallet.WalletDetailBaseMsg;
import com.lce.merchantmini.domain.dto.wallet.WalletSearchDetails;
import com.lce.merchantmini.service.MerMoneyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/merchant/money")
@Slf4j
public class MerMoneyController {

    private MerMoneyService moneyService;

    @Autowired
    public void setMoneyService(MerMoneyService moneyService) {
        this.moneyService = moneyService;
    }

    /**
     * 提现
     * @param params mId商家主键，phone手机号 amount金额
     * @return
     */
    @PostMapping("/drawCash")
    public BaseResult drawCash(@RequestBody Map params){
        return DataResult.success(this.moneyService.drawCash(params));
    }

    /**
     * 充值页面中展现可提现金额
     * @param
     * @return
     */
    @PostMapping("/show")
    public BaseResult walletShow(@RequestParam Integer mId){
        return DataResult.success(this.moneyService.getAmountDetail(mId));
    }

    /**
     * 账单详情
     * @param details
     * @return
     */
    @PostMapping("/show/detail")
    public BaseResult walletDetailShow(@RequestBody WalletSearchDetails details){
        System.out.println(details);
        return DataResult.success(this.moneyService.getWalletDetail(details));
    }
    /**
     * 账单详情
     * @param mId
     * @return
     */
    @PostMapping("/show/detail/ticket")
    public BaseResult walletTicketShow(@RequestParam Integer mId,@RequestParam String status){
        return DataResult.success(this.moneyService.getTicketInfoByStatus(mId,status));
    }
    /**
     * 充值
     * @param params 1、mId(商家id) 2、amount("金额")
     * @return
     */
    @PostMapping("/pay")
    public BaseResult pay(@RequestBody Map params){
        return this.moneyService.chargeMoney(params);
    }
    /**
     * 支付的回调函数
    * @return
     */
    @PostMapping("/payBack")
    public String payBack(HttpServletRequest request){
        return this.moneyService.payBack(request);
    }
}

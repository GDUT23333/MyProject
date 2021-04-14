package com.lce.merchantmini.service.impl;

import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayConstants;
import com.github.wxpay.sdk.WXPayUtil;
import com.lce.common.exception.ErrorException;
import com.lce.common.model.MoneyLog;
import com.lce.common.util.*;
import com.lce.merchantmini.domain.dto.MerMoneyLog;
import com.lce.merchantmini.domain.dto.wallet.*;
import com.lce.merchantmini.domain.vo.WalletDetailShow;
import com.lce.merchantmini.domain.vo.WalletShowDetail;
import com.lce.merchantmini.domain.enums.MerCodeEnum;
import com.lce.merchantmini.domain.enums.WxPayDrawEnum;
import com.lce.merchantmini.mapper.MerMoneyMapper;
import com.lce.merchantmini.service.MerMoneyService;
import com.lce.merchantmini.utils.BigDecimalUtil;
import com.lce.merchantmini.utils.DateCompareUtils;
import com.lce.merchantmini.utils.MyWxConfigUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;


@Service
@Slf4j
public class MerMoneyServiceImpl implements MerMoneyService {

    private MerMoneyMapper merMoneyMapper;
    private MyWxConfigUtils utils;
    @Autowired
    public void setMerMoneyMapper(MerMoneyMapper merMoneyMapper) {
        this.merMoneyMapper = merMoneyMapper;
    }

    @Autowired
    public void setUtils(MyWxConfigUtils utils) {
        this.utils = utils;
    }

    @Override
    public WalletShowDetail getAmountDetail(Integer mId) {
        Map<String,Integer> params = new HashMap<>();
        params.put("mId",mId);
        //状态为1时，表示已经支付
        params.put("status",1);
        //票的总收入，为已支付状态
        BigDecimal ticketIncome = this.merMoneyMapper.getTicketsIncomeByStatus(params);
        //充值的总金额
        Map<String,Object> param = new HashMap<>(2);
        param.put("mId",mId);
        param.put("operation",1);
        BigDecimal chargeAmount = this.merMoneyMapper.getMerchantLog(param);

        //提现的总金额
        param.put("operation",0);
        BigDecimal drawAmount = this.merMoneyMapper.getMerchantLog(param);

        //活动未过截止时间，但票是不可退的所有票款
        BigDecimal notAllowRefund = this.merMoneyMapper.getNotAllowRefund(mId);

        //活动已过截止时间，得到的所有票款
        BigDecimal overTime = this.merMoneyMapper.getOverTime(mId);

        //处理空的情况
        ticketIncome = BigDecimalUtil.correctAmount(ticketIncome);
        chargeAmount = BigDecimalUtil.correctAmount(chargeAmount);
        drawAmount = BigDecimalUtil.correctAmount(drawAmount);
        notAllowRefund = BigDecimalUtil.correctAmount(notAllowRefund);
        overTime = BigDecimalUtil.correctAmount(overTime);

        //整合得到可以提现金额
        BigDecimal canDrawCash = chargeAmount.subtract(drawAmount).add(notAllowRefund).add(overTime);
        //封装
        WalletShowDetail showDetail = new WalletShowDetail();
        //总的金额
        showDetail.setAllAmount(ticketIncome.add(chargeAmount).subtract(drawAmount));
        showDetail.setCanDrawCashAmount(canDrawCash);
        showDetail.setMId(mId);
        return showDetail;
    }

    /**
     * 提现
     * @param params mId商家主键，phone手机号
     * @return
     */
    @Override
    public Map drawCash(Map params) {
        //通过Mid获取openid
        String result = this.merMoneyMapper.matchOpenId(params);

        if(!MyStringUtil.isNull(result)){
            try{
                Integer mId = (Integer) params.get("mId");
                //单位为分
                BigDecimal amount = new BigDecimal((String) params.get("amount"));
                if(BigDecimalUtil.limitAmount(amount)){
                    MerMoneyLog moneyLog = new MerMoneyLog();
                    moneyLog.setAmount(amount);
                    moneyLog.setMId(mId);
                    moneyLog.setOpenId(result);
                    return drawCash(moneyLog);
                }else{
                    //单笔低于0.3或者高于5000
                    throw new ErrorException(MerCodeEnum.AMOUNT_LIMIT_ERROR.getCode(),MerCodeEnum.AMOUNT_LIMIT_ERROR.getMsg());
                }
            }catch(NumberFormatException e){
                //处理参数错误情况
                e.printStackTrace();
                throw new ErrorException(MerCodeEnum.PARAMS_NOT_MATCH.getCode(),MerCodeEnum.PARAMS_NOT_MATCH.getMsg());
            }
        }
        else{
            throw new ErrorException(MerCodeEnum.NOT_MATCH.getCode(),MerCodeEnum.NOT_MATCH.getMsg());
        }


    }
    /**
     * 提现
     * @param moneyLog
     * @return
     */
    private Map drawCash(MerMoneyLog moneyLog) {

        WXPay wxPay = new WXPay(utils, WXPayConstants.SignType.MD5);
        String orderId = KeyUtil.getOrderId();
        //封装参数
        Map params = new HashMap<String,String>();
        params.put("mch_appid",utils.getAppID());
        params.put("mchid",utils.getMchID());
        params.put("nonce_str", WXPayUtil.generateNonceStr());
        params.put("partner_trade_no", orderId);
        params.put("openid",moneyLog.getOpenId());
        params.put("check_name", WxPayDrawEnum.NOCHECK.getValue());
        //单位为分
        params.put("amount",moneyLog.getAmount().toString());
        params.put("desc","提现");
        //获取签名
        String sign = null;
        try {
            sign = WXPayUtil.generateSignature(params,this.utils.getKey(), WXPayConstants.SignType.MD5);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            log.error("订单号:"+orderId+"获取签名失败");
            throw new ErrorException(MerCodeEnum.SIGN_FAIL.getCode(),MerCodeEnum.SIGN_FAIL.getMsg());

        }
        params.put("sign",sign);

        //使用官方API进行提现
        try {
            //进行提现
            String data = wxPay.requestWithCert(utils.getDrawNotifyUrl(),params,utils.getHttpConnectTimeoutMs(),utils.getHttpReadTimeoutMs());
            //处理返回参数
            Map<String,String> result = WXPayUtil.xmlToMap(data);
            String returnCode = result.get("return_code");
            //储存返回值
            Map<String,Object> resultWaitReturn = new HashMap<String,Object>();
            resultWaitReturn.put("return_code",result.get("return_code"));
            resultWaitReturn.put("return_msg",result.get("return_msg"));
            //
            //return_code为true，表示与第三方接口通信成功
            if(WxPayDrawEnum.SUCCESS.getValue().equalsIgnoreCase(returnCode)){
                //封装返回参数
                resultWaitReturn.put("result_code",result.get("result_code"));
                //提现成功
                if(WxPayDrawEnum.SUCCESS.getValue().equalsIgnoreCase(result.get("result_code"))){
                    resultWaitReturn.put("partner_trade_no",result.get("partner_trade_no"));
                    resultWaitReturn.put("payment_no",result.get("payment_no"));
                    resultWaitReturn.put("payment_time",result.get("payment_time"));
                    //提现成功，完善信息
                    moneyLog.setOrderId(orderId);
                    String currentTime = TimeUtil.getCurrentTime();
                    moneyLog.setCreateTime(currentTime);
                    //提现的话为0
                    moneyLog.setStatus(0);
                    //信息入库
                    this.merMoneyMapper.addMoneyLog(moneyLog);
                }
                else{
                    //提现失败
                    resultWaitReturn.put("err_code",result.get("err_code"));
                    resultWaitReturn.put("err_code_des",result.get("err_code_des"));
                    MerMoneyServiceImpl.log.info("支付失败，err_code:{},err_code_des:{}",result.get("err_code"),result.get("err_code_des"));
                    throw new ErrorException(CodeEnum.INTERNAL_SERVER_ERROR);
                }
                //返回最新金钱
                resultWaitReturn.put("wallet",getAmountDetail(moneyLog.getMId()));
                return resultWaitReturn;
            }else{
                //与第三方接口通信失败
                log.info("支付失败，与第三方接口通信失败");
                throw new ErrorException(MerCodeEnum.COMMUNICATE_FAIL.getCode(),MerCodeEnum.COMMUNICATE_FAIL.getMsg());
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            log.error("订单：{},提现过程异常",orderId);
        }
        //这里抛出异常
        throw new ErrorException(CodeEnum.BAD_REQUEST,"订单:"+orderId+"提现失败");
    }

    @Override
    public BaseResult chargeMoney(Map params){
        //检查参数是否完整
        if(MyStringUtil.isNull(params.get("amount")) || MyStringUtil.isNull(params.get("mId"))){
            throw new ErrorException(CodeEnum.PARAM_ERROR);
        }
        //找到openid
        String result = this.merMoneyMapper.findOpenIdByMId((Integer) params.get("mId"));
        if(!MyStringUtil.isNull(result)){
            return DataResult.success(chargeMoney((String) params.get("amount"),result));
        }
        return BaseResult.error("500","openid匹配错误");
    }

    /**
     *充值
     * @param amount
     * @return
     */
    @Override
    public Map chargeMoney(String amount,String openid) {
        WXPay pay = new WXPay(utils, WXPayConstants.SignType.MD5);
        Map<String,String> params = new HashMap<String,String>();
        //封装接口需要参数
        params.put("appid",utils.getAppID());
        params.put("mch_id",utils.getMchID());
        params.put("nonce_str",WXPayUtil.generateNonceStr());
        params.put("body","商家充值");
        String orderId = KeyUtil.getOrderId();
        params.put("out_trade_no", orderId);
        params.put("total_fee",amount);
        params.put("spbill_create_ip",utils.getSpbillCreateIP());
        params.put("trade_type",utils.getTradeType());
        params.put("notify_url",utils.getMerPayNotifyUrl());
        //使用JSAPI支付，openid必传
        params.put("openid",openid);
        //生成签名
        String sign = null;
        try {
            sign = WXPayUtil.generateSignature(params,utils.getKey(),WXPayConstants.SignType.MD5);
            params.put("sign",sign);
            //统一下单,生成预支付订单
            Map<String, String> result = pay.unifiedOrder(params, utils.getHttpConnectTimeoutMs(), utils.getHttpReadTimeoutMs());
            String returnCode = result.get("return_code");
            //与第三方通信成功
            if(WxPayDrawEnum.SUCCESS.getValue().equals(returnCode)){
                Map<String,String> waitReturn = new HashMap<String,String>();
                String resultCode = result.get("result_code");
                //生成预付订单成功
                if(WxPayDrawEnum.SUCCESS.getValue().equals(resultCode)){
                    //组合数据再次签名（appID,timeStamp,nonceStr,package,signType）
                    waitReturn.put("appId", utils.getAppID());
                    waitReturn.put("nonce_str",result.get("nonce_str"));
                    waitReturn.put("package", "prepay_id="+result.get("prepay_id"));
                    waitReturn.put("timeStamp", String.valueOf(System.currentTimeMillis() / 1000));
                    waitReturn.put("signType",WXPayConstants.MD5);
                    //再次签名
                    String reSign = WXPayUtil.generateSignature(waitReturn,utils.getKey(), WXPayConstants.SignType.MD5);
                    //再次组装签名
                    waitReturn.put("paySign",reSign);
                    //封装预支付订单prepay_id
                    waitReturn.put("prepay_id",result.get("prepay_id"));
                    //返回给前端，拉起支付窗口
                    return waitReturn;
                }else{
                    log.info("openId:{},商家充值过程异常,err_code:{},err_code_des:{}",openid,result.get("err_code"),result.get("err_code_des"));
                    throw new ErrorException(CodeEnum.INTERNAL_SERVER_ERROR);
                }
            }else{
                log.info("openId:{},商家充值过程与第三方接口失败,return_msg:{}",openid,result.get("return_msg"));
                throw  new ErrorException(MerCodeEnum.COMMUNICATE_FAIL.getCode(),MerCodeEnum.COMMUNICATE_FAIL.getMsg());
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            log.error("openId：{},商家充值过程异常",openid);
        }
        throw new ErrorException(CodeEnum.INTERNAL_SERVER_ERROR);
    }

    /**
     * 充值回调
     * @param request
     * @return
     */
    @Override
    public String payBack(HttpServletRequest request) {
        String inputLine="";
        StringBuilder notityXml = new StringBuilder();
        try {
            while ((inputLine = request.getReader().readLine()) != null) {
                notityXml.append(inputLine);
            }
            request.getReader().close();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("支付回调发生错误：{}",e.getMessage());
            return PayConst.PAY_BACK_FAIL;
        }
        WXPay wxPay = new WXPay(utils);
        String xmlBack = "";
        try {
            //拿到通知结果
            Map<String, String> notifyMap = WXPayUtil.xmlToMap(notityXml.toString());
            //验证签名是否有效
            if(wxPay.isPayResultNotifySignatureValid(notifyMap)){
                String returnCode = notifyMap.get("return_code");
                //代表通信成功
                if(WxPayDrawEnum.SUCCESS.getValue().equals(returnCode)){
                    String resultCode = notifyMap.get("result_code");
                    //商户订单号
                    String outTradeNo = notifyMap.get("out_trade_no");
                    //完成支付时间
                    String timeEnd = notifyMap.get("timeEnd");
                    //订单金额
                    BigDecimal totalFee = new BigDecimal(notifyMap.get("total_fee")).multiply(new BigDecimal(0.01));
                    //商家的openID
                    String openId = notifyMap.get("openid");
                    //交易成功
                    if(WxPayDrawEnum.SUCCESS.getValue().equals(resultCode)) {
                        //信息入库
                        MerMoneyLog moneyLog = new MerMoneyLog();
                        moneyLog.setStatus(1);
                        moneyLog.setAmount(totalFee);
                        moneyLog.setOpenId(openId);
                        moneyLog.setOrderId(outTradeNo);
                        moneyLog.setCreateTime(timeEnd);
                        this.merMoneyMapper.addBackMoneyLog(moneyLog);
                        log.info("商家微信手机充值回调成功，订单号:{}", outTradeNo);
                        xmlBack = PayConst.PAY_BACK_SUCCESS;
                    }
                }else{
                   log.error("商家充值回调与第三方接口通信失败");
                }
            }else{
                // 签名错误，如果数据里没有sign字段，也认为是签名错误
                log.error("小程序支付回调通知签名错误");
                xmlBack = PayConst.PAY_BACK_FAIL;
            }
        } catch (Exception e) {
            log.error("手机支付回调通知失败:{}",e.getMessage());
            e.printStackTrace();
        }
        return xmlBack;
    }

    @Override
    public WalletDetails getWalletDetail(WalletSearchDetails details) {
        List<ActivitySimpleInfo> activityInfos = this.merMoneyMapper.getActivityInfos(details.getId());
        List<MoneyLogInfo> moneyLogs = new ArrayList<>();
        List<TicketWalletInfo> allTicketWalletInfo = new ArrayList<>();
        WalletDetails walletDetails = new WalletDetails();
        walletDetails.setActivitySimpleInfos(activityInfos);
        Map<String,Object> params = new HashMap<>(4);
        params.put("month",details.getMonth());
        params.put("mId",details.getId());
        params.put("year",details.getYear());
        List<WalletDetailBaseMsg> msg = new ArrayList<>();
        //获取票的情况
        //查询全部交易
        if(details.getStatus().equals(WalletChooseEnums.FIND_ALL.getValue())){
            //2代表查找所有记录
            params.put("activityId",details.getActivityId());
            params.put("ticketStatus",WalletChooseEnums.TICKET_ALL.getValue());
            allTicketWalletInfo = this.merMoneyMapper.getTicketInfoByStatus(params);
            //对票是否冻结进行处理
            //获取提现充值情况
            //2代表全部
            params.put("status",WalletChooseEnums.MONEY_ALL.getValue());
            moneyLogs = this.merMoneyMapper.getMoneyLog(params);
            }
        //只查询提现
        else if(details.getStatus().equals(WalletChooseEnums.ONLY_DRAW_CASH.getValue())){
            params.put("status",WalletChooseEnums.MONEY_DRAW_CASH.getValue());
            moneyLogs = this.merMoneyMapper.getMoneyLog(params);
        }
        //只查询充值
        else if(details.getStatus().equals(WalletChooseEnums.ONLY_CHARGE.getValue())){
            params.put("status",WalletChooseEnums.MONEY_CHARGE.getValue());
            moneyLogs = this.merMoneyMapper.getMoneyLog(params);
        }
        //只查询退票
        else if(details.getStatus().equals(WalletChooseEnums.ONLY_REFUND.getValue())){
            params.put("activityId",details.getActivityId());
            params.put("ticketStatus",WalletChooseEnums.TICKET_REFUND.getValue());
            allTicketWalletInfo = this.merMoneyMapper.getTicketInfoByStatus(params);
        }
        //只查询售票
        else if(details.getStatus().equals(WalletChooseEnums.ONLY_SALE.getValue())){
            params.put("activityId",details.getActivityId());
            params.put("ticketStatus",WalletChooseEnums.TICKET_SALE.getValue());
            allTicketWalletInfo = this.merMoneyMapper.getTicketInfoByStatus(params);

        }
        Date current = new Date();
        for (TicketWalletInfo info : allTicketWalletInfo) {
            //如果活动已经结束或者是不可退款的
            if (info.getAcEndTime().compareTo(current.toString()) < 0 || info.getStatus() == 0){
                //不锁定金额
                info.setIsLock(0);
            }
            //其他进行锁定
            else{
                info.setIsLock(1);
            }
        }
        //进行处理
        for (MoneyLogInfo info : moneyLogs) {
            //充值和提现都不锁定
            info.setIsLock(0);
            if(info.getOperationType() == 0){
                info.setMoneyType(-1);
            }else{
                info.setMoneyType(0);
            }
        }
        //进行排序
        Collections.sort(msg,new DateCompareUtils());
        msg.addAll(allTicketWalletInfo);
        msg.addAll(moneyLogs);
        walletDetails.setWalletDetailBaseMsgs(msg);
        return walletDetails;
    }

    @Override
    public List<TicketWalletInfo> getTicketInfoByStatus(Integer mId,String status) {
        Map<String,Object> params = new HashMap<>(2);
        params.put("status",status);
        params.put("mId",mId);
        return this.merMoneyMapper.getTicketInfoByStatus(params);
    }
}

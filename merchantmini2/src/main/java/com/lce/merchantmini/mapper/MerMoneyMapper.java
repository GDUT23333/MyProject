package com.lce.merchantmini.mapper;

import com.lce.common.model.MoneyLog;
import com.lce.merchantmini.domain.dto.MerMoneyLog;
import com.lce.merchantmini.domain.dto.wallet.ActivitySimpleInfo;
import com.lce.merchantmini.domain.dto.wallet.MoneyLogInfo;
import com.lce.merchantmini.domain.dto.wallet.TicketWalletInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 支付与提现
 */
@Repository
@Mapper
public interface MerMoneyMapper {
    /**
     *
     * @param params
     * @return
     */
    public BigDecimal getTicketsIncomeByStatus(Map params);

    /**
     * 获取商家的总充值或者总提现
     * @param params mId:商家主键 operation:1为充值，0为提现
     * @return
     */
    public BigDecimal getMerchantLog(Map params);

    /**
     * 完结的活动的收入
     * @param mId
     * @return
     */
    public BigDecimal getOverTime(Integer mId);

    /**
     * 不准退款但未过时的活动收入
     * @param mId
     * @return
     */
    public BigDecimal getNotAllowRefund(Integer mId);

    /**
     * 匹配openid
     * @param params
     * @return
     */
    public String matchOpenId(Map params);

    /**
     * 增加充值日志
     * @param log
     * @return
     */
    public Integer addMoneyLog(MerMoneyLog log);

    /**
     * 通过Mid找openid
     * @param mId
     * @return
     */
    public String findOpenIdByMId(Integer mId);

    /**
     * 增加提现日志
     * @param log
     * @return
     */
    public Integer addBackMoneyLog(MerMoneyLog log);

    /**
     * 票的情况
     * @param mId
     * @return
     */
    public List<TicketWalletInfo> getAllTicketWalletInfo(Integer mId);

    /**
     * 提现和充值情况
     * @param params mId，month ,status 0:只要提现，1:只要充值
     * @return
     */
    public List<MoneyLogInfo> getMoneyLog(Map params);

    /**
     * 根据状态获取票的情况
     * @param params mId 商家唯一标识，status:状态
     * @return
     */
    public List<TicketWalletInfo> getTicketInfoByStatus(Map params);

    /**
     * 获取指定用户的活动信息
     * @param mId
     * @return
     */
    public List<ActivitySimpleInfo> getActivityInfos(Integer mId);
}

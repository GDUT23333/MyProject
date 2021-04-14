package com.lce.merchantmini.mapper;

import com.lce.merchantmini.domain.dto.ActivityInfo;
import com.lce.merchantmini.domain.dto.RegisteredMemberDetail;
import com.lce.merchantmini.domain.vo.*;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface MerControlActivityMapper {

    public String checkIsComplete(Integer mId);
    /**
     * 根据状态展示所有活动
     * @param params
     * @return
     */
    public List<ActivitySimpleInfo> showActivityByStatus(Map params);

    /**
     * 展示所有活动
     * @param mId
     * @return
     */
    public List<ActivitySimpleInfo> showActivity(Integer mId);
    /**
     * 商家活动管理页面回显
     * @param activityId 活动id
     * @return
     */
    public ActivityDataShow getActivityDetail(Integer activityId);

    /**
     * 统计商家活动未回答的问题
     * @param activityId
     * @return
     */
    public Integer countTotalConsultById(Integer activityId);

    /**
     * 回显已经报名指定活动的人
     * @param activityId 活动id
     * @return
     */
    public List<RegisteredMember> getRegisteredMembers(Integer activityId);
    /**
     * 回显已经报名指定活动的人
     * @param params 搜索关键词和活动id
     * @return
     */
    public List<RegisteredMember> searchRegisteredMembers(Map params);

    /**
     * 获取指定报名人的报名表
     * @param params
     * @return
     */
    public List<FormTable> getMembersDetails(Map params);

    /**
     * 获取指定报名人的额外信息
     * @param mId
     * @return
     */
    public RegisteredMemberDetail getMemberDetailsAddition(String mId);

    /**
     * 回显活动信息
     * @param activityId
     * @return
     */
    public ActivityInfo getActInfo(Integer activityId);

    /**
     * 更新活动信息，除了票
     * @param info
     */
    public void updateActInfo(ActivityInfo info);

    /**
     * 更新指定活动的票
     * @param tickets 指定活动的票
     */
    public void updateTickets(List<SimpleTicketInfo> tickets);

    /**
     * 检验是否已经绑定公众号的openid
     * @param mId
     * @return
     */
    public Integer checkIsSocket(Integer mId);
    /**
     * 改变活动通知状态
     * 0：关闭 1：开启
     * @param params activityId status
     */
    public void changeInformStatus(Map params);
}

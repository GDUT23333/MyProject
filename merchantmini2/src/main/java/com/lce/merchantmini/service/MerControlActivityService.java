package com.lce.merchantmini.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lce.merchantmini.domain.dto.ActivityInfo;
import com.lce.merchantmini.domain.dto.RegisteredMemberDetail;
import com.lce.merchantmini.domain.vo.ActivitySimpleInfo;
import com.lce.merchantmini.domain.vo.ActivityDataShow;
import com.lce.merchantmini.domain.vo.RegisteredMember;

import java.io.UnsupportedEncodingException;
import java.util.List;

public interface MerControlActivityService {
    /**
     * 获取指定活动详情
     * @param activityId
     * @return
     */
    public ActivityDataShow getActivityDetail(Integer activityId);

    /**
     * 获取报名人的信息
     * @param activityId
     * @return
     */
    public List<RegisteredMember> getRegisteredMembers(Integer activityId);

    /**
     * 根据特定信息搜索报名人
     * @param activityId
     * @param content
     * @return
     * @throws UnsupportedEncodingException
     */
    public List<RegisteredMember> searchRegisteredMembers(Integer activityId,String content) throws UnsupportedEncodingException;

    /**
     * 获取指定报名表详情
     * @param activityId
     * @param mId
     * @return
     */
    public RegisteredMemberDetail getRegisteredDetail(Integer activityId, String mId);

    /**
     * 回显活动信息
     * @param activityId
     * @return
     */
    public ActivityInfo getActInfo(Integer activityId);

    /**
     *
     * @param info
     */
    public void updateActivity(ActivityInfo info);

    /**
     * 展示所有活动和
     * @param mId
     * @return
     */
    public List<ActivitySimpleInfo> showActivity(Integer mId);

    /**
     * 根据状态显示活动
     * @param mId
     * @param status
     * @return
     */
    public List<ActivitySimpleInfo> showActivityByStatus(Integer mId,Integer status);


    /**
     * 改变指定活动的通知状态
     * @param mId
     * @param activityId
     * @param status
     *
     * @return
     */
    public boolean changeInformStatus(Integer mId,Integer activityId,Integer status);
}

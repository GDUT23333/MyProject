package com.lce.merchantmini.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lce.common.exception.ErrorException;
import com.lce.common.util.TimeUtil;
import com.lce.merchantmini.domain.dto.ActivityInfo;
import com.lce.merchantmini.domain.dto.RegisteredMemberDetail;
import com.lce.merchantmini.domain.vo.ActivitySimpleInfo;
import com.lce.merchantmini.domain.vo.FormTable;
import com.lce.merchantmini.domain.vo.ActivityDataShow;
import com.lce.merchantmini.domain.vo.RegisteredMember;
import com.lce.merchantmini.mapper.MerControlActivityMapper;
import com.lce.merchantmini.service.MerControlActivityService;
import com.lce.merchantmini.utils.DivStringUtils;
import com.lce.merchantmini.utils.TimeChangeUtils;
import org.bouncycastle.asn1.x509.Time;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MerControlActivityServiceImpl implements MerControlActivityService {

    private MerControlActivityMapper merMapper;

    @Autowired
    public void setMer(MerControlActivityMapper merMapper) {
        this.merMapper = merMapper;
    }

    /**
     * 活动管理页面信息回显
     * @param activityId
     * @return
     */
    @Override
    public ActivityDataShow getActivityDetail(Integer activityId) {
        ActivityDataShow activityDetail = this.merMapper.getActivityDetail(activityId);
        activityDetail.setWaitReply(this.merMapper.countTotalConsultById(activityId));
        return activityDetail;
    }

    /**
     * 获取指定商家活动的报名人信息
     * @param activityId
     * @return
     */
    @Override
    public List<RegisteredMember> getRegisteredMembers(Integer activityId) {
        return this.merMapper.getRegisteredMembers(activityId);
    }

    /**
     * 条件搜索指定商家活动报名人信息
     * @param activityId
     * @param content
     * @return
     */
    @Override
    public List<RegisteredMember> searchRegisteredMembers(Integer activityId, String content) throws UnsupportedEncodingException {
        //搜索内容为空就返回所有数据
        if(DivStringUtils.isEmpty(content)){
            return this.merMapper.getRegisteredMembers(activityId);
        }
        //进行指定搜索
        Map<String, Object> params = new HashMap<>();
        params.put("activityId",activityId);
        params.put("content",content);
        return this.merMapper.searchRegisteredMembers(params);
    }

    /**
     * 回显指定报名者信息
     * @param activityId 活动Id
     * @param mId 报名者的id
     * @return
     */
    @Override
    public RegisteredMemberDetail getRegisteredDetail(Integer activityId, String mId) {
        //拿到报名表
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("activityId",activityId);
        params.put("mId",mId);
        List<FormTable> table = this.merMapper.getMembersDetails(params);
        for (FormTable form : table) {
            //多选情况下
            if(form.getPropertyType() == 3){
                String[] split = form.getProperty().split(";");
                form.setProperty(split[0]);
                String[] info= new String[split.length - 1];
                System.arraycopy(split,1,info,0,split.length - 1);
                form.setContent(info);
                form.setTableContents(form.getTableValue().split(";"));
            }
        }
        RegisteredMemberDetail addition = this.merMapper.getMemberDetailsAddition(mId);
        addition.setTables(table);
        return addition;
    }

    /**
     * 回显修改活动页面
     * @param activityId 活动Id
     * @return
     */

    @Override
    public ActivityInfo getActInfo(Integer activityId) {
        return this.merMapper.getActInfo(activityId);
    }

    /**
     * 修改活动信息
     * @param info 更新后的活动信息
     */
    @Transactional
    @Override
    public void updateActivity(ActivityInfo info) {
        return;
    }

    @Override
    public List<ActivitySimpleInfo> showActivity(Integer mId) {
        return this.merMapper.showActivity(mId);
    }


    @Override
    public List<ActivitySimpleInfo> showActivityByStatus(Integer mId,Integer status) {
        Map params = new HashMap<>(2);
        params.put("mId",mId);
        params.put("status",status);

        //检查资料是否已经完整
        if(!DivStringUtils.isEmpty(this.merMapper.checkIsComplete(mId))){
            return this.merMapper.showActivityByStatus(params);
        }
        throw new ErrorException("500","未完善信息");
    }


    /**
     * 改变指定活动状态
     * @param activityId
     * @param status
     */
    @Override
    public boolean changeInformStatus(Integer mId,Integer activityId, Integer status) {
        //检查是否已经绑定公众号
        Integer isSocket = this.merMapper.checkIsSocket(mId);
        //如果已经绑定
        if(isSocket == 1){
            Map<String,Integer> params = new HashMap<String,Integer>(2);
            params.put("activityId",activityId);
            params.put("status",status);
            this.merMapper.changeInformStatus(params);
            return true;
        }
        //如果未绑定
        return false;
    }
}

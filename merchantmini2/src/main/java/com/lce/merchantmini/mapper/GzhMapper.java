package com.lce.merchantmini.mapper;

import com.lce.merchantmini.domain.dso.GzhUserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Map;


/**
 * @author: Ember
 * @create: 2021-02-28 19:56
 * @Description:
 **/

@Repository
@Mapper
public interface GzhMapper {
    /**
     * 关联公众号(没用版)
     * @param info
     */
    public void socketGzh(GzhUserInfo info);

    /**
     * 关联公众号,使用活动id和公众号openid
     * @param params activityId gzhOpenId
     */
    public void socketGzhByActivityId(Map params);
    /**
     * 检验是否已经绑定公众号openid
     * @param mId
     * @return
     */
    public Integer checkIsSocket(Integer mId);

    /**
     * 根据id获取活动名
     * @param id
     * @return
     */
    public String getActNameByID(Integer id);
}

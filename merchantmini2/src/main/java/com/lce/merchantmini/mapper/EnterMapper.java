package com.lce.merchantmini.mapper;

import com.lce.merchantmini.domain.dto.EnterDetail;
import com.lce.merchantmini.domain.dto.InvitedInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 入驻和邀请登录
 */
@Repository
@Mapper
public interface EnterMapper {
    /**
     * 检查是否已经入库(可能后面需求会添上) 0:表示公司与手机都未被入驻申请
     * @param detail 入驻信息
     * @return
     */
    public Integer checkIsEnter(EnterDetail detail);

    /**
     * 入驻申请
     * @param detail
     * @return
     */
    public Integer doEnter(EnterDetail detail);

    /**
     * 检查是否已经被邀请过
     * @param info
     * @return
     */
    public Integer checkIsInvited(InvitedInfo info);
    /**
     * 邀请人当管理员
     * @param info 被邀请人信息
     * @return
     */
    public Integer invite(InvitedInfo info);

    /**
     * 踢出管理团队
     * @param id
     * @return
     */
    public Integer dropManager(Integer id);
}

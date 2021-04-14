package com.lce.merchantmini.service;

import com.lce.merchantmini.domain.dto.EnterDetail;
import com.lce.merchantmini.domain.dto.InvitedInfo;

/**
 * 入驻
 */
public interface EnterService {
    /**
     * 入驻
     * @param detail
     * @return
     */
    public Integer doEnter(EnterDetail detail);
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

package com.lce.merchantmini.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Author: Ember
 * @Date: 2021/3/31 22:21
 * @Description: 被邀请人信息
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class InvitedInfo {
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 头像URL
     */
    private String headPitcher;
    /**
     * 前端传的jsCode，用来获取openid
   */
    private String jsCode;
    /**
     * openid
     */
    private String openId;
    /**
     * 角色，0:运营 1:管理者
     */
    private Integer role;
    /**
     * 商家账号唯一标识
     */
    private Integer mId;
}

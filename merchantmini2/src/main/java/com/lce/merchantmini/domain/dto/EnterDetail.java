package com.lce.merchantmini.domain.dto;

import lombok.*;

import java.io.Serializable;

/**
 * 入驻详情
 */
@Data
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@ToString
public class EnterDetail implements Serializable {
    /**主键**/
    private Integer id;
    /**企业组织名称**/
    private String merchantCompany;
    /**负责人名字**/
    private String merchantName;
    /**主要活动类型**/
    private String activityType;
    /**运营平台**/
    private String platform;
    /**是否同意入驻 0:未入驻，1:以入驻**/
    private Integer isAgree;
    /**手机号**/
    private String phone;
    /**openid**/
    private String openId;
    /**jsCode**/
    private String jsCode;
    /**提交时间**/
    private String createTime;
    /**
     * unionId用来区别公众号跟他是否为同一个用户
     */
    private String unionId;
}

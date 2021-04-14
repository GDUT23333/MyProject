package com.lce.merchantmini.domain.dto;

import lombok.*;

/**
 * 商家个人信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class MerchantInfo {
    //商家登录时主键
    private Integer id;
    //组织名
    private String nickName;
    //介绍
    private String introduction;
    //手机号
    private String phone;
    //头像
    private String imgShowUrl;
}

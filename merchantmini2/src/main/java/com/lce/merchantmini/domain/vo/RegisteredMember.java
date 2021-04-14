package com.lce.merchantmini.domain.vo;

import lombok.*;

/**
 * 报名的人
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class    RegisteredMember {

    //主键
    private String mId;
    //名字
    private String name;
    //学校
    private String school;
    //拥有的票
    private String ticketName;
    //手机号
    private String phone;
    //性别 1:为男，0为女
    private Integer gender;
    //状态，0待支付，1已支付，2退款中，5已退款,6该票委托平台退款，等待平台确定退款
    private Integer status;
}

package com.lce.merchantmini.domain.vo;

import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Consult {
    //主键
    private Integer id;
    //活动的Id
    private Integer aId;
    //回复
    private String reply;
    //问题
    private String question;
    //是否已经回答
    private Integer status;
    //提出问题用户的openId
    private String openId;
    //用户的昵称
    private String nickName;
    //活动名字
    private String activityName;
    //用户头像
    private String portrait;
    //日期
    private Date createTime;
}

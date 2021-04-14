package com.lce.merchantmini.domain.vo;

import lombok.*;

import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 商家活动管理页面数据回显
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class ActivityDataShow {
    //主键
    private Integer activityId;
    //活动名称
    private String activityName;
    //背景图
    private String backGround;
    //时间
    private Date startTime;
    //活动状态
    private Integer status;
    //报名的人数
    private Integer registryNumber;
    //总票款
    @DecimalMin("0.00")
    private BigDecimal totalPrice;
    //票
    private List<SimpleTicketInfo> tickets;
    //今日热度
    private Integer todayClickRate;
    //未回答的问题
    private Integer waitReply;
    //评论
    private Integer commentCount;
    //是否接收提醒
    private Integer isAccept;
}

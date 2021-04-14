package com.lce.merchantmini.domain.vo;

import lombok.*;

import java.util.Date;

/**
 * 活动列表中的活动信息
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class ActivitySimpleInfo {
    private Integer activityId;
    private String posterImage;
    private String activityName;
    private String address;
    private Date startTime;
    private Date registrationDeadLine;
    private Integer status;
}

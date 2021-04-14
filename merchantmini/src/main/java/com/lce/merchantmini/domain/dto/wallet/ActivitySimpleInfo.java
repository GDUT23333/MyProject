package com.lce.merchantmini.domain.dto.wallet;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Author: Ember
 * @Date: 2021/4/9 10:05
 * @Description: 活动简单信息
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ActivitySimpleInfo {
    private String activityName;
    private Integer activityId;
}

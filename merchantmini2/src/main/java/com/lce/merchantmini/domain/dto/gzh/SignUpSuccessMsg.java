package com.lce.merchantmini.domain.dto.gzh;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 公众号报名提醒模板
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SignUpSuccessMsg extends BaseMessage{
    /**
     * 活动名称
     */
    private MessageItem activityName;
    /**
     * 报名者名字
     */
    private MessageItem name;
    /**
     * 报名者电话
     */
    private MessageItem phone;
}

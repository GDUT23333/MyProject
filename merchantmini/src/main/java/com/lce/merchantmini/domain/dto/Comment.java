package com.lce.merchantmini.domain.dto;

import lombok.*;

/**
 * 活动评论
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Comment {

    private Integer id;
    private String content;
    private String reply;
    private String nickName;
    private String portrait;
    private String createTime;
    private String activityName;
}

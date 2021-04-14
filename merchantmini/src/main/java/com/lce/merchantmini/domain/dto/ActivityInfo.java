package com.lce.merchantmini.domain.dto;

import com.lce.merchantmini.domain.vo.CompleteTicketInfo;
import com.lce.merchantmini.domain.vo.SimpleTicketInfo;
import lombok.*;

import java.util.Date;
import java.util.List;

/**
 * 更改活动的信息回显
 */
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ActivityInfo {
    /**
     * 第一部分
     */
    private Integer status;
    private Integer aId;
    private String activityName;
    private String startTime;
    private String registerDeadLine;
    private String endTime;
    private String address;
    private String videoUrl;
    private String posterImg;

    /**
     * 第二部分
     */
    private String linkManCode;
    private String groupCode;
    private String announcement;
    private String activityDetail;

    /**
     * 第三部分
     */
    private List<CompleteTicketInfo> tickets;
}

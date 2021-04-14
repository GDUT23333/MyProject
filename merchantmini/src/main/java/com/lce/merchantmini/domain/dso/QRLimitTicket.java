package com.lce.merchantmini.domain.dso;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Negative;

/**
 * @Author: Ember
 * @Date: 2021/3/19 16:51
 * @Description: QRLimitTicket 永久的二维码
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class QRLimitTicket {


    @JSONField(name = "action_name")
    private String actionName;
    @JSONField(name = "action_info")
    private ActionInfo info;
    public QRLimitTicket(String actionName, String sceneStr) {
        this.actionName = actionName;
        this.info = new ActionInfo(sceneStr);
    }
}

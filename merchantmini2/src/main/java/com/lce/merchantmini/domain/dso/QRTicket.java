package com.lce.merchantmini.domain.dso;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Author: Ember
 * @Date: 2021/3/20 17:51
 * @Description: 临时的二维码
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class QRTicket extends QRLimitTicket{
    @JSONField(name = "expire_seconds")
    private Integer expireSeconds;

    public QRTicket(String actionName,Integer expireSeconds ,String sceneStr) {
        super(actionName, sceneStr);
        this.expireSeconds = expireSeconds;
    }
}

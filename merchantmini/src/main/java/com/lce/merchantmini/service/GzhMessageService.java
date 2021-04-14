package com.lce.merchantmini.service;

import com.lce.merchantmini.domain.dso.GzhToken;
import com.lce.merchantmini.domain.dto.gzh.*;
/**
 * @Author: Ember
 * @Date: 2021/3/17 17:21
 * @Description:
 */
public interface GzhMessageService {
    /**
     * 发送消息模板
     * @param msg
     */
    public void sendMessage(BaseMessage msg);
}

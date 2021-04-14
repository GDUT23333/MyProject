package com.lce.merchantmini.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lce.common.exception.ErrorException;
import com.lce.common.util.HttpUtil;
import com.lce.merchantmini.domain.dso.GzhToken;
import com.lce.merchantmini.domain.dto.gzh.*;
import com.lce.merchantmini.domain.enums.GzhEnums;
import com.lce.merchantmini.mapper.GzhMapper;
import com.lce.merchantmini.service.GzhMessageService;
import com.lce.merchantmini.service.GzhService;
import com.lce.merchantmini.utils.GzhSendMessageFactory;
import com.lce.merchantmini.utils.MyGzhConfigUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
/**
 * @author: Ember
 * @create: 2021-02-28 19:56
 * @Description:
 **/
@Service
@Slf4j
public class GzhMessageServiceImpl implements GzhMessageService {

    private MyGzhConfigUtils configUtils;
    private GzhMapper gzhMapper;
    private GzhService gzhService;
    @Autowired
    public void setConfigUtils(MyGzhConfigUtils configUtils) {
        this.configUtils = configUtils;
    }

    @Autowired
    public void setGzhMapper(GzhMapper gzhMapper) {
        this.gzhMapper = gzhMapper;
    }

    @Autowired
    public void setGzhService(GzhService gzhService) {
        this.gzhService = gzhService;
    }

    /**
     * 发送模板消息
     * @param msg 模板消息
     */
    @Override
    public void sendMessage(BaseMessage msg) {
        GzhSendMessageFactory factory = new GzhSendMessageFactory(msg);
        String token = this.gzhService.getGzhAccessToken();
        factory.send(token);
    }

}

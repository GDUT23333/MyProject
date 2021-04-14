package com.lce.merchantmini.service.impl;

import com.alibaba.fastjson.JSON;
import com.lce.common.exception.entry.GetOpenidFailException;
import com.lce.common.util.HttpClientUtil;
import com.lce.common.util.TimeUtil;
import com.lce.merchantmini.domain.dso.Code2SessionInfo;
import com.lce.merchantmini.domain.dto.EnterDetail;
import com.lce.merchantmini.domain.dto.InvitedInfo;
import com.lce.merchantmini.domain.enums.WxInfoEnum;
import com.lce.merchantmini.mapper.EnterMapper;
import com.lce.merchantmini.service.EnterService;
import com.lce.merchantmini.utils.GetWxInfoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.util.HashMap;
import java.util.Map;


/**
 * 入驻与邀请
 */
@Service
public class EnterServiceImpl implements EnterService {

    private EnterMapper enterMapper;
    private GetWxInfoUtils infoUtils;
    @Autowired
    public void setEnterMapper(EnterMapper enterMapper) {
        this.enterMapper = enterMapper;
    }

    @Autowired
    public void setInfoUtils(GetWxInfoUtils infoUtils) {
        this.infoUtils = infoUtils;
    }

    /**
     * 入驻
     * @param detail
     * @return
     */
    @Override
    public Integer doEnter(EnterDetail detail) {
        Code2SessionInfo info = getOpenId(detail.getJsCode());
        detail.setCreateTime(TimeUtil.timeInitInString());
        detail.setOpenId(info.getOpenid());
        detail.setUnionId(info.getUnionId());
        return this.enterMapper.doEnter(detail);
    }

    /**
     * 邀请管理员
     * @param info 被邀请人信息
     * @return
     */
    @Override
    public Integer invite(InvitedInfo info) {
        if(this.enterMapper.checkIsInvited(info) == 1){
            return 0;
        }
        Code2SessionInfo code2SessionInfo = getOpenId(info.getJsCode());
        info.setOpenId(code2SessionInfo.getOpenid());
        return this.enterMapper.invite(info);
    }

    /**
     * 删除管理员
     * @param id
     * @return
     */
    @Override
    public Integer dropManager(Integer id) {
        return this.enterMapper.dropManager(id);
    }

    /**
     * 获取openId
     * @param jsCode
     * @return
     */
    private Code2SessionInfo getOpenId(String jsCode){
        //获取openId
        String url = WxInfoEnum.CODE2SESSION_URL.getValue();
        //封装参数
        Map<String,String> params = new HashMap<>();
        params.put("appid",this.infoUtils.getAppID());
        params.put("secret",infoUtils.getAppSecret());
        params.put("js_code",jsCode);
        params.put("grant_type",WxInfoEnum.CODE2SESSION_GRANT_TYPE.getValue());
        String result = HttpClientUtil.doGet(url,params);
        Code2SessionInfo info = JSON.parseObject(result, Code2SessionInfo.class);
        //获取openid失败
        if (!StringUtils.isEmpty(info.getErrCode()) || StringUtils.isEmpty(info.getOpenid())) {
            //请求失败
            throw new GetOpenidFailException(Long.parseLong(info.getErrCode()), info.getErrMsg());
        }
        return info;
    }
}

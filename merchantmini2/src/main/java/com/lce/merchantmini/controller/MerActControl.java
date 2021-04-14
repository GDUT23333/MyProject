package com.lce.merchantmini.controller;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.lce.common.util.BaseResult;
import com.lce.common.util.DataResult;
import com.lce.merchantmini.domain.dto.ActivityInfo;
import com.lce.merchantmini.domain.enums.GzhEnums;
import com.lce.merchantmini.service.MerControlActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.io.UnsupportedEncodingException;

/**
 * 管理活动模块
 */
@RestController
@RequestMapping("/merchant/activity")
public class MerActControl {
    private MerControlActivityService merService;

    @Autowired
    public void setMerService(MerControlActivityService merService) {
        this.merService = merService;
    }

    /**
     * 获取所有活动
     * @param mId
     * @return
     */
    @PostMapping("/showActivity")
    public BaseResult getAllActivity(@RequestBody String mId){
        Integer umaiId = Integer.parseInt(JSON.parseObject(mId).get("mId").toString());
        return DataResult.success(this.merService.showActivity(umaiId));
    }
    /**
     * 根据状态获取活动
     * @param mId
     * @return
     */
    @PostMapping("/showActivityByStatus")
    public BaseResult getAllActivityByStatus(@RequestParam Integer mId,@RequestParam Integer status){
        return DataResult.success(this.merService.showActivityByStatus(mId,status));
    }

    /**
     * 活动管理页面回显
     * @param activityId
     * @return
     */
    @GetMapping("/info/{activityId}")
    public BaseResult controlPageShow(@PathVariable("activityId") int activityId){
        return DataResult.success(this.merService.getActivityDetail(activityId));
    }

    /**
     * 获取指定商家活动所有报名者
     * @param activityId
     * @return
     */
    @GetMapping("/info/members/{activityId}")
    public BaseResult getRegisteredMembers(@PathVariable("activityId") int activityId){
        return DataResult.success(this.merService.getRegisteredMembers(activityId));
    }

    /**
     * 特定搜索指定商家活动报名者
     * @param activityId
     * @param content
     * @return
     */
    @GetMapping("/info/members/{activityId}/{content}")
    public BaseResult searchRegisteredMembers(@PathVariable("activityId") int activityId,@PathVariable("content") String content) throws UnsupportedEncodingException {
        return DataResult.success(this.merService.searchRegisteredMembers(activityId,content));
    }

    /**
     * 获取报名成员信息
     * @param activityId
     * @param mId
     * @return
     */
    @PostMapping("/info/members/detail")
    public BaseResult getRegisteredDetail(@RequestParam Integer activityId,@RequestParam String mId){
        return DataResult.success(this.merService.getRegisteredDetail(activityId,mId));
    }

    /**
     * 回显活动信息
     * @param activityId
     * @return
     */
    @GetMapping("/actInfo/{activityId}")
    public BaseResult getActInfo(@PathVariable("activityId") Integer activityId){
        return DataResult.success(this.merService.getActInfo(activityId));
    }

    /**
     * 修改活动信息
     * @param info 更新后的活动信息
     * @return
     */
    @PostMapping("/actInfo/updateActInfo")
    public BaseResult updateActivityInfo(@RequestBody ActivityInfo info){
        this.merService.updateActivity(info);
        return BaseResult.success();
    }

    /**
     * 修改活动报名通知(是否开启、关闭)
     * @param activityId
     * @param status
     * @return
     */
    @PostMapping("/actInfo/updateStatus")
    public BaseResult changeInformStatus(@RequestParam Integer activityId,@RequestParam Integer status,@RequestParam Integer mId){
        if(this.merService.changeInformStatus(mId,activityId,status)){
            return BaseResult.success();
        }else{
            return BaseResult.error(GzhEnums.NOT_SOCKET.getKey(),GzhEnums.NOT_SOCKET.getValue());
        }
    }
}

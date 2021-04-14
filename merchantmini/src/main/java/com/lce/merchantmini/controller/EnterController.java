package com.lce.merchantmini.controller;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.lce.common.util.BaseResult;
import com.lce.merchantmini.domain.dto.EnterDetail;
import com.lce.merchantmini.domain.dto.InvitedInfo;
import com.lce.merchantmini.service.EnterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 入驻与登录
 */
@RestController
@RequestMapping("/merchant/enter/one")
public class EnterController {

    private EnterService enterService;

    @Autowired
    public void setEnterService(EnterService enterService) {
        this.enterService = enterService;
    }


    /**
     * 入驻
     * @param detail 入驻商家信息
     * @return
     */
    @PostMapping("/doEnter")
    public BaseResult doEnter(@RequestBody EnterDetail detail){
        int result = this.enterService.doEnter(detail);
        if(result == 1){
            return BaseResult.success();
        }
        return BaseResult.error("300","信息入库失败");
    }

    /**
     * 删除指定管理员
     * @param id 管理员唯一标识
     * @return
     */
    @DeleteMapping("/deleteManager/{id}")
    public BaseResult dropManager(@PathVariable("id") Integer id){
        this.enterService.dropManager(id);
        return BaseResult.success();
    }

    /**
     * 添加管理员
     * @param info
     * @return
     */
    @PostMapping("/addManager")
    public BaseResult addManager(@RequestBody InvitedInfo info){
        int result = this.enterService.invite(info);
        if (result == 1){
            return  BaseResult.success();
        }
        return BaseResult.error("500","管理员已经存在，请勿重复添加");
    }
}

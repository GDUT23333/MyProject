package com.lce.merchantmini.controller;

import com.lce.common.util.BaseResult;
import com.lce.common.util.DataResult;
import com.lce.merchantmini.service.GzhService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;


/**
 * @Author: Ember
 * @Date: 2021/3/15 17:22
 * @Description: 公众号接口
 */
@RestController
@RequestMapping("/gzh")
public class GzhController {

    private GzhService gzhService;

    @Autowired
    public void setGzhService(GzhService gzhService) {
        this.gzhService = gzhService;
    }

    /**
     * 重定向请求获取code
     * @param response
     * @return
     */
    @GetMapping("/authorize")
    public BaseResult authorize(HttpServletResponse response){
        this.gzhService.authorize(response);
        return BaseResult.success();
    }


    /**
     * 通过回调，获取code
     * @param code
     * @param state
     * @return
     */
    @GetMapping("/getCode")
    public BaseResult getCode(@RequestParam String code,@RequestParam String state){
        this.gzhService.getCode(code,state);
        return BaseResult.success();
    }

    /**
     * 生成二维码，临时与永久
     * 1. QRTicket 临时
     * 2. QRLimitTicket:永久
     * 3.上线如果不对外开放可以注释掉
     * @return
     */
    @PostMapping("/getQRTicket")
    public BaseResult getQRTicket(@RequestParam Integer activityId,@RequestParam Integer status){
        return DataResult.success(this.gzhService.createTicket(activityId,status));
    }
    @GetMapping("/getQRLimitTicket")
    public BaseResult getQRLimitTicket(){
        return DataResult.success(this.gzhService.createLimitTicket());
    }

    /**
     * 公众号回调url
     * 1. Post是接收事件推送的，并且进行自动回复
     * 2. Get是微信验证服务器的
     * @param content
     * @param response
     * @return
     */
    @PostMapping(value = "/verifyGzh",produces = "application/xml;charset=utf-8")
    public String receiveGzhMsg(@RequestBody String content, HttpServletResponse response){
        return this.gzhService.receive(content);
    }
    @GetMapping("/verifyGzh")
    public String verifyGzh(@RequestParam String signature, @RequestParam Integer timestamp,
                            @RequestParam String nonce, @RequestParam String echostr){
        //todo
        //这里还需进行验证是否是微信的官方请求。

        return echostr;
    }
}

package com.lce.merchantmini.controller;

import com.lce.common.util.BaseResult;
import com.lce.common.util.DataResult;
import com.lce.merchantmini.service.MerActConsultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/merchant/consult")
public class MerActConsultController {

    private MerActConsultService consultService;

    @Autowired
    public void setConsultService(MerActConsultService consultService) {
        this.consultService = consultService;
    }

    /**
     * 商家查看所有活动的问题
     * @param mId
     * @return
     */
    @PostMapping("/getAllConsult")
    public BaseResult getAllConsult(@RequestParam Integer mId){
        return DataResult.success(this.consultService.getAllConsult(mId));
    }

    /**
     * 商家回复
     * @param
     * @return
     */
    @PostMapping("/reply")
    public BaseResult reply(@RequestParam Integer aId, @RequestParam String reply){
        Integer result = this.consultService.reply(aId,reply);
        if(result != 1){
            return DataResult.error("302","评论失败");
        }
        return DataResult.success();
    }

    /**
     * 获取所有评论
     * @param mId
     * @return
     */
    @PostMapping("/getAllComment")
    public BaseResult getAllComment(@RequestParam Integer mId){
        return DataResult.success(this.consultService.getAllComment(mId));
    }
    @PostMapping("/replyComment")
    public BaseResult replyComment(@RequestParam Integer cId,@RequestParam String reply){
        Integer result = this.consultService.replyComment(cId,reply);
        if(result != 1){
            return DataResult.error("302","评论失败");
        }
        return DataResult.success();
    }
}

package com.lce.merchantmini.controller;

import com.lce.common.util.BaseResult;
import com.lce.common.util.DataResult;
import com.lce.common.util.cos.FileUtil;
import com.lce.merchantmini.domain.dto.MerchantInfo;
import com.lce.merchantmini.service.MerchantInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/merchant/info")
public class MerchantController {

    private MerchantInfoService merchantInfoService;
    private FileUtil fileUtil;

    @Autowired
    public void setMerchantInfoService(MerchantInfoService merchantInfoService) {
        this.merchantInfoService = merchantInfoService;
    }

    @Autowired
    public void setFileUtil(FileUtil fileUtil) {
        this.fileUtil = fileUtil;
    }

    /**
     * 回显商家信息
     * @param id 商家主键
     * @return
     */
    @PostMapping("/me")
    public BaseResult showInfo(@RequestParam int id){
        return DataResult.success(this.merchantInfoService.showInfo(id));
    }

    /**
     * 修改商家信息
     * @param merchantInfo
     * @return
     */
    @PostMapping("/updateInfo")
    public BaseResult updateInfo(@RequestBody MerchantInfo merchantInfo){
        this.merchantInfoService.updateInfo(merchantInfo);
        return BaseResult.success();
    }

    /**
     * 商家修改头像时进行上传，然后回显
     * @param multipartFile 上传的图片
     * @return
     */
    @PostMapping("/upLoadImg")
    public BaseResult upLoadImg(MultipartFile multipartFile){
        String url = fileUtil.uploadPicture(multipartFile);
        return DataResult.success(url);
    }


}

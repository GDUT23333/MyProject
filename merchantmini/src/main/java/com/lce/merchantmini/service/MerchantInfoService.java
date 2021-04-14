package com.lce.merchantmini.service;

import com.lce.merchantmini.domain.dto.MerchantInfo;
import org.springframework.stereotype.Service;

/**
 * 商家信息
 */
@Service
public interface MerchantInfoService {
    /**
     * 回显商家信息
     * @param id 商家登录时的主键id
     * @return
     */
    public MerchantInfo showInfo(int id);

    /**
     * 修改商家信息
     * @param info
     */
    public void updateInfo(MerchantInfo info);
}

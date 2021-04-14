package com.lce.merchantmini.service.impl;

import com.lce.merchantmini.domain.dto.MerchantInfo;
import com.lce.merchantmini.mapper.MerchantInfoMapper;
import com.lce.merchantmini.service.MerchantInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MerchantInfoServiceImpl implements MerchantInfoService {

    private MerchantInfoMapper merchantInfoMapper;

    @Autowired
    public void setMerchantInfoMapper(MerchantInfoMapper merchantInfoMapper) {
        this.merchantInfoMapper = merchantInfoMapper;
    }

    @Override
    public MerchantInfo showInfo(int id) {
        MerchantInfo info = this.merchantInfoMapper.showInfo(id);
        info.setId(id);
        return info;
    }

    @Override
    public void updateInfo(MerchantInfo info) {
        this.merchantInfoMapper.updateInfo(info);
    }
}

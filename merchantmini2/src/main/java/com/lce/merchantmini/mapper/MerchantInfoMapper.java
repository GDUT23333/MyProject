package com.lce.merchantmini.mapper;

import com.lce.merchantmini.domain.dto.MerchantInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 商家个人信息模块
 */
@Repository
@Mapper
public interface MerchantInfoMapper {
    public MerchantInfo showInfo(int id);
    public Integer updateInfo(MerchantInfo info);
}

package com.mjh.www.dao;

import com.mjh.www.bean.Msg;
import com.mjh.www.po.Page;

public interface PageShowInterface {
    public Msg searchAll(Page page);
    public Msg findAll(String email,Page page);
    public Msg findMerchantTotalCount(String email);
    public Msg findAllTotalCount(String inf);
}

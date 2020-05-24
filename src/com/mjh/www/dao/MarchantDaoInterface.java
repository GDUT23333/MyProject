package com.mjh.www.dao;

import com.mjh.www.bean.Msg;
import com.mjh.www.po.MerChant;
import com.mjh.www.po.Page;
import com.mjh.www.po.Route;

public interface MarchantDaoInterface {
    public Msg checkEmail(String email);
    public Msg registerMarc(MerChant merChant);
    public Msg active(String code);
    public Msg modify(MerChant merChant);
    public Msg checkPhone(String phone);
    public Msg deleteCommodity(int id);
    public Msg modifyCommodity(Route route,MerChant merChant);
    public Msg checkDetail(int id);
    public Msg addCommodity(Route route,MerChant merChant);
}

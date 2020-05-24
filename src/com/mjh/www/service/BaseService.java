package com.mjh.www.service;

import com.mjh.www.bean.Msg;
import com.mjh.www.dao.BaseLoginDao;
import com.mjh.www.po.Base;

/**
 * 登录
 */
public class BaseService {
    BaseLoginDao baseLoginDao = new BaseLoginDao();
    public Msg login(String email,String password){
        Base base = new Base(email,password);
        Msg msg = baseLoginDao.login(base);
        return msg;
    }
}

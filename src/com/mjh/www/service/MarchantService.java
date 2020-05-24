package com.mjh.www.service;

import com.mjh.www.bean.Msg;
import com.mjh.www.dao.MarchantDao;
import com.mjh.www.po.MerChant;
import com.mjh.www.po.Page;
import com.mjh.www.po.Pitcher;
import com.mjh.www.po.Route;

import java.util.List;

public class MarchantService {
    MarchantDao marchantDao = new MarchantDao();

    /**
     * 检验email是否已经注册
     * @param email
     * @return
     */
    public Msg checkEmail(String email){
        Msg msg = marchantDao.checkEmail(email);
        return msg;
    }

    /**
     * 检验手机号是否已经注册
     * @param phone
     * @return
     */
    public Msg checkPhone(String phone){
        Msg msg = marchantDao.checkPhone(phone);
        return msg;
    }

    /**
     * 实现注册
     * @param merChant
     * @return
     */
    public Msg regist(String code,MerChant merChant){
        merChant.setSign("");
        merChant.setStatus("N");
        merChant.setCode(code);
        Msg msg = marchantDao.registerMarc(merChant);
        return msg;
    }

    /**
     * 注册后的激活
     * @param code
     * @return
     */
    public Msg active(String code){
        Msg msg = marchantDao.active(code);
        return msg;
    }

    /**
     * 修改用户信息
     * @param merChant
     * @return
     */
    public Msg modify(MerChant merChant){
        Msg msg = marchantDao.modify(merChant);
        return msg;
    }

    /**
     * 商家删除上架的商品
     * @param id
     * @return
     */
    public Msg deleteCommodity(int id){
        Msg msg = marchantDao.deleteCommodity(id);
        return msg;
    }

    /**
     * 商家修改上架商品的信息
     * @param merChant
     * @return
     */
    public Msg modifyCommodity(String img,float price,String intro,String title,int id,List<Pitcher> lists,int category,MerChant merChant){
        Route route = new Route(img,price,intro,title,id,lists);
        route.setCategory(category);
        Msg msg = marchantDao.modifyCommodity(route,merChant);
        return msg;
    }

    /**
     * 商家查看上架的商品信息
     * @param id
     * @return
     */
    public Msg checkDetail(int id){
        Msg msg = marchantDao.checkDetail(id);
        return msg;
    }

    /**
     * 商家上架新商品
     * @param merChant
     * @return
     */
    public Msg addCommodity(int category, String img, float price, String intro, String title, MerChant merChant, List<Pitcher> lists){
        Route route = new Route(category,img,price,intro,title);
        route.setPicture(lists);
        route.setSellerAddr(merChant.getAddr());
        route.setSellerPhone(merChant.getPhone());
        Msg msg = marchantDao.addCommodity(route,merChant);
        return msg;
    }
}

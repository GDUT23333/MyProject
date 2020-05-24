package com.mjh.www.service;

import com.mjh.www.bean.Msg;
import com.mjh.www.dao.PageShowDao;
import com.mjh.www.po.Page;

public class PageService {
    PageShowDao pageShowDao = new PageShowDao();

    /**
     * 首页的展示
     * @param startLines
     * @param totalPage
     * @param pageSize
     * @param currentPage
     * @param totalCount
     * @param inf
     * @return
     */
    public Msg searchAll(int startLines,int totalPage,int pageSize,int currentPage,int totalCount,String inf){
        Page page = new Page();
        page.setStartLine(startLines);
        page.setTotalPage(totalPage);
        page.setPageSize(pageSize);
        page.setCurrentPage(currentPage);
        page.setTotalCount(totalCount);
        page.setInf(inf);
        Msg msg = pageShowDao.searchAll(page);
        return msg;
    }

    /**商家的上架商品
     *
     * @param email
     * @param currentPage
     * @param pageSize
     * @param startLine
     * @param totalPage
     * @param totalCount
     * @return
     */
    public Msg findAll(String email,int currentPage,int pageSize,int startLine,int totalPage,int totalCount){
        Page page = new Page();
        page.setCurrentPage(currentPage);
        page.setPageSize(pageSize);
        page.setStartLine(startLine);
        page.setTotalPage(totalPage);
        page.setTotalCount(totalCount);
        Msg msg = pageShowDao.findAll(email,page);
        return msg;
    }
    /**
     * 计数商家上架商品的数据数目
     */
    public Msg merchantCount(String email){
        Msg msg = pageShowDao.findMerchantTotalCount(email);
        return msg;
    }

    /**
     * 搜寻商品的总记录数
     * @param inf
     * @return
     */
    public Msg findAllTotalCount(String inf){
        Msg msg = pageShowDao.findAllTotalCount(inf);
        return msg;
    }
}

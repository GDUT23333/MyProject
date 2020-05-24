package com.mjh.www.po;

import java.util.List;

/**
 * 展示的页面类
 */

public class Page {
    private int id;//类别
    private int totalPage;//总页数
    private int currentPage;//当前页数
    private int totalCount;//总记录数
    private int pageSize;//一页显示的记录数
    private List<Route> list;//显示的数据
    private String inf;//用户搜索信息
    private List<Category> categories;
    private boolean isLogin;

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public void setList(List<Route> list) {
        this.list = list;
    }

    public Page() {
    }

    public Page(int id, int pageSize, int startLine, int totalPage, String inf) {
        this.id = id;
        this.pageSize = pageSize;
        this.startLine = startLine;
        this.totalPage = totalPage;
        this.inf = inf;
    }

    public void setStartLine(int startLine) {
        this.startLine = startLine;
    }

    public String getInf() {
        return inf;
    }

    public void setInf(String inf) {
        this.inf = inf;
    }


    private int startLine;//开始的行数

    public int getStartLine() {
        return startLine;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List getList() {
        return list;
    }

}

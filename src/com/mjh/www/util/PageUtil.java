package com.mjh.www.util;

/**
 * 得到总页数
 */
public class PageUtil {
    public static int getTotalPage(int pageSize,int totalCount){
        int totalPage = (totalCount + pageSize - 1) / pageSize;
        return totalPage;
    }
}

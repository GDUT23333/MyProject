package com.mjh.www.util;

/**
 * 得到开始行数
 */
public class StartLineUtil {
    public static int getStartLine(int currentPage,int pageSize){
        int startLine = (currentPage-1)*pageSize;
        return startLine;
    }
}

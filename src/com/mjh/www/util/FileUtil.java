package com.mjh.www.util;

import java.io.File;

/**
 * 生成图片文件
 */
public class FileUtil {
    public static File createFile(String fileName,String path){
        File file = new File(path,fileName);
        return file;
    }
}

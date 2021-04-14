package com.lce.merchantmini.utils;

import com.google.zxing.*;
import com.google.zxing.common.BitMatrix;
import com.lce.common.util.cos.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @Author: Ember
 * @Date: 2021/3/11 19:32
 * @Description: TODO 二维码生成工具类
 */

@Component
public class QRCodeUtils {
    private FileUtil fileUtil;

    @Autowired
    public void setFileUtil(FileUtil fileUtil) {
        this.fileUtil = fileUtil;
    }

    /**
     * 种子
     */
    private  int SEED = 255;
    /**
     * 线性同余发生器生成的随机数不大于M
     */
    private final int M = 10;
    /**
     * 生成随机数的最大最小值
     */
    private final int MAX = 9;
    private final int MIN = 0;

    private final int BLACK = 0xFF6071d8;
    private final int WHITE = 0xFFFFFFFF;

    private final int DEFAULT_HEIGHT = 300;
    private final int DEFAULT_WEIGHT = 300;

    private final String CHAR_SET = "utf-8";
    private final int MARGIN = 1;

    /**
     * 使用线性同余生成随机数
     * @return
     */
    private int createOneRandom(int seed){
        Random random = new Random();
        //线性同余器需要的系数
        int a = random.nextInt(1024);
        int b = random.nextInt(1024);
        int result = (a * seed + b) % M;
        return result;
    }

    /**
     * 使用线性同余器生成
     * @param seed
     * @return
     */
    private int createRandom(int seed){
        int result = MIN + seed % (MAX - MIN + 1);
        return result;
    }

    /**
     * 生成16位随机数组合
     * @return
     */
    private  String createQRCode(){
        StringBuffer buffer = new StringBuffer();
        buffer.append("QR");
        int size = 15;
        int seed = SEED;
        for (int i = 0; i < size; i++) {
            buffer.append(createOneRandom(seed));
            //重置seed
            seed = createOneRandom(seed);
        }
        return buffer.toString();
    }

    /**
     * 生成二维码图片
     * @param text
     * @return 图片上传的全路径
     */
    public String createQR(String text){
        Map<EncodeHintType, Object> params = new HashMap<>(2);
        params.put(EncodeHintType.CHARACTER_SET,CHAR_SET);
        params.put(EncodeHintType.MARGIN,MARGIN);
        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE,DEFAULT_WEIGHT,DEFAULT_HEIGHT,params);
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
            //对二维码进行上色
            //嵌套for索引坐标
            for (int i = 0; i < width; i++) {
                for(int j = 0; j < height;j++){
                    //bitMatrix.get(i,j)返回的是二维码里面那些图案的坐标
                    //对单个坐标设置rgb
                    image.setRGB(i,j,bitMatrix.get(i,j)?BLACK:WHITE);
                }
            }
            //生成临时文件
            File file = File.createTempFile("temp",null);
            ImageIO.write(image,"JPG",file);
            //进行上传
            return(fileUtil.uploadPicture(file));
        } catch (WriterException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

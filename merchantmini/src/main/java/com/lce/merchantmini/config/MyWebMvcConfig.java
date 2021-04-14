package com.lce.merchantmini.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @Author: Ember
 * @Date: 2021/3/16 21:20
 * @Description: WebMvcConfig配置
 */
@Configuration
public class MyWebMvcConfig extends WebMvcConfigurationSupport {
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        //单纯进行公众号文件映射。
        registry.addResourceHandler("/MP_verify_D5vnnRz6Ho9AYNR9.txt").addResourceLocations("classpath:/static/MP_verify_D5vnnRz6Ho9AYNR9.txt");
    }
}

package com.pingxun.web.front.controller;

import com.pingxun.web.common.filter.CwFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 控制器注册
 * Created by dujy on 2017-05-22.
 */
@Configuration
public class FrontWebConfig {

    @Bean(name = "front_loginFilter_reg")
    public FilterRegistrationBean loginFilterRegistrationBean() {
        FilterRegistrationBean bean = new CwFilter.Builder().loginUrl("/front/login.html")
                .notNeedLoginUrls("/css/login.css","/front/parameter/findParameter.json","/front/product/findProductVersion.json",
                        "/front/userInfo/upload.json")
                .urlPatterns("/front/*").supportUserType("user").build();
        bean.setName("front_loginFilter");

        return bean;
    }
}

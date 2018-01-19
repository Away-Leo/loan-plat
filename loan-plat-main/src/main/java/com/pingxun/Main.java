package com.pingxun;

import com.pingxun.core.common.base.BaseRepositoryFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * 启动类
 * Created by dujy on 2017-05-20.
 */
@SpringBootApplication
@EnableAutoConfiguration
@EnableJpaRepositories(basePackages = {"com.pingxun.biz"},
        repositoryFactoryBeanClass = BaseRepositoryFactoryBean.class//指定自己的工厂类
)
public class Main {

    public static void main(String[] args){
        System.setProperty("loan.appName","loan-plat");
        SpringApplication.run(Main.class,args);
    }

}

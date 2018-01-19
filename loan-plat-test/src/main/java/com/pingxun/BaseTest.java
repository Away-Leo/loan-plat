package com.pingxun;


import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=Main.class)// 指定spring-boot的启动类
public class BaseTest{

    private Logger log = LoggerFactory.getLogger(BaseTest.class);



}


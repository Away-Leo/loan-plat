package com.pingxun.biz.log.domain.repository;


import com.pingxun.biz.log.domain.entity.SendSmsTime;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 短信发送次数
 * Created by dujy on 2017-05-20.
 */
public interface SendSmsTimeRepository extends JpaRepository<SendSmsTime,Long> {

    SendSmsTime findByPhoneAndAppName(String phone,String appName);
}

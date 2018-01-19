package com.pingxun.biz.order.domain.repository;

import com.pingxun.biz.order.domain.entity.ApplicationInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
* @Title: ApplicationInfoRepository.java
* @Description:  应用信息操作库
* @author Away
* @date 2017/12/13 17:54
* @copyright 重庆平讯数据
* @version V1.0
*/
public interface ApplicationInfoRepository extends JpaRepository<ApplicationInfo,Long> {

    /**
     * @Author: Away
     * @Description: 获取app配置信息（查找第一条）
     * @Return com.pingxun.biz.order.domain.entity.ApplicationInfo
     * @Date 2017/12/13 17:59
     * @Copyright 重庆平讯数据
     */
    public ApplicationInfo findFirst1ByOrderByRawAddTimeDesc();

}

package com.pingxun.biz.ibeesaas.domain.repository;

import com.pingxun.biz.ibeesaas.domain.entity.IdentityRecord;
import org.springframework.data.jpa.repository.JpaRepository;

/**
* @Title: IdentityRecordRepository.java
* @Description:  认证记录表操作库
* @author Away
* @date 2017/12/18 14:54
* @copyright 重庆平讯数据
* @version V1.0
*/
public interface IdentityRecordRepository extends JpaRepository<IdentityRecord,Long>{

    /**
     * @Author: Away
     * @Description： 按照用户查找认证记录
     * @Param: userId 用户ID
     * @Return com.pingxun.biz.identify.domain.entity.IdentityRecord
     * @Date 2017/12/18 14:57
     * @Copyright 重庆平讯数据
     */
    public IdentityRecord findByUserId(Long userId);
}

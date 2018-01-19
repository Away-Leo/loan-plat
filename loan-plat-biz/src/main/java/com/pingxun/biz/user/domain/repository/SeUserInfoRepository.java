package com.pingxun.biz.user.domain.repository;

import com.pingxun.biz.user.domain.entity.CwUserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 用户基本信息
 * Created by dujy on 2017-05-20.
 */
public interface SeUserInfoRepository extends JpaRepository<CwUserInfo,Long>,JpaSpecificationExecutor<CwUserInfo> {

    CwUserInfo findByUserId(Long userId);

    /**
     * @Author: Away
     * @Description: 按照是否认证和身份证查找
     * @Param: isAuthentication
     * @Param: idCard
     * @Return com.pingxun.biz.user.domain.entity.CwUserInfo
     * @Date 2018/1/8 11:28
     * @Copyright 重庆平讯数据
     */
    CwUserInfo findByIsAuthenticationAndIdCardAndUserType(boolean isAuthentication,String idCard,Integer userType);
}

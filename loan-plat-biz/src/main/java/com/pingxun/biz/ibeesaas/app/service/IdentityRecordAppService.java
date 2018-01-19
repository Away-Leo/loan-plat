package com.pingxun.biz.ibeesaas.app.service;

import com.pingxun.biz.ibeesaas.app.dto.IdentityParamDto;
import com.pingxun.biz.ibeesaas.app.dto.IdentityRecordDto;
import com.pingxun.biz.ibeesaas.domain.entity.IdentityRecord;
import com.pingxun.biz.ibeesaas.domain.service.IdentityRecordDomainService;
import com.pingxun.biz.user.app.dto.CwUserInfoDto;
import com.pingxun.core.common.util.ObjectHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @Title: IdentityRecordAppService.java
* @Description:  身份认证service
* @author Away
* @date 2017/12/18 15:07
* @copyright 重庆平讯数据
* @version V1.0
*/
@Service
public class IdentityRecordAppService {

    @Autowired
    private IdentityRecordDomainService identityRecordDomainService;

    /**
     * @Author: Away
     * @Description: 按照用户查找
     * @Param: userId
     * @Return com.pingxun.biz.identify.app.dto.IdentityRecordDto
     * @Date 2017/12/18 15:10
     * @Copyright 重庆平讯数据
     */
    public IdentityRecordDto findByUserId(Long userId){
        IdentityRecord sourceData=identityRecordDomainService.findByUserId(userId);
        if(ObjectHelper.isNotEmpty(sourceData)){
            return sourceData.to(IdentityRecordDto.class);
        }else{
            return null;
        }
    }

    /**
     * @Author: Away
     * @Description: 验证身份信息
     * @Param: identityRecordDto
     * @Return com.pingxun.biz.identify.app.dto.IdentityRecordDto
     * @Date 2017/12/18 15:50
     * @Copyright 重庆平讯数据
     */
    public CwUserInfoDto checkIdentity(IdentityParamDto identityParamDto) throws Exception{
        return this.identityRecordDomainService.checkIdentity(identityParamDto);
    }
}

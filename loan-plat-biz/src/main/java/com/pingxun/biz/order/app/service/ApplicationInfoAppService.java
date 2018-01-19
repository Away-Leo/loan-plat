package com.pingxun.biz.order.app.service;

import com.pingxun.biz.order.app.dto.ApplicationInfoDto;
import com.pingxun.biz.order.domain.entity.ApplicationInfo;
import com.pingxun.biz.order.domain.service.ApplicationInfoDomainService;
import com.pingxun.core.common.util.ObjectHelper;
import com.zds.common.lang.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @Title: ApplicationInfoAppService.java
* @Description: app配置信息业务服务
* @author Away
* @date 2017/12/13 18:04
* @copyright 重庆平讯数据
* @version V1.0
*/
@Service
public class ApplicationInfoAppService {

    @Autowired
    private ApplicationInfoDomainService applicationInfoDomainService;

    /**
     * @Author: Away
     * @Description: 查找APP配置
     * @Return com.pingxun.biz.order.domain.entity.ApplicationInfo
     * @Date 2017/12/14 9:43
     * @Copyright 重庆平讯数据
     */
    public ApplicationInfoDto getAppInfo(){
        ApplicationInfo returnData=applicationInfoDomainService.getApplicationInfo();
        if(ObjectHelper.isNotEmpty(returnData)){
            return returnData.to(ApplicationInfoDto.class);
        }else {
            throw new BusinessException("PX001","查找app配置信息出错！配置为空");
        }
    }
}

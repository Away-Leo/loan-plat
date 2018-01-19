package com.pingxun.biz.order.domain.service;

import com.pingxun.biz.order.domain.entity.ApplicationInfo;
import com.pingxun.biz.order.domain.repository.ApplicationInfoRepository;
import com.pingxun.core.common.util.ObjectHelper;
import com.zds.common.lang.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**   
* @Title: ApplicationInfoDomainService.java
* @Description:  app配置信息数据服务层
* @author Away
* @date 2017/12/13 18:00 
* @copyright 重庆平讯数据
* @version V1.0   
*/
@Service
public class ApplicationInfoDomainService {

    @Autowired
    private ApplicationInfoRepository applicationInfoRepository;

    /**
     * @Author: Away
     * @Description: 查找配置信息
     * @Return com.pingxun.biz.order.domain.entity.ApplicationInfo
     * @Date 2017/12/13 18:02
     * @Copyright 重庆平讯数据
     */
    public ApplicationInfo getApplicationInfo() throws BusinessException{
        ApplicationInfo returnData=applicationInfoRepository.findFirst1ByOrderByRawAddTimeDesc();
        if(ObjectHelper.isNotEmpty(returnData)){
            return returnData;
        }else{
            throw new BusinessException("PX002","未找到相应APP配置信息");
        }
    }
}

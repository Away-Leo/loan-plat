package com.pingxun.biz.user.app.service;

import com.pingxun.biz.user.app.dto.AppInfoDto;
import com.pingxun.biz.user.app.dto.AppModuleDto;
import com.pingxun.biz.user.domain.service.ModuleDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * 后台模块开关服务
 * Created by dujy on 2017-05-20.
 */
@Transactional
@Service
public class ModuleAppService {

    @Autowired
    private ModuleDomainService moduleDomainService;

    /**
     * 查询app查询显示模块
     * @return
     */
    public AppModuleDto getAppModule(String appName)
    {
        return moduleDomainService.getAppModule(appName);
    }

    /**
     * app短信配置
     * @param appName
     * @return
     */
    public AppInfoDto getAppSendMessage(String appName)
    {
        return moduleDomainService.getAppSendMessage(appName);
    }
}

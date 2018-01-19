package com.pingxun.biz.user.domain.service;


import com.pingxun.biz.user.app.dto.AppInfoDto;
import com.pingxun.biz.user.app.dto.AppModuleDto;
import com.pingxun.biz.user.domain.dao.ModuleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * banner查询服务
 * Created by dujy on 2017-05-20.
 */
@Service
public class ModuleDomainService {

    @Autowired
    private ModuleDao moduleDao;

    /**
     * 统计新增用户数
     * @return
     */
    public AppModuleDto getAppModule(String appName)
    {
        AppModuleDto appModule = moduleDao.getAppModule(appName);
        return appModule;
    }


    public AppInfoDto getAppSendMessage(String appName)
    {
        AppInfoDto appModule = moduleDao.getAppSendMessage(appName);
        return appModule;
    }

}

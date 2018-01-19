package com.pingxun.biz.user.app.service;


import com.pingxun.biz.user.app.dto.AppInfoDto;
import com.pingxun.biz.user.domain.entity.AppInfo;
import com.pingxun.biz.user.domain.service.AppInfoDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * 后台模块开关服务
 * Created by dujy on 2017-05-20.
 */
@Transactional
@Service
public class AppInfoAppService {

    @Autowired
    private AppInfoDomainService appInfoDomainService;

    /**
     * 查询app查询显示模块
     * @return
     */
    public List<AppInfoDto> getAppList() {

        List<AppInfoDto> appInfoDtoList = new ArrayList<AppInfoDto>();
        List<AppInfo> appInfoList = appInfoDomainService.getAppList();
        for(AppInfo appInfo:appInfoList){
            appInfoDtoList.add(appInfo.to(AppInfoDto.class));
        }
        return appInfoDtoList;
    }

    /**
     * 查询APP所属主体
     * @param code
     * @return
     */
    public AppInfoDto findByCode(String code) {
        return appInfoDomainService.findByCode(code);
    }

}

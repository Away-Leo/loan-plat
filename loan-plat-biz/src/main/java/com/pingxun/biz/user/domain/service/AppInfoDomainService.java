package com.pingxun.biz.user.domain.service;


import com.pingxun.biz.user.app.dto.AppInfoDto;
import com.pingxun.biz.user.domain.entity.AppInfo;
import com.pingxun.biz.user.domain.repository.AppInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * banner查询服务
 * Created by dujy on 2017-05-20.
 */
@Service
public class AppInfoDomainService {

    @Autowired
    private AppInfoRepository repository;

    /**
     * 统计新增用户数
     * @return
     */
    public List<AppInfo> getAppList()
    {
        List<AppInfo> appInfoList = repository.findAll();
        return appInfoList;
    }

    /**
     * 查询APP所属主体
     * @param code
     * @return
     */
    public AppInfoDto findByCode(String code){
        return repository.findByAndCode(code).to(AppInfoDto.class);
    }

}

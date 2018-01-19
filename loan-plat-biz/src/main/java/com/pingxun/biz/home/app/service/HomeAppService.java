package com.pingxun.biz.home.app.service;

import com.pingxun.biz.home.app.dto.HomeDto;
import com.pingxun.biz.home.domain.service.HomeDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * 首页统计数据
 */
@Transactional
@Service
public class HomeAppService {

    @Autowired
    private HomeDomainService homeDomainService;

    /**
     * 发展数据监控
     * @param homeDto
     * @return
     */
    public HomeDto dayDevelopmentData(HomeDto homeDto){

        return homeDomainService.dayDevelopmentData(homeDto);
    }

}

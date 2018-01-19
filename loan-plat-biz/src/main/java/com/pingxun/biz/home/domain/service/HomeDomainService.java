package com.pingxun.biz.home.domain.service;

import com.pingxun.biz.home.app.dto.HomeDto;
import com.pingxun.biz.home.domain.dao.HomeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HomeDomainService {

    @Autowired
    private HomeDao homeDao;

    /**
     * 发展数据监控
     * @param homeDto
     * @return
     */
    public HomeDto dayDevelopmentData(HomeDto homeDto){

        return homeDao.dayDevelopmentData(homeDto);
    }
}

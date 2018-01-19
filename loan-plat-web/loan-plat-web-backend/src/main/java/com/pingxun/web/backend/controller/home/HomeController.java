package com.pingxun.web.backend.controller.home;

import com.pingxun.biz.home.app.dto.HomeDto;
import com.pingxun.biz.home.app.service.HomeAppService;
import com.pingxun.web.backend.controller.AbstractBackendController;
import com.pingxun.web.common.dto.CPViewResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 后台首页
 * Created by Administrator on 2017/6/5.
 */
@RestController
public class HomeController extends AbstractBackendController {

    @Autowired
    private HomeAppService homeAppService;

    /**
     * 查询日发展监控数据
     * @param homeDto
     * @return
     */
    @GetMapping("/home/dayDevelopmentData.json")
    @ResponseBody
    public CPViewResultInfo dayDevelopmentData(HomeDto homeDto){
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        HomeDto homeDto1 = homeAppService.dayDevelopmentData(homeDto);
        cpViewResultInfo.setData(homeDto1);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("成功");
        return cpViewResultInfo;
    }


}

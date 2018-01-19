package com.pingxun.web.backend.controller.user;

import com.pingxun.biz.user.app.dto.CwUserInfoDto;
import com.pingxun.biz.user.app.service.CwUserInfoAppService;
import com.pingxun.web.backend.controller.AbstractBackendController;
import com.pingxun.web.common.dto.CPViewResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 客户管理
 * @author dujiangyuu
 */
@RestController
public class PxUserController extends AbstractBackendController{

    @Autowired
    private CwUserInfoAppService cwUserInfoAppService;

    /**
     * 客户列表
     * @param cwUserInfoDto
     * @return
     */
    @PostMapping("/customer/findByCondition.json")
    public CPViewResultInfo findByCondition(@RequestBody CwUserInfoDto cwUserInfoDto){
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        Page<CwUserInfoDto> cwUserInfoDtos = cwUserInfoAppService.findByCondition(cwUserInfoDto);
        cpViewResultInfo.setData(cwUserInfoDtos);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("成功");

        return cpViewResultInfo;
    }

}

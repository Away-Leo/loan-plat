package com.pingxun.web.front.controller.identity;

import com.pingxun.biz.CPContext;
import com.pingxun.biz.ibeesaas.app.dto.IdentityParamDto;
import com.pingxun.biz.ibeesaas.app.service.IdentityRecordAppService;
import com.pingxun.biz.user.app.dto.CwUserInfoDto;
import com.pingxun.web.common.dto.CPViewResultInfo;
import com.pingxun.web.front.controller.AbstractFrontController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
* @Title: IdentityController.java
* @Description:  身份认证
* @author Away
* @date 2017/12/22 15:54
* @copyright 重庆平讯数据
* @version V1.0
*/
@RestController
public class IdentityController extends AbstractFrontController{

    @Autowired
    private IdentityRecordAppService identityRecordAppService;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @PostMapping("/identity/startIdentity.json")
    @ResponseBody
    public CPViewResultInfo startIdentity(@RequestBody IdentityParamDto identityParamDto){
        CPViewResultInfo info=new CPViewResultInfo();
        try{
            identityParamDto.setUserId(CPContext.getContext().getSeUserInfo().getId());
            CwUserInfoDto result=identityRecordAppService.checkIdentity(identityParamDto);
            info.setSuccess(true);
            info.setData(result);
        }catch (Exception e){
            info.setSuccess(false);
            info.setMessage(e.getMessage());
            logger.error("PX004","身份识别出错",e);
            e.printStackTrace();
        }
        return info;
    }

}

package com.pingxun.web.front.controller.call;

import com.pingxun.biz.call.app.dto.CallRecordDto;
import com.pingxun.biz.call.app.service.CallRecordAppService;
import com.pingxun.web.common.dto.CPViewResultInfo;
import com.pingxun.web.front.controller.AbstractFrontController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
public class CallController extends AbstractFrontController{

    @Autowired
    private CallRecordAppService callRecordAppService;

    @PostMapping("/callRecord/createRecord")
    public CPViewResultInfo saveRecord(@RequestBody CallRecordDto callRecordDto){
        CPViewResultInfo info=new CPViewResultInfo();
        try {
            CallRecordDto savedData=this.callRecordAppService.saveRecord(callRecordDto);
            info.setData(savedData);
            info.setSuccess(true);
        }catch (Exception e){
            info.setSuccess(false);
            info.setMessage(e.getMessage());
        }
        return info;
    }


}

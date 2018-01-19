package com.pingxun.biz.call.app.service;


import com.pingxun.biz.call.app.dto.CallRecordDto;
import com.pingxun.biz.call.domain.entity.CallRecord;
import com.pingxun.biz.call.domain.service.CallRecordDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

/**
* @Title: CallRecordAppService.java
* @Description:  拨打记录服务
* @author Away
* @date 2018/1/9 18:32
* @copyright 重庆平讯数据
* @version V1.0
*/
@Service
@Transactional
public class CallRecordAppService {

    @Autowired
    private CallRecordDomainService callRecordDomainService;


    public CallRecordDto saveRecord(CallRecordDto callRecordDto){
        CallRecord callRecord=new CallRecord();
        callRecord.from(callRecordDto);
        callRecord.setCalledTime(new Date());
        return this.callRecordDomainService.saveData(callRecord).to(CallRecordDto.class);
    }

}

package com.pingxun.biz.call.domain.service;


import com.pingxun.biz.call.domain.entity.CallRecord;
import com.pingxun.biz.call.domain.repository.CallRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @Title: CallRecordDomainService.java
* @Description:  服务
* @author Away
* @date 2018/1/9 18:29
* @copyright 重庆平讯数据
* @version V1.0
*/
@Service
public class CallRecordDomainService {

    @Autowired
    private CallRecordRepository callRecordRepository;

    /**
     * @Author: Away
     * @Description: 保存
     * @Param: callRecord
     * @Return com.pingxun.biz.call.domain.entity.CallRecord
     * @Date 2018/1/9 18:31
     * @Copyright 重庆平讯数据
     */
    public CallRecord saveData(CallRecord callRecord){
        return this.callRecordRepository.save(callRecord);
    }


}

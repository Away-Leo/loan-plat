package com.pingxun.biz.log.app.service;

import com.pingxun.biz.log.app.dto.SendSmsLogDto;
import com.pingxun.biz.log.domain.entity.SendSmsLog;
import com.pingxun.biz.log.domain.service.SendSmsLogDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * 短信发送日志
 * Created by dujy on 2017-05-20.
 */
@Transactional
@Service
public class SendSmsLogAppService {

    @Autowired
    private SendSmsLogDomainService logDomainService;

    /**
     * 新增数据日志
     * @param logDto
     * @return
     */
    public Long create(SendSmsLogDto logDto)
    {
        return logDomainService.create(logDto).getId();
    }

    /**
     * 修改系统短信发送标识
     * @param logDto
     * @return
     */
    public SendSmsLogDto update(SendSmsLogDto logDto)
    {
        return logDomainService.update(logDto).to(SendSmsLogDto.class);
    }

    /**
     * 查询短信发送信息
     * @param phone
     * @return
     */
    public SendSmsLogDto findById(String phone){

        SendSmsLog sendSmsLog = logDomainService.findById(phone);
        if(sendSmsLog!=null)
        {
            return sendSmsLog.to(SendSmsLogDto.class);
        }
        return null;
    }

}

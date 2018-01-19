package com.pingxun.biz.log.domain.service;

import com.pingxun.biz.log.app.dto.SendSmsLogDto;
import com.pingxun.biz.log.domain.entity.SendSmsLog;
import com.pingxun.biz.log.domain.repository.SendSmsLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 产品日志
 * Created by dujy on 2017-05-20.
 */
@Service
public class SendSmsLogDomainService {

    @Autowired
    private SendSmsLogRepository repository;

    /**
     * 记录短信发送次数
     * @param sendSmsLogDto
     * @return
     */
    public SendSmsLog create(SendSmsLogDto sendSmsLogDto){
        SendSmsLog log = new SendSmsLog();
        log.from(sendSmsLogDto);
        return repository.save(log);
    }

    /**
     * 修改短信信息
     * @param sendSmsLogDto
     * @return
     */
    public SendSmsLog update(SendSmsLogDto sendSmsLogDto){
        SendSmsLog log = repository.findOne(sendSmsLogDto.getId());
        if(log!=null)
        {
            log.from(sendSmsLogDto);
        }
        return log;
    }

    /**
     * 查询日志发送信息
     * @param phone
     * @return
     */
    public SendSmsLog findById(String phone){
        return repository.findByPhone(phone);
    }

}

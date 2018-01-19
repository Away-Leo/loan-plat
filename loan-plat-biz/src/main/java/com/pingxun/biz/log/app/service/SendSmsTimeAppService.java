package com.pingxun.biz.log.app.service;

import com.pingxun.biz.log.app.dto.SendSmsTimeDto;
import com.pingxun.biz.log.domain.entity.SendSmsTime;
import com.pingxun.biz.log.domain.service.SendSmsTimeDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;

/**
 * 短信发送次数
 * Created by dujy on 2017-05-20.
 */
@Transactional
@Service
public class SendSmsTimeAppService {

    @Autowired
    private SendSmsTimeDomainService sendSmsTimeDomainService;

    /**
     * 修改当天发送次数
     * @param sendSmsTimeDto
     * @return
     */
    public void update(SendSmsTimeDto sendSmsTimeDto)
    {
        sendSmsTimeDomainService.update(sendSmsTimeDto);
    }

    /**
     * 查询手机号码发送次数
     * @param phone
     * @return
     */
    public SendSmsTimeDto findByPhone(String phone,String appName)
    {
        SendSmsTimeDto sendSmsTimeDto = null;
        SendSmsTime sendSmsTime = sendSmsTimeDomainService.findByPhone(phone,appName);
        if(!Objects.isNull(sendSmsTime))
        {
            sendSmsTimeDto = sendSmsTime.to(SendSmsTimeDto.class);
        }
        return sendSmsTimeDto;
    }

}

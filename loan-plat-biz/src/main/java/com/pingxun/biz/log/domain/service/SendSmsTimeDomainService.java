package com.pingxun.biz.log.domain.service;

import com.pingxun.biz.log.app.dto.SendSmsTimeDto;
import com.pingxun.biz.log.domain.entity.SendSmsTime;
import com.pingxun.biz.log.domain.repository.SendSmsTimeRepository;
import com.pingxun.core.common.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;

/**
 * 短信发送次数
 * Created by dujy on 2017-05-20.
 */
@Service
public class SendSmsTimeDomainService {

    @Autowired
    private SendSmsTimeRepository repository;

    /**
     * 新增产品
     * @param sendSmsTimeDto
     * @return
     */
    private SendSmsTime create(SendSmsTimeDto sendSmsTimeDto){
        SendSmsTime sendSmsTime = new SendSmsTime();
        sendSmsTime.from(sendSmsTimeDto);
        return repository.save(sendSmsTime);
    }

    /**
     * 修改短信发送次数
     * @param sendSmsTimeDto
     * @return
     */
    public void update(SendSmsTimeDto sendSmsTimeDto){
        SendSmsTime sendSmsTime =  repository.findByPhoneAndAppName(sendSmsTimeDto.getPhone(),sendSmsTimeDto.getAppName());
        if(sendSmsTime == null)
        {
            sendSmsTimeDto.setSendTime(1);
            sendSmsTimeDto.setSendDate(new Date());
            create(sendSmsTimeDto);
        }else{
            String sendDate = Utils.convertDate(sendSmsTime.getSendDate());
            String now = Utils.convertDate(new Date());
            if(sendDate.equals(now)) {
                sendSmsTime.setSendTime(sendSmsTime.getSendTime() + 1);
            }else{
                sendSmsTime.setSendDate(new Date());
                sendSmsTime.setSendTime(1);
            }
        }
    }

    /**
     * 查询短信发送次数
     * @param phone
     * @return
     */
    public SendSmsTime findByPhone(String phone,String appName){
        SendSmsTime sendSmsTime = repository.findByPhoneAndAppName(phone,appName);
        if(!Objects.isNull(sendSmsTime)) {
            String sendDate = Utils.convertDate(sendSmsTime.getSendDate());
            String now = Utils.convertDate(new Date());
            //初始化短信发送次数
            if (!sendDate.equals(now)) {
                sendSmsTime.setSendDate(new Date());
                sendSmsTime.setSendTime(Integer.valueOf(0));
            }
        }
        return sendSmsTime;
    }

}

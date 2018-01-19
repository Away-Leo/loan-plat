package com.pingxun.biz.message.app.service;

import com.pingxun.biz.common.dto.Pages;
import com.pingxun.biz.message.app.dto.MessageDto;
import com.pingxun.biz.message.domain.service.MessageDomainService;
import com.pingxun.biz.parameter.app.ParameterEnum;
import com.pingxun.biz.parameter.domain.entity.Parameter;
import com.pingxun.biz.parameter.domain.service.ParameterDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * 消息列表服务
 * Created by dujy on 2017-05-20.
 */
@Transactional
@Service
public class MessageAppService {

    @Autowired
    private MessageDomainService messageDomainService;

    /**
     * 新增消息
     * @param messageDto
     * @return
     */
    public Long create(MessageDto messageDto)
    {
        return messageDomainService.create(messageDto).getId();
    }
    /**
     * 已读消息接口
     * @param messageDto
     * @return
     */
    public Long readMessage(MessageDto messageDto)
    {
        return messageDomainService.readMessage(messageDto).getId();
    }
    /**
     * 查询消息详情
     * @param id
     * @return
     */
    public MessageDto findById(Long id)
    {
        return messageDomainService.findById(id).to(MessageDto.class);
    }

    /**
     * 查询消息列表
     * @return
     */
    public Page<MessageDto> findByCondition(MessageDto dto)
    {
        return Pages.map(messageDomainService.findByCondition(dto),MessageDto.class);
    }

    /**
     * 删除消息
     * @param messageDto
     * @return
     */
    public int delete(MessageDto messageDto){
        return messageDomainService.delete(messageDto);
    }

    /**
     * 新增消息
     * @param messageDto
     * @return
     */
    public int findNewMessage(MessageDto messageDto)
    {
        return messageDomainService.findNewMessage(messageDto);
    }
}

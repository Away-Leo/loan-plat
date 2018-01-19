package com.pingxun.web.front.controller.message;

import com.pingxun.biz.CPContext;
import com.pingxun.biz.message.app.dto.MessageDto;
import com.pingxun.biz.message.app.service.MessageAppService;
import com.pingxun.web.common.dto.CPViewResultInfo;
import com.pingxun.web.front.controller.AbstractFrontController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 消息服务
 * Created by dujy on 2017-05-20.
 */
@RestController
public class MessageController extends AbstractFrontController {

    @Autowired
    private MessageAppService messageAppService;

    /**
     * 查询消息详情
     * @param id
     * @return
     */
    @GetMapping("/message/findById.json")
    @ResponseBody
    public CPViewResultInfo findById(Long id)
    {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        MessageDto messageDto = messageAppService.findById(id);
        cpViewResultInfo.setData(messageDto);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("成功");
        return cpViewResultInfo;
    }

    /**
     * 查询我的消息
     * @param messageDto
     * @return
     */
    @GetMapping("/message/list.json")
    @ResponseBody
    public CPViewResultInfo findByCondition(MessageDto messageDto){
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        Page<MessageDto> messageDtos = messageAppService.findByCondition(messageDto);
        cpViewResultInfo.setData(messageDtos);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("成功");
        return cpViewResultInfo;
    }

    /**
     * 已读消息接口
     * @param messageDto
     * @return
     */
    @GetMapping("/message/readMessage.json")
    @ResponseBody
    public CPViewResultInfo readMessage(MessageDto messageDto){
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        Long result= messageAppService.readMessage(messageDto);
        cpViewResultInfo.setData(result);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("成功");
        return cpViewResultInfo;
    }

    /**
     * 清空接口
     * @param messageDto
     * @return
     */
    @GetMapping("/message/delete.json")
    @ResponseBody
    public CPViewResultInfo delete(MessageDto messageDto){
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        messageDto.setUserId(CPContext.getContext().getSeUserInfo().getId());
        int result= messageAppService.delete(messageDto);
        cpViewResultInfo.setData(result);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("成功");
        return cpViewResultInfo;
    }

    /**
     * 新消息接口
     * @return
     */
    @GetMapping("/message/findNewMessage.json")
    @ResponseBody
    public CPViewResultInfo findNewMessage(){
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        MessageDto messageDto = new MessageDto();
        messageDto.setPageNo(1);
        messageDto.setUserId(CPContext.getContext().getSeUserInfo().getId());
        int newMessageCount = messageAppService.findNewMessage(messageDto);
        cpViewResultInfo.setData(newMessageCount);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("成功");
        return cpViewResultInfo;
    }

}

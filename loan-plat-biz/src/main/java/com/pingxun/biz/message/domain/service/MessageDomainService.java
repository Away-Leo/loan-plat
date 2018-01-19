package com.pingxun.biz.message.domain.service;

import com.pingxun.biz.CPContext;
import com.pingxun.biz.CwException;
import com.pingxun.biz.message.app.dto.MessageDto;
import com.pingxun.biz.message.domain.entity.Message;
import com.pingxun.biz.message.domain.repository.MessageRepository;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;

/**
 * 产品服务
 * Created by dujy on 2017-05-20.
 */
@Service
public class MessageDomainService {

    @Autowired
    private MessageRepository repository;
    /**
     * 新增消息
     * @param messageDto
     * @return
     */
    public Message create(MessageDto messageDto)
    {
        Message message = new Message();
        message.from(messageDto);
        message.setSendDate(new Date());
        return repository.save(message);
    }
    /**
     * 消息已读
     * @param messageDto
     * @return
     */
    public Message readMessage(MessageDto messageDto)
    {
        Message message = repository.findOne(messageDto.getId());
        if(message == null)
        {
            CwException.throwIt("消息不存在");
        }
        message.setIsRead(Boolean.TRUE);
        return message;
    }

    /**
     * 删除消息
     * @param messageDto
     * @return
     */
    public int delete(MessageDto messageDto){
        return repository.deleteByUserId(messageDto.getUserId());
    }

    /**
     * 是否有新的消息
     * @param messageDto
     * @return
     */
    public int findNewMessage(MessageDto messageDto){
        return repository.findMessageByUserId(messageDto.getUserId());
    }
    /**
     * 查询单个参数
     * @param id
     * @return
     */
    public Message findById(Long id)
    {
        MessageDto messageDto = new MessageDto();
        messageDto.setId(id);
        readMessage(messageDto);
        return repository.findOne(id);
    }

    /**
     * 查询消息列表
     * @param messageDto
     * @return
     */
    public Page<Message> findByCondition(MessageDto messageDto)
    {
        String[] fields = {"sendDate"};
        messageDto.setSortFields(fields);
        messageDto.setSortDirection(Sort.Direction.DESC);
        Specification<Message> supplierSpecification = (Root<Message> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> predicates = Lists.newArrayListWithCapacity(20);
            predicates.add(cb.equal(root.get("userId"),CPContext.getContext().getSeUserInfo().getId()));
            predicates.add(cb.equal(root.get("appName"),messageDto.getAppName()));
            query.where(cb.and(predicates.toArray(new Predicate[0])));
            return query.getRestriction();
        };
        return repository.findAll(supplierSpecification, messageDto.toPage());
    }

}

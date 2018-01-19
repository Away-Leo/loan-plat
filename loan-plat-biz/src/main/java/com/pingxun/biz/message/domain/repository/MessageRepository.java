package com.pingxun.biz.message.domain.repository;

import com.pingxun.biz.message.domain.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 * 系统消息
 * Created by dujy on 2017-05-20.
 */
public interface MessageRepository extends JpaRepository<Message,Long>,JpaSpecificationExecutor<Message> {

    int deleteByUserId(Long userId);

    @Query(value = "select count(1) from loan_message where is_read=0 and user_id= ?1 ",nativeQuery = true)
    int findMessageByUserId(Long userId);
}

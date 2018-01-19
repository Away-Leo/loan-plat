package com.pingxun.biz.log.domain.repository;


import com.pingxun.biz.log.domain.entity.SystemLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 日志
 * Created by dujy on 2017-05-20.
 */
public interface SystemLogRepository extends JpaRepository<SystemLog,Long> {

}

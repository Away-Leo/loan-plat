package com.pingxun.biz.log.domain.service;

import com.pingxun.biz.log.app.dto.SystemLogDto;
import com.pingxun.biz.log.domain.entity.SystemLog;
import com.pingxun.biz.log.domain.repository.SystemLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 系统操作日志
 * Created by dujy on 2017-05-20.
 */
@Service
public class SystemLogDomainService {

    @Autowired
    private SystemLogRepository repository;

    /**
     * 记录操作日志
     * @param systemLogDto
     * @return
     */
    public SystemLog create(SystemLogDto systemLogDto){
        SystemLog systemLog = new SystemLog();
        systemLog.from(systemLogDto);
        return repository.save(systemLog);
    }


}

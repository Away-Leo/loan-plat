package com.pingxun.biz.log.app.service;

import com.pingxun.biz.log.app.dto.SystemLogDto;
import com.pingxun.biz.log.domain.service.SystemLogDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * 数据埋点日志
 * Created by dujy on 2017-05-20.
 */
@Transactional
@Service
public class SystemLogAppService {

    @Autowired
    private SystemLogDomainService logDomainService;

    /**
     * 新增操作日志
     * @param logDto
     * @return
     */
    public Long create(SystemLogDto logDto)
    {
        return logDomainService.create(logDto).getId();
    }


}

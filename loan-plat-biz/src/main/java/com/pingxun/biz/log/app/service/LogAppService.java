package com.pingxun.biz.log.app.service;

import com.pingxun.biz.log.app.dto.ApplyLogDto;
import com.pingxun.biz.log.app.dto.LogDto;
import com.pingxun.biz.log.domain.service.LogDomainService;
import com.pingxun.biz.log.domain.entity.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据埋点日志
 * Created by dujy on 2017-05-20.
 */
@Transactional
@Service
public class LogAppService {

    @Autowired
    private LogDomainService logDomainService;

    /**
     * 新增数据日志
     * @param logDto
     * @return
     */
    public Long create(LogDto logDto){
        return logDomainService.create(logDto).getId();
    }
    /**
     * 查询所有日志
     * @return
     */
    public List<LogDto> findAll(){
        List<LogDto> logDtos = new ArrayList<LogDto>();
        List<Log> logList = logDomainService.findAll();
        for(Log log :logList){
            logDtos.add(log.to(LogDto.class));
        }
        return logDtos;
    }

    /**
     * 查询产品申请用户id
     * @return
     */
    public List<ApplyLogDto> findUserByProductId(){

        return logDomainService.findNumByProductId();
    }

}

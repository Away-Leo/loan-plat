package com.pingxun.biz.log.domain.service;

import com.pingxun.biz.log.app.dto.ApplyLogDto;
import com.pingxun.biz.log.app.dto.LogDto;
import com.pingxun.biz.log.domain.dao.LogDao;
import com.pingxun.biz.log.domain.repository.LogRepository;
import com.pingxun.biz.log.domain.entity.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 埋点数据日志
 * Created by dujy on 2017-05-20.
 */
@Service
public class LogDomainService {

    @Autowired
    private LogRepository repository;

    @Autowired
    private LogDao logDao;

    /**
     * 新增埋点数据日志
     * @param logDto
     * @return
     */
    public Log create(LogDto logDto){
        Log log = new Log();
        log.from(logDto);
        if(logDto.getChannelNo()!=null) {
            if (logDto.getChannelNo().toUpperCase().contains("IOS")){
                log.setChannelNo("IOS");
            }
        }
        log.setApplyDate(new Date());

        return repository.save(log);
    }

    /**
     * 查询所有日志信息
     * @return
     */
    public List<Log> findAll(){
        return repository.findAll();
    }

    /**
     * 根据产品id查询申请用户数
     * @return
     */
    public List<ApplyLogDto> findNumByProductId(){

        return logDao.findApplyNumById();
    }

}

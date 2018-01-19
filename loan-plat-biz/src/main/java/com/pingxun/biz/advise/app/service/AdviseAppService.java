package com.pingxun.biz.advise.app.service;

import com.pingxun.biz.advise.app.dto.AdviseDto;
import com.pingxun.biz.advise.domain.service.AdviseDomainService;
import com.pingxun.biz.common.dto.Pages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * 意见收集服务
 * Created by dujy on 2017-05-20.
 */
@Transactional
@Service
public class AdviseAppService {

    @Autowired
    private AdviseDomainService adviseDomainService;

    /**
     * 新增消息
     * @param adviseDto
     * @return
     */
    public Long create(AdviseDto adviseDto){
        return adviseDomainService.create(adviseDto).getId();
    }

    /**
     * 查询意见列表
     * @return
     */
    public Page<AdviseDto> findByCondition(AdviseDto dto){
        return Pages.map(adviseDomainService.findByCondition(dto),AdviseDto.class);
    }

}

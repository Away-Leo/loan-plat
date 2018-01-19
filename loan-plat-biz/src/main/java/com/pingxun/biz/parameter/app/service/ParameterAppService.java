package com.pingxun.biz.parameter.app.service;

import com.pingxun.biz.parameter.app.ParameterEnum;
import com.pingxun.biz.parameter.app.dto.IndexParameterDto;
import com.pingxun.biz.parameter.app.dto.ParameterDto;
import com.pingxun.biz.parameter.domain.entity.Parameter;
import com.pingxun.biz.parameter.domain.service.ParameterDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * 系统参数服务
 * Created by dujy on 2017-05-20.
 */
@Transactional
@Service
public class ParameterAppService {

    @Autowired
    private ParameterDomainService parameterDomainService;

    /**
     * 修改参数
     * @param parameterDto
     * @return
     */
    public Long update(IndexParameterDto parameterDto)
    {
        return parameterDomainService.update(parameterDto).getId();
    }

    /**
     * 根据编码查询配置
     * @param code
     * @return
     */
    public ParameterDto findByCode(String code){
        return parameterDomainService.findByCode(code).to(ParameterDto.class);
    }

    /**
     * 查询全部参数
     * @return
     */
    public IndexParameterDto findAll(){
        IndexParameterDto indexParameterDto = new IndexParameterDto();
        List<Parameter> productList = parameterDomainService.findAll();
        for(Parameter parameter : productList){
            if(ParameterEnum.PAYMODE_MEMO.getKey().equals(parameter.getParameterCode())) {
                indexParameterDto.setPayModeMemo(parameter.getParameterValue());
            }
            //申请限制
            if(ParameterEnum.APPLY_LIMIT.getKey().equals(parameter.getParameterCode())) {
                indexParameterDto.setApplyLimit(Integer.parseInt(parameter.getParameterValue()));
            }
            //申请限制
            if(ParameterEnum.AUTH_FLOW.getKey().equals(parameter.getParameterCode())) {
                indexParameterDto.setAuthFlow(Integer.parseInt(parameter.getParameterValue()));
            }

        }
        return indexParameterDto;
    }

}

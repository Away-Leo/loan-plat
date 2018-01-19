package com.pingxun.biz.parameter.domain.service;

import com.pingxun.biz.parameter.app.ParameterEnum;
import com.pingxun.biz.parameter.app.dto.IndexParameterDto;
import com.pingxun.biz.parameter.app.dto.ParameterDto;
import com.pingxun.biz.parameter.domain.entity.Parameter;
import com.pingxun.biz.parameter.domain.repository.ParameterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * 产品服务
 * Created by dujy on 2017-05-20.
 */
@Service
public class ParameterDomainService {

    @Autowired
    private ParameterRepository repository;
    /**
     * 新增参数
     * @param parameterDto
     * @return
     */
    public Parameter create(ParameterDto parameterDto){
        Parameter parameter = new Parameter();
        parameter.from(parameterDto);
        return repository.save(parameter);
    }

    /**
     * 修改参数
     * @param indexParameterDto
     * @return
     */
    public Parameter update(IndexParameterDto indexParameterDto){
        //借款起始金额
        Parameter parameter = repository.findByParameterCode(ParameterEnum.PAYMODE_MEMO.getKey());
        if(parameter == null){
            ParameterDto parameterDto = new ParameterDto();
            parameterDto.setParameterCode(ParameterEnum.PAYMODE_MEMO.getKey());
            parameterDto.setParameterName(ParameterEnum.PAYMODE_MEMO.getValue());
            parameterDto.setParameterName(ParameterEnum.PAYMODE_MEMO.getValue());
            parameterDto.setParameterValue(indexParameterDto.getPayModeMemo());
            create(parameterDto);
        }else{
            parameter.setParameterValue(indexParameterDto.getPayModeMemo());
        }

        return parameter;
    }

    /**
     * 查询单个参数
     * @param id
     * @return
     */
    public Parameter findById(Long id) {
        return repository.findOne(id);
    }

    /**
     * 根据参数编码查询审核配置
     * @param code
     * @return
     */
    public Parameter findByCode(String code){
        return repository.findByParameterCode(code);
    }

    /**
     * 查询所有参数
     * @return
     */
    public List<Parameter> findAll() {
        return repository.findAll();
    }

}

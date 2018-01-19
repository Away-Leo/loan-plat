package com.pingxun.biz.parameter.domain.service;

import com.pingxun.biz.parameter.app.dto.ParameterDto;
import com.pingxun.biz.parameter.domain.entity.SpinnerParameter;
import com.pingxun.biz.parameter.domain.repository.SpinnerParameterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 产品服务
 * Created by dujy on 2017-05-20.
 */
@Service
public class SpinnerParameterDomainService {

    @Autowired
    private SpinnerParameterRepository repository;
    /**
     * 新增参数
     * @param parameterDto
     * @return
     */
    public SpinnerParameter create(ParameterDto parameterDto){
        SpinnerParameter parameter = new SpinnerParameter();
        parameter.from(parameterDto);
        return repository.save(parameter);
    }

    /**
     * 根据参数类型查询参数值
     * @param type
     * @return
     */
    public List<SpinnerParameter> findByType(String type){
        return repository.findByTypeAndIsValidOrderByShowOrderAsc(type,true);
    }

    /**
     * 查询职业信息
     * @param code
     * @return
     */
    public SpinnerParameter findByCode(String code) {
        return repository.findByCode(code);
    }
}

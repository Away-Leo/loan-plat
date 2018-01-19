package com.pingxun.biz.parameter.domain.repository;

import com.pingxun.biz.parameter.domain.entity.SpinnerParameter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 系统参数表
 * Created by dujy on 2017-05-20.
 */
public interface SpinnerParameterRepository extends JpaRepository<SpinnerParameter,Long>{
    List<SpinnerParameter> findByTypeAndIsValidOrderByShowOrderAsc(String type, Boolean isValid);

    SpinnerParameter findByCode(String code);
}

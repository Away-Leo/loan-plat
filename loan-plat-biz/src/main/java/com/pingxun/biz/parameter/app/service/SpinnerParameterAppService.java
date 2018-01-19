package com.pingxun.biz.parameter.app.service;

import com.pingxun.biz.common.entity.BaseEntity;
import com.pingxun.biz.parameter.app.dto.ScreenConditionDto;
import com.pingxun.biz.parameter.app.dto.SpinnerParameterDto;
import com.pingxun.biz.parameter.domain.entity.SpinnerParameter;
import com.pingxun.biz.parameter.domain.service.SpinnerParameterDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 查询列表参数
 * Created by dujy on 2017-05-20.
 */
@Transactional
@Service
public class SpinnerParameterAppService {

    @Autowired
    private SpinnerParameterDomainService domainService;

    /**
     * 查询列表参数
     * @return
     */
    public List<SpinnerParameterDto> findByType(String type){
        List<SpinnerParameter> spinnerParameters =domainService.findByType(type);
        return  BaseEntity.map(spinnerParameters,SpinnerParameterDto.class);
    }
    /**
     * 查询刷选条件
     * @return
     */
    public List<ScreenConditionDto> findByCondition(Integer userType){
        List<ScreenConditionDto> list = new ArrayList();
        ScreenConditionDto screenConditionDto = new ScreenConditionDto();

        if(userType==1) {
            //期限
            String type="period";
            List<SpinnerParameter> spinnerParameters =domainService.findByType(type);
            List<SpinnerParameterDto> screenConditionDtoList = new ArrayList();
            for(SpinnerParameter spinnerParameter:spinnerParameters){
                screenConditionDtoList.add(spinnerParameter.to(SpinnerParameterDto.class));
            }
            screenConditionDto.setTitle("期限");
            screenConditionDto.setCode("loanPeriod");
            screenConditionDto.setData(screenConditionDtoList);
            list.add(screenConditionDto);
            //利率
            type = "monthRate";
            spinnerParameters = domainService.findByType(type);
            screenConditionDtoList = new ArrayList();
            for (SpinnerParameter spinnerParameter : spinnerParameters) {
                screenConditionDtoList.add(spinnerParameter.to(SpinnerParameterDto.class));
            }
            screenConditionDto = new ScreenConditionDto();
            screenConditionDto.setTitle("利率");
            screenConditionDto.setCode("monthRate");
            screenConditionDto.setData(screenConditionDtoList);
            list.add(screenConditionDto);

        }else{
            //社会身份
            String type="socialIdentity";
            List<SpinnerParameter> spinnerParameters =domainService.findByType(type);
            List<SpinnerParameterDto> screenConditionDtoList = new ArrayList();
            for(SpinnerParameter spinnerParameter:spinnerParameters){
                screenConditionDtoList.add(spinnerParameter.to(SpinnerParameterDto.class));
            }
            screenConditionDto.setTitle("社会身份");
            screenConditionDto.setCode("socialIdentity");
            screenConditionDto.setData(screenConditionDtoList);
            list.add(screenConditionDto);

            //借款用途
            type="loanPurpose";
            spinnerParameters =domainService.findByType(type);
            screenConditionDtoList = new ArrayList();
            for(SpinnerParameter spinnerParameter:spinnerParameters){
                screenConditionDtoList.add(spinnerParameter.to(SpinnerParameterDto.class));
            }
            screenConditionDto = new ScreenConditionDto();
            screenConditionDto.setTitle("借款用途");
            screenConditionDto.setCode("loanPurpose");
            screenConditionDto.setData(screenConditionDtoList);
            list.add(screenConditionDto);

            //贷款要求
            type="loanRequire";
            spinnerParameters =domainService.findByType(type);
            screenConditionDtoList = new ArrayList();
            for(SpinnerParameter spinnerParameter:spinnerParameters){
                screenConditionDtoList.add(spinnerParameter.to(SpinnerParameterDto.class));
            }
            screenConditionDto = new ScreenConditionDto();
            screenConditionDto.setTitle("借款要求");
            screenConditionDto.setCode("loanRequire");
            screenConditionDto.setData(screenConditionDtoList);
            list.add(screenConditionDto);
        }

        return list;
    }

    /**
     * 查询描述
     * @return
     */
    public SpinnerParameterDto findByCode(String code)
    {
        SpinnerParameterDto spinnerParameterDto = null;
        SpinnerParameter spinnerParameter = domainService.findByCode(code);
        if (!Objects.isNull(spinnerParameter)) {
            spinnerParameterDto = spinnerParameter.to(SpinnerParameterDto.class);
        }
        return spinnerParameterDto;
    }

}

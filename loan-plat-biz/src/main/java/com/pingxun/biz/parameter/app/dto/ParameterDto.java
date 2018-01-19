package com.pingxun.biz.parameter.app.dto;

import com.pingxun.biz.common.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;


/**
 * 首页查询参数配置
 * Created by Administrator on 2017/6/1.
 */
@Setter
@Getter
public class ParameterDto extends BaseDto{

    private Long id;
    private String parameterCode;
    private String parameterName;
    private String parameterValue;

    private String parameterType;

    private Boolean isValid=Boolean.TRUE;


}

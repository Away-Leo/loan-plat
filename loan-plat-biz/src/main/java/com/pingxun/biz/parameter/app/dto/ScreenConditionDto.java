package com.pingxun.biz.parameter.app.dto;

import com.pingxun.biz.common.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ScreenConditionDto extends BaseDto {

    private String title;

    private String code;

    private List<SpinnerParameterDto> data;

}

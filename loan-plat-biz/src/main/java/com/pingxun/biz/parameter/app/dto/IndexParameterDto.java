package com.pingxun.biz.parameter.app.dto;

import com.pingxun.biz.common.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * 首页配置信息
 * Created by Administrator on 2017/6/1.
 */
@Setter
@Getter
public class IndexParameterDto extends BaseDto{

    //支付方式说明
    private String payModeMemo;

    private Integer applyLimit;//申请限制

    private Integer authFlow;
}

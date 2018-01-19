package com.pingxun.biz.price.app.dto;

import com.pingxun.biz.common.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 认证费用价格表
 * Created by dujy on 2017/7/31.
 */
@Getter
@Setter
public class PriceDto extends BaseDto{

    private Long id;//价格id

    private Integer userType;//用户类型

    private BigDecimal originalPrice;

    private BigDecimal price;//认证费用

    private Date startDate;//开始时间

    private Date endDate;//结束时间

    private String rightAddTime;

    private Date discount;//折扣

    private String feeCode;//费项编码

    private String feeItem;//费用项目

    private String feeGroupCode;//费用组合项目

    private String feeImg;//费用图片

    private Boolean isValid=Boolean.FALSE;//是否有效

}

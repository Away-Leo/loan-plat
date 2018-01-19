package com.pingxun.biz.order.app.dto;

import com.pingxun.biz.common.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 支付结果
 * Created by dujy on 2017/12/07.
 */
@Getter
@Setter
public class PayResultDto extends BaseDto {

    private Long id;

    private String payNo;

    private String payType;

    private BigDecimal payFee=BigDecimal.ZERO;

    private Boolean payStatus=Boolean.FALSE;

    private Date payDate;

    private String backSerialNo;

}

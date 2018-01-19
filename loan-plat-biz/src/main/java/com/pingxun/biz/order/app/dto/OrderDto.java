package com.pingxun.biz.order.app.dto;

import com.pingxun.biz.common.dto.PageDto;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单
 * Created by dujy on 2017/12/07.
 */
@Getter
@Setter
public class OrderDto extends PageDto {

    private Long id;

    private Long userId;

    private String orderNo;

    private String orderDescribe;

    private String productName;

    private String productCode;

    private String feeItem;

    private String feeCode;

    private BigDecimal orderFee;

    private Date orderDate;

    private Integer status;

    private BigDecimal payFee=BigDecimal.ZERO;

    private Date payDate;

    private String payNo;

    private String backSerialNo;

}

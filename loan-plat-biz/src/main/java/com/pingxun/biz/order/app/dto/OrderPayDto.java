package com.pingxun.biz.order.app.dto;

import com.pingxun.biz.common.dto.PageDto;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单支付记录
 * Created by dujy on 2017/12/07.
 */
@Getter
@Setter
public class OrderPayDto extends PageDto {

    private Long id;

    private String payNo;

    private Long userId;

    private String feeItem;

    private String feeCode;

    private BigDecimal orderFee;

    private Date orderDate;

    private Boolean isPay;

    private BigDecimal payFee;

    private Date payDate;

    private String payType;

    private String backSerialNo;

    private String signCode;

    private String name;

    private Date effDate;

    private String startDate;

    private String endDate;

    private String phone;

    private String idCard;

    private String accountNo;

    private BigDecimal oldPrice;

    private BigDecimal activePrice;

    private String areaName;

}

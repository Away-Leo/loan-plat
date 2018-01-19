package com.pingxun.biz.pay.app.dto;

import lombok.Getter;
import lombok.Setter;

/**
* @Title: PayParamDto.java
* @Description:  支付请求参数DTO
* @author Away
* @date 2017/12/23 14:03
* @copyright 重庆平讯数据
* @version V1.0
*/
@Getter
@Setter
public class PayParamDto {
    String payMethod;

    String feeCode;

    Long orderId;

    Long userId;

    String transaction_id;

    String out_trade_no;

    String sign;

    String sign_type;

    AlipayResponseDto alipay_trade_app_pay_response;

}

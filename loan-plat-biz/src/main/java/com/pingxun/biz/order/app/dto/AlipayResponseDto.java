package com.pingxun.biz.order.app.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Away
 * @version V1.0
 * @Title: AlipayResponseDto.java
 * @Description: 支付宝APP返回信息
 * @date 2017/12/24 16:30
 * @copyright 重庆平讯数据
 */
@Getter
@Setter
public class AlipayResponseDto {
    String code;
    String msg;
    String app_id;
    String auth_app_id;
    String charset;
    String timestamp;
    String total_amount;
    String trade_no;
    String seller_id;
    String out_trade_no;
}

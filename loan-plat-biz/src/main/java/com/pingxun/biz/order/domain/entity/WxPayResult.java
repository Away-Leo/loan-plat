package com.pingxun.biz.order.domain.entity;

import com.pingxun.biz.common.entity.AggEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Away
 * @version V1.0
 * @Title: WxPayResult.java
 * @Description: 微信支付结果
 * @date 2017/12/14 11:55
 * @copyright 重庆平讯数据
 */
@Entity
@Table(name = "loan_wxpay_result")
@Getter
@Setter
public class WxPayResult extends AggEntity {


    @Column(name = "payresult_id", columnDefinition = "int(11) comment '支付结果ID'")
    private Long payResultId;

    @Column(name = "appid", columnDefinition = "varchar(32) comment '微信开放平台审核通过的应用APPID'")
    private String appid;

    @Column(name = "mch_id", columnDefinition = "varchar(32) comment '微信支付分配的商户号'")
    private String mchId;

    @Column(name = "device_info", columnDefinition = "varchar(32) comment '微信支付分配的终端设备号'")
    private String deviceInfo;

    @Column(name = "nonce_str", columnDefinition = "varchar(32) comment '随机字符串，不长于32位'")
    private String nonceStr;

    @Column(name = "sign", columnDefinition = "varchar(32) comment '签名'")
    private String sign;

    @Column(name = "result_code", columnDefinition = "varchar(16) comment '业务结果'")
    private String resultCode;

    @Column(name = "openid", columnDefinition = "varchar(128) comment '用户在商户appid下的唯一标识'")
    private String openid;

    @Column(name = "trade_type", columnDefinition = "varchar(16) comment '交易类型'")
    private String tradeType;

    @Column(name = "bank_type", columnDefinition = "varchar(16) comment 'CMC 银行类型，采用字符串类型的银行标识，银行类型见银行列表'")
    private String bankType;

    @Column(name = "total_fee", columnDefinition = "int(11) comment '订单总金额，单位为分'")
    private Long totalFee;

    @Column(name = "fee_type", columnDefinition = "varchar(8) comment 'CNY 货币类型，符合ISO4217标准的三位字母代码，默认人民币：CNY，其他值列表详见货币类型'")
    private String feeType;

    @Column(name = "cash_fee", columnDefinition = "int(11) comment '现金支付金额订单现金支付金额，详见支付金额'")
    private String cashFee;

    @Column(name = "cash_fee_type", columnDefinition = "varchar(16) comment 'CNY 货币类型，符合ISO4217标准的三位字母代码，默认人民币：CNY，其他值列表详见货币类型'")
    private String cashFeeType;

    @Column(name = "transaction_id", columnDefinition = "varchar(32) comment '微信支付订单号'")
    private String transactionId;

    @Column(name = "out_trade_no", columnDefinition = "varchar(32) comment '商户系统内部订单号，要求32个字符内，只能是数字、大小写字母_-|*@ ，且在同一个商户号下唯一'")
    private String outTradeNo;

    @Column(name = "attach", columnDefinition = "varchar(128) comment '商家数据包，原样返回'")
    private String attach;

    @Column(name = "time_end", columnDefinition = "varchar(14) comment '支付完成时间，格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010。其他详见时间规则'")
    private String timeEnd;

}

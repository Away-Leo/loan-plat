package com.pingxun.biz.order.domain.entity;

import com.pingxun.biz.common.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "loan_alipay_result")
@Getter
@Setter
public class AliPayResult extends BaseEntity {

    @Column(name = "result_id", columnDefinition = "int(11) comment '结果表ID'")
    private Long resultId;
    @Column(name = "notify_time", columnDefinition = "varchar(20) comment '通知时间格式为yyyy-MM-dd HH:mm:ss'")
    private String notify_time;
    @Column(name = "notify_type", columnDefinition = "varchar(64) comment '通知类型'")
    private String notify_type;
    @Column(name = "notify_id", columnDefinition = "varchar(128) comment '通知校验ID'")
    private String notify_id;
    @Column(name = "app_id", columnDefinition = "varchar(32) comment '支付宝分配给开发者的应用Id'")
    private String app_id;
    @Column(name = "charset", columnDefinition = "varchar(10) comment '编码格式'")
    private String charset;
    @Column(name = "sign_type", columnDefinition = "varchar(10) comment '签名类型'")
    private String sign_type;
    @Column(name = "sign", columnDefinition = "varchar(256) comment '签名'")
    private String sign;
    @Column(name = "trade_no", columnDefinition = "varchar(64) comment '支付宝交易号'")
    private String trade_no;
    @Column(name = "out_trade_no", columnDefinition = "varchar(64) comment '商户订单号'")
    private String out_trade_no;
    @Column(name = "buyer_id", columnDefinition = "varchar(16) comment '买家支付宝用户号'")
    private String buyer_id;
    @Column(name = "buyer_logon_id", columnDefinition = "varchar(16) comment '买家支付宝账号'")
    private String buyer_logon_id;
    @Column(name = "seller_id", columnDefinition = "varchar(30) comment '卖家支付宝用户号'")
    private String seller_id;
    @Column(name = "seller_email", columnDefinition = "varchar(100) comment '卖家支付宝账号'")
    private String seller_email;
    @Column(name = "trade_status", columnDefinition = "varchar(32) comment '交易状态'")
    private String trade_status;
    @Column(name = "total_amount", columnDefinition = "varchar(32) comment '本次交易支付的订单金额，单位为人民币（元）'")
    private String total_amount;
    @Column(name = "receipt_amount", columnDefinition = "varchar(32) comment '商家在交易中实际收到的款项，单位为元'")
    private String receipt_amount;
    @Column(name = "invoice_amount", columnDefinition = "varchar(32) comment '用户在交易中支付的可开发票的金额'")
    private String invoice_amount;
    @Column(name = "buyer_pay_amount", columnDefinition = "varchar(32) comment '用户在交易中支付的金额'")
    private String buyer_pay_amount;
    @Column(name = "subject", columnDefinition = "varchar(256) comment '商品的标题/交易标题/订单标题/订单关键字等，是请求时对应的参数，原样通知回来'")
    private String subject;
    @Column(name = "body", columnDefinition = "varchar(400) comment '该订单的备注、描述、明细等。对应请求时的body参数，原样通知回来'")
    private String body;
    @Column(name = "gmt_create", columnDefinition = "varchar(20) comment '该笔交易创建的时间。格式为yyyy-MM-dd HH:mm:ss'")
    private String gmt_create;
    @Column(name = "gmt_payment", columnDefinition = "varchar(20) comment '该笔交易的买家付款时间。格式为yyyy-MM-dd HH:mm:ss'")
    private String gmt_payment;
    @Column(name = "gmt_close", columnDefinition = "varchar(20) comment '该笔交易结束时间。格式为yyyy-MM-dd HH:mm:ss'")
    private String gmt_close;
    @Column(name = "fund_bill_list", columnDefinition = "varchar(512) comment '支付成功的各个渠道金额信息，详见资金明细信息说明'")
    private String fund_bill_list;
    @Column(name = "passback_params", columnDefinition = "varchar(512) comment '公共回传参数，如果请求时传递了该参数，则返回给商户时会在异步通知时将该参数原样返回。本参数必须进行UrlEncode之后才可以发送给支付宝'")
    private String passback_params;
}

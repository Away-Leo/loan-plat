package com.pingxun.biz.order.domain.entity;

import com.pingxun.biz.common.entity.AggEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单支付记录
 * Created by dujy on 2017/12/07.
 */
@Entity
@Table(name="loan_order_pay")
@Getter
@Setter
public class OrderPay extends AggEntity {

    @Column(name="pay_no",columnDefinition="varchar(100) not null comment '支付编码'")
    private String payNo;

    @Column(name="user_id",columnDefinition="varchar(100) not null comment '用户ID'")
    private Long userId;

    @Column(name="fee_item",columnDefinition="varchar(32)  comment '费用项'")
    private String feeItem;

    @Column(name="fee_code",columnDefinition="varchar(10) comment '费用编码'")
    private String feeCode;

    @Column(name="order_fee",columnDefinition="decimal(11,2)  comment '订单费用'")
    private BigDecimal orderFee;

    @Column(name="order_date",columnDefinition="datetime comment '订单时间'")
    private Date orderDate;

    @Column(name="is_pay",columnDefinition="tinyint(1) comment '是否支付：0：未支付，1：已支付，9：支付失败'")
    private Boolean isPay=Boolean.FALSE;

    @Column(name="pay_fee",columnDefinition="decimal(11,2) comment '支付金额'")
    private BigDecimal payFee=BigDecimal.ZERO;

    @Column(name="pay_date",columnDefinition="datetime comment '支付时间'")
    private Date payDate;

    @Column(name="pay_type",columnDefinition="varchar(10)  comment '支付方式：alipay:支付宝，wechat:微信'")
    private String payType;

    @Column(name="back_serial_no",columnDefinition="varchar(50) comment '支付订单号'")
    private String backSerialNo;

    @Column(name="pay_signcode",columnDefinition="varchar(100) comment '支付签名'")
    private String signCode;

}

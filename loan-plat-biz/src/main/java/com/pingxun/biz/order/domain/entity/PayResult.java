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
 * 支付结果
 * Created by dujy on 2017/12/07.
 */
@Entity
@Table(name="loan_pay_result")
@Getter
@Setter
public class PayResult extends AggEntity {

    @Column(name="pay_no",columnDefinition="varchar(100) not null comment '支付编码'")
    private String payNo;

    @Column(name="pay_type",columnDefinition="varchar(10) comment '支付方式：zfb:支付宝，wechat:微信'")
    private String payType;

    @Column(name="pay_fee",columnDefinition="decimal(11,2) comment '支付金额'")
    private BigDecimal payFee=BigDecimal.ZERO;

    @Column(name="pay_status",columnDefinition="tinyint(1) comment '支付结果'")
    private Boolean payStatus=Boolean.FALSE;

    @Column(name="pay_date",columnDefinition="datetime comment '支付时间'")
    private Date payDate;

    @Column(name="back_serial_no",columnDefinition="varchar(50) comment '支付返回流水号'")
    private String backSerialNo;

}

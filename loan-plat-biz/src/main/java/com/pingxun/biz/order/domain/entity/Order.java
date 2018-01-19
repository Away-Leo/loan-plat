package com.pingxun.biz.order.domain.entity;

import com.pingxun.biz.common.entity.AggEntity;
import com.pingxun.biz.price.app.dto.PriceDto;
import com.pingxun.biz.price.app.service.PriceAppService;
import com.pingxun.core.common.util.ObjectHelper;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单
 * Created by dujy on 2017/12/07.
 */
@Entity
@Table(name="loan_order")
@Getter
@Setter
public class Order extends AggEntity {

    @Autowired
    private transient PriceAppService priceAppService;

    @Column(name="order_no",columnDefinition="varchar(100) not null comment '订单编码'")
    private String orderNo;

    @Column(name="user_id",columnDefinition="int(11) not null comment '用户ID'")
    private Long userId;

    @Column(name="order_describe",columnDefinition="varchar(250) comment '订单描述'")
    private String orderDescribe;

    @Column(name="product_name",columnDefinition="varchar(100)  comment '产品名称'")
    private String productName;

    @Column(name="product_code",columnDefinition="varchar(20)  comment '产品编码'")
    private String productCode;

    @Column(name="fee_item",columnDefinition="varchar(32)  comment '费用项'")
    private String feeItem;

    @Column(name="fee_code",columnDefinition="varchar(32)  comment '费用项编码'")
    private String feeCode;

    @Column(name="order_fee",columnDefinition="decimal(11,2)  comment '订单费用'")
    private BigDecimal orderFee;

    @Column(name="order_date",columnDefinition="datetime comment '订单时间'")
    private Date orderDate;

    @Column(name="status",columnDefinition="int(11) comment '订单状态：0：下单；1：待支付；2：已支付；3：未支付,9：支付失败'")
    private Integer status;

    @Column(name="pay_fee",columnDefinition="decimal(11,2) comment '支付金额'")
    private BigDecimal payFee=BigDecimal.ZERO;

    @Column(name="pay_date",columnDefinition="datetime comment '支付时间'")
    private Date payDate;

    @Column(name="pay_no",columnDefinition="varchar(50) comment '订单支付表_支付编码'")
    private String payNo;

    @Column(name="back_serial_no",columnDefinition="varchar(50) comment '支付返回流水号'")
    private String backSerialNo;

    public void prepareSave(){
        PriceDto price=priceAppService.findByFeeCode(this.getFeeCode());
        if(ObjectHelper.isNotEmpty(price)){
            this.setFeeItem(price.getFeeItem());
        }
    }

}

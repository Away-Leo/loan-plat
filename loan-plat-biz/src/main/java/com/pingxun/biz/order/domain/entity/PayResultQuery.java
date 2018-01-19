package com.pingxun.biz.order.domain.entity;

import com.pingxun.biz.common.entity.AggEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 支付查询记录表
 * Created by dujy on 2017/12/07.
 */
@Entity
@Table(name="loan_pay_resultquery")
@org.hibernate.annotations.Table(appliesTo="loan_pay_resultquery",comment = "支付查询记录表")
@Getter
@Setter
public class PayResultQuery extends AggEntity {

    @Column(name="query_param",columnDefinition="varchar(2000) not null comment '查询参数'")
    private String queryParam;

}

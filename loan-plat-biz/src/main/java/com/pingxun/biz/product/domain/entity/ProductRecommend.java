package com.pingxun.biz.product.domain.entity;

import com.pingxun.biz.common.entity.AggEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 产品推荐配置表
 * Created by Administrator on 2017/6/1.
 */
@Entity
@Table(name="cw_product_recommend")
@Getter
@Setter
public class ProductRecommend extends AggEntity{

    @Column(name="product_id",columnDefinition="int(11) not null comment '产品ID'")
    private Long product_id;

    @Column(name="recommend_product_id",columnDefinition="int(11) not null comment '推荐产品ID'")
    private Long recommendProductId;

    @Column(name="loan_amount",columnDefinition="decimal(11,2) comment '成功放款金额'")
    private BigDecimal loanAmount=BigDecimal.ZERO;
}

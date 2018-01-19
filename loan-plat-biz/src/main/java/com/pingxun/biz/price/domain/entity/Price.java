package com.pingxun.biz.price.domain.entity;

import com.pingxun.biz.common.entity.AggEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 认证费用价格表
 * Created by dujy on 2017/7/31.
 */
@Entity
@Table(name="loan_price")
@Getter
@Setter
public class Price extends AggEntity{

    @Column(name="user_type",columnDefinition="int(11) comment '用户类型：1借方，2：贷方'")
    private Integer userType;

    @Column(name="original_price",columnDefinition="decimal(11,2)  comment '原价'")
    private BigDecimal originalPrice;

    @Column(name="price",columnDefinition="decimal(11,2)  comment '折后价格'")
    private BigDecimal price;

    @Column(name="start_date",columnDefinition="datetime comment '生效时间'")
    private Date startDate;

    @Column(name="end_date",columnDefinition="datetime comment '失效时间'")
    private Date endDate;

    @Column(name="right_addtime",columnDefinition="varchar(3) comment '此费用项所对应的权力延长时间（例如 延长3个月的会员时间，则为M3）'")
    private String rightAddTime;

    @Column(name="discount",columnDefinition="decimal(11,2) comment '折扣'")
    private BigDecimal discount;

    @Column(name="fee_code",columnDefinition="varchar(10) comment '费用编码'")
    private String feeCode;

    @Column(name="fee_group_code",columnDefinition="varchar(10) comment '费用大项编码'")
    private String feeGroupCode;

    @Column(name="fee_item",columnDefinition="varchar(50) comment '费用项目'")
    private String feeItem;

    @Column(name="fee_img",columnDefinition="varchar(100) comment '费用图片'")
    private String feeImg;

    @Column(name="is_valid",columnDefinition="tinyint(1) comment '是否有效'")
    private Boolean isValid=Boolean.FALSE;

}

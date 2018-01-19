package com.pingxun.biz.parameter.domain.entity;

import com.pingxun.biz.common.entity.AggEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 下拉列表参数类
 * Created by Administrator on 2017/6/1.
 */
@Entity
@Table(name="loan_parameter_spinner")
@Setter
@Getter
public class SpinnerParameter extends AggEntity{

    @Column(name="code",columnDefinition="varchar(30) not null comment '参数值'")
    private String code;

    @Column(name="name",columnDefinition="varchar(30) not null comment '参数名称'")
    private String name;

    @Column(name="type",columnDefinition="varchar(20) not null comment '参数类型'")
    private String type;

    @Column(name="cycle",columnDefinition="varchar(20) not null comment '参数周期()'")
    private String cycle;

    @Column(name="start_value",columnDefinition="decimal(20,4) not null comment '参数周期起始值'")
    private BigDecimal startValue;

    @Column(name="end_value",columnDefinition="decimal(20,4) not null comment '参数周期结束值'")
    private BigDecimal endValue;

    @Column(name="show_order",columnDefinition="int(11) default 0 comment '显示顺序'")
    private Integer showOrder;

    @Column(name="is_valid",columnDefinition="tinyint(1) not null default 1 comment '是否有效'")
    private Boolean isValid=Boolean.TRUE;

    /**
     * 保存数据验证
     */
    public void prepareSave()
    {

    }
}

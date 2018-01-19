package com.pingxun.biz.parameter.domain.entity;

import com.pingxun.biz.common.entity.AggEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 首页查询参数配置
 * Created by Administrator on 2017/6/1.
 */
@Entity
@Table(name="loan_parameter")
@Setter
@Getter
public class Parameter extends AggEntity{

    @Column(name="parameter_code",columnDefinition="varchar(30) not null comment '参数编码'")
    private String parameterCode;

    @Column(name="parameter_name",columnDefinition="varchar(200) not null comment '参数名称'")
    private String parameterName;

    @Column(name="parameter_value",columnDefinition="varchar(200) not null comment '参数值'")
    private String parameterValue;

    @Column(name="parameter_type",columnDefinition="varchar(30) comment '参数类型(日、月)'")
    private String parameterType;

    @Column(name="is_valid",columnDefinition="tinyint(1) not null comment '是否有效'")
    private Boolean isValid=Boolean.TRUE;


    /**
     * 保存数据验证
     */
    public void prepareSave()
    {

    }
}

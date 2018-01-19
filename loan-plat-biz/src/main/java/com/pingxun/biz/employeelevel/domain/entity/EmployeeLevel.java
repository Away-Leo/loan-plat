package com.pingxun.biz.employeelevel.domain.entity;

import com.pingxun.biz.common.entity.AggEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 会员等级
 * Created by dujiangyu on 2017/6/21.
 */
@Entity
@Table(name="loan_employee_level")
@Getter
@Setter
public class EmployeeLevel extends AggEntity {

    @Column(name="name",columnDefinition="varchar(100) not null comment '会员等级'")
    private String name;

    @Column(name="memo",columnDefinition="varchar(500) comment '会员等级描述'")
    private String memo;

    /**
     * 保存数据验证
     */
    public void prepareSave()
    {

    }
}

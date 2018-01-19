package com.pingxun.biz.employeelevel.domain.entity;

import com.pingxun.biz.common.entity.AggEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 会员等级权限
 * Created by dujiangyu on 2017/6/21.
 */
@Entity
@Table(name="loan_employee_level_permission")
@Getter
@Setter
public class EmployeeLevelPermission extends AggEntity {

    @Column(name="level_id",columnDefinition="int(11) not null comment '会员等级ID'")
    private Long levelId;

    @Column(name="amount_id",columnDefinition="int(11) comment '金额期限'")
    private Integer amountId;

    @Column(name="is_sesame_score",columnDefinition="tinyint(1)  comment '芝麻信用分'")
    private Boolean isSesameScore=Boolean.FALSE;

    @Column(name="is_wechat_amount",columnDefinition="tinyint(1)  comment '有无微粒贷额度'")
    private Boolean isWechatAmount=Boolean.FALSE;

    @Column(name="is_social_security",columnDefinition="tinyint(1)  comment '有无社保'")
    private Boolean isSocialSecurity=Boolean.FALSE;

    @Column(name="is_gjj",columnDefinition="tinyint(1)  comment '有无公积金'")
    private Boolean isGjj=Boolean.FALSE;

    @Column(name="is_credit_card",columnDefinition="tinyint(1)  comment '有无信用卡'")
    private Boolean isCreditCard=Boolean.FALSE;

    @Column(name="is_house",columnDefinition="tinyint(1)  comment '有无房贷'")
    private Boolean isHouse=Boolean.FALSE;

    @Column(name="is_car",columnDefinition="tinyint(1)  comment '有无车贷'")
    private Boolean isCar=Boolean.FALSE;
    /**
     * 保存数据验证
     */
    public void prepareSave()
    {

    }
}

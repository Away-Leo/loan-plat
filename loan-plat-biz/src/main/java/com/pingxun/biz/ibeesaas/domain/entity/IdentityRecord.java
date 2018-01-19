package com.pingxun.biz.ibeesaas.domain.entity;

import com.pingxun.biz.common.entity.AggEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
* @Title: IdentityRecord.java
* @Description: 身份认证记录表
* @author Away
* @date 2017/12/17 18:38
* @copyright 重庆平讯数据
* @version V1.0
*/
@Entity
@Table(name="loan_identity_record")
@org.hibernate.annotations.Table(appliesTo = "loan_identity_record",comment="银行实名认证订单记录")
@Getter
@Setter
public class IdentityRecord extends AggEntity {

    @Column(name="user_id",columnDefinition="int(11) not null comment '认证人'")
    private Long userId;

    @Column(name="identity_type",columnDefinition="varchar(32) comment '认证类型'")
    private String identityType;

    @Column(name="identity_result",columnDefinition="varchar(5)  comment '0000-认证成功;0001-认证失败，您的身份信息同银行卡信息不一致;0002-认证失败（失败原因）'")
    private String identityResult;

    @Column(name="identity_params",columnDefinition="varchar(500)  comment '认证传递参数'")
    private String identityParams;

    @Column(name="identity_transid",columnDefinition="varchar(50)  comment '认证订单号'")
    private String identityTransId;

    @Column(name="identity_tradeno",columnDefinition="varchar(50)  comment '交易流水号'")
    private String identityTransNo;

    @Column(name="identity_orgcode",columnDefinition="varchar(50)  comment '机构响应码'")
    private String identityOrgCode;

    @Column(name="identity_orgdesc",columnDefinition="varchar(250)  comment '机构响应描述'")
    private String identityOrgDesc;

    @Column(name="identity_bankid",columnDefinition="varchar(32)  comment '银⾏编码'")
    private String identityBankId;

    @Column(name="identity_fee",columnDefinition="varchar(10)  comment '是否收费 '")
    private String identityFee;




}

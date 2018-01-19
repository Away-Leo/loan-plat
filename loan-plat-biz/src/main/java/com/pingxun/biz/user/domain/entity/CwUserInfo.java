package com.pingxun.biz.user.domain.entity;

import com.pingxun.biz.CwException;
import com.pingxun.biz.common.entity.AggEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户基础信息
 * Created by dujy on 2017/7/31.
 */
@Entity
@Table(name="loan_user_info")
@Getter
@Setter
public class CwUserInfo extends AggEntity{

    @Column(name="user_id",columnDefinition="varchar(100) not null comment '用户ID'")
    private Long userId;

    @Column(name="user_type",columnDefinition="int(11) comment '用户类型：1借方，2：贷方'")
    private Integer userType;

    @Column(name="name",columnDefinition="varchar(100)  comment '姓名'")
    private String name;

    @Column(name="head_img",columnDefinition="varchar(50) comment '用户头像'")
    private String headImg;

    @Column(name="id_card",columnDefinition="varchar(20)  comment '证件号码'")
    private String idCard;

    @Column(name="phone",columnDefinition="varchar(32)  comment '手机号码'")
    private String phone;

    @Column(name="account_no",columnDefinition="varchar(50)  comment '银行卡号'")
    private String accountNo;
    //支付完成后调用认证接口返回数据
    @Column(name="is_authentication",columnDefinition="tinyint(1)  comment '是否实名认证'")
    private Boolean isAuthentication=Boolean.FALSE;

    @Column(name="authentication_time",columnDefinition="int(3)  comment '已用实名认证次数，当认证失败时可能调用多次'")
    private Integer authTime;

    @Column(name="authentication_fee",columnDefinition="decimal(11,2) comment '认证费用'")
    private BigDecimal authenticationFee=BigDecimal.ZERO;

    @Column(name="eff_date",columnDefinition="datetime comment '会员截止日期'")
    private Date effDate;


    @Column(name="area",columnDefinition="varchar(50)  comment '用户所在区域'")
    private String area;

    @Column(name="area_name",columnDefinition="varchar(50)  comment '用户所在区域描述'")
    private String areaName;

    @Column(name="link_address",columnDefinition="varchar(200)  comment '用户详细地址'")
    private String linkAddress;

    @Column(name="is_pay",columnDefinition="tinyint(1) comment '是否支付会员费'")
    private Boolean isPay=Boolean.FALSE;

    @Column(name="social_identity",columnDefinition="varchar(50)  comment '社会身份'")
    private String socialIdentity;

    @Column(name="salary",columnDefinition="decimal(11,2)  comment '月收入'")
    private BigDecimal salary;

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

    @Column(name="employee_level",columnDefinition="int(11) default 0 comment '会员等级:0:普通会员，1：充值会员，2：VIP'")
    private Integer employeeLevel;

    @Column(name="register_date",columnDefinition="datetime comment '注册时间'")
    private Date registerDate;

    @Column(name="message_tip_flag",columnDefinition="tinyint(1)  comment '加好友是否提示设置：0：不提示，1：提示'")
    private Boolean messageTipFlag=Boolean.FALSE;

    public void prepareSave(){
        if(getName()==null||getName().equals("")){
            CwException.throwIt("用户名称不能为空");
        }
        if(getIdCard()==null||getIdCard().equals("")){
            CwException.throwIt("身份证号码不能为空");
        }
        if(getAccountNo()==null||getAccountNo().equals("")){
            CwException.throwIt("银行账号不能为空");
        }
        if(getPhone()==null||getPhone().equals("")){
            CwException.throwIt("银行预留手机号不能为空");
        }
    }
}

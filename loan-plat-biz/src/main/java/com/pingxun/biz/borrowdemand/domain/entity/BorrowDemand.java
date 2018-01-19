package com.pingxun.biz.borrowdemand.domain.entity;

import com.pingxun.biz.CPContext;
import com.pingxun.biz.common.entity.AggEntity;
import com.pingxun.biz.user.app.dto.CwUserInfoDto;
import com.pingxun.biz.user.app.service.CwUserInfoAppService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 借款放款需求
 * Created by dujy on 2017/7/31.
 */
@Entity
@Table(name="loan_borrow_demand")
@Getter
@Setter
public class BorrowDemand extends AggEntity{

    @Autowired
    private transient CwUserInfoAppService cwUserInfoAppService;

    @Column(name="user_id",columnDefinition="int(11) not null comment '用户ID'")
    private Long userId;

    @Column(name="user_name",columnDefinition="varchar(50) comment '用户名称'")
    private String userName;

    @Column(name="head_img",columnDefinition="varchar(50) comment '用户头像'")
    private String headImg;

    @Column(name="is_authentication",columnDefinition="tinyint(1)  comment '是否实名认证'")
    private Boolean isAuthentication=Boolean.FALSE;

    @Column(name="loan_type",columnDefinition="int(11) comment '用户类型：1:借款需求，2：放款需求'")
    private Integer loanType=1;

    @Column(name="loan_fee",columnDefinition="decimal(11,2)  comment '借款金额'")
    private BigDecimal loanFee;

    @Column(name="area",columnDefinition="varchar(50) comment '用户所在区域'")
    private String area;

    @Column(name="area_name",columnDefinition="varchar(50)  comment '用户所在区域描述'")
    private String areaName;

    @Column(name="link_address",columnDefinition="varchar(50)  comment '详细地址'")
    private String linkAddress;

    @Column(name="loan_period",columnDefinition="int(20)  comment '借款期限'")
    private Integer loanPeriod;

    @Column(name="loan_purpose",columnDefinition="varchar(32)  comment '借款用途'")
    private String loanPurpose;

    @Column(name="loan_date",columnDefinition="datetime  comment '借款时间'")
    private Date loanDate;

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

    @Column(name="status",columnDefinition="int(11)  comment '状态'")
    private Integer status=1;

    @Column(name="publish_date",columnDefinition="datetime  comment '发布时间'")
    private Date publishDate;

    @Column(name="is_valid",columnDefinition="tinyint(1)  comment '是否有效1：有效，0：无效'")
    private Boolean isValid=Boolean.TRUE;

    public void prepareSave(){
        CwUserInfoDto cwUserInfoDto = cwUserInfoAppService.findById(CPContext.getContext().getSeUserInfo().getId());
        if(cwUserInfoDto!=null){
            setUserName(cwUserInfoDto.getName());
            setHeadImg(cwUserInfoDto.getHeadImg());
            setIsAuthentication(cwUserInfoDto.getIsAuthentication());
        }
    }
}

package com.pingxun.biz.loandemand.domain.entity;

import com.pingxun.biz.CPContext;
import com.pingxun.biz.common.entity.AggEntity;
import com.pingxun.biz.user.app.dto.CwUserInfoDto;
import com.pingxun.biz.user.app.service.CwUserInfoAppService;
import com.pingxun.biz.user.domain.entity.SeUser;
import com.pingxun.biz.user.domain.service.SeUserService;
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
@Table(name="loan_loan_demand")
@Getter
@Setter
public class LoanDemand extends AggEntity{

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
    private Integer loanType=2;

    @Column(name="loan_start_fee",columnDefinition="decimal(11,2)  comment '借款结束金额'")
    private BigDecimal loanStartFee;

    @Column(name="loan_end_fee",columnDefinition="decimal(11,2)  comment '借款结束金额'")
    private BigDecimal loanEndFee;

    @Column(name="loan_start_period",columnDefinition="int(11)  comment '借款开始期限'")
    private Integer loanStartPeriod;

    @Column(name="loan_end_period",columnDefinition="int(11)  comment '借款结束期限'")
    private Integer loanEndPeriod;

    @Column(name="month_rate",columnDefinition="decimal(11,2)  comment '借款利率'")
    private BigDecimal monthRate;

    @Column(name="area",columnDefinition="varchar(50)  comment '用户所在区域'")
    private String area;

    @Column(name="area_name",columnDefinition="varchar(50)  comment '用户所在区域描述'")
    private String areaName;

    @Column(name="link_address",columnDefinition="varchar(50)  comment '详细地址'")
    private String linkAddress;

    @Column(name="other",columnDefinition="varchar(200)  comment '其他放款需求'")
    private String other;

    @Column(name="publish_date",columnDefinition="datetime  comment '发布时间'")
    private Date publishDate;

    @Column(name="status",columnDefinition="int(11)  comment '状态'")
    private Integer status;

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

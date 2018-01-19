package com.pingxun.biz.loandemand.domain.entity;

import com.pingxun.biz.borrowdemand.app.dto.BorrowDemandDto;
import com.pingxun.biz.borrowdemand.app.service.BorrowDemandAppService;
import com.pingxun.biz.common.entity.AggEntity;
import com.pingxun.biz.loandemand.app.dto.LoanDemandDto;
import com.pingxun.biz.loandemand.app.service.LoanDemandAppService;
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
 * 借款和放款关系对接表
 * Created by dujy on 2017/7/31.
 */
@Entity
@Table(name="loan_loan_borrow_relation")
@Getter
@Setter
public class LoanBorrowRelation extends AggEntity{

    @Autowired
    private transient LoanDemandAppService loanDemandAppService;
    @Autowired
    private transient BorrowDemandAppService borrowDemandAppService;
    @Autowired
    private transient CwUserInfoAppService cwUserInfoAppService;

    @Column(name="loan_id",columnDefinition="int(11) comment '借款需求id'")
    private Long loanId;

    @Column(name="loan_user_id",columnDefinition="int(11) comment '借款人用户ID'")
    private Long loanUserId;

    @Column(name="borrow_id",columnDefinition="int(11) comment '放款需求ID'")
    private Long borrowId;

    @Column(name="borrow_user_id",columnDefinition="int(11) comment '放款用户ID'")
    private Long borrowUserId;

    @Column(name="status",columnDefinition="int(11) comment '借款状态'")
    private Integer status=0;

    @Column(name="transfer_date",columnDefinition="datetime comment '确认交易时间'")
    private Date transferDate;

    @Column(name="user_name",columnDefinition="varchar(50) comment '用户名称'")
    private String userName;

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

    @Column(name="other",columnDefinition="varchar(200)  comment '其他放款需求'")
    private String other;

    @Column(name="id_card",columnDefinition="varchar(20)  comment '证件号码'")
    private String idCard;

    @Column(name="phone",columnDefinition="varchar(32)  comment '手机号码'")
    private String phone;

    @Column(name="account_no",columnDefinition="varchar(50)  comment '银行卡号'")
    private String accountNo;

    @Column(name="area_name",columnDefinition="varchar(100)  comment '区域'")
    private String areaName;

    @Column(name="channel_no",columnDefinition="varchar(20)  comment '区域'")
    private String channelNo;

    public void prepareSave(){
        if(loanId!= null){
            LoanDemandDto loanDemandDto = loanDemandAppService.findById(loanId,Boolean.FALSE);
            this.setUserName(loanDemandDto.getUserName());
            this.setLoanStartFee(loanDemandDto.getLoanStartFee());
            this.setLoanEndFee(loanDemandDto.getLoanEndFee());
            this.setLoanStartPeriod(loanDemandDto.getLoanStartPeriod());
            this.setLoanEndPeriod(loanDemandDto.getLoanEndPeriod());
            this.setMonthRate(loanDemandDto.getMonthRate());
            this.setOther(loanDemandDto.getOther());
            this.setAreaName(loanDemandDto.getAreaName());
            //查询个人认证信息
            CwUserInfoDto cwUserInfoDto = cwUserInfoAppService.findById(loanDemandDto.getUserId());
            this.setAccountNo(cwUserInfoDto.getAccountNo());
            this.setIdCard(cwUserInfoDto.getIdCard());
            this.setPhone(cwUserInfoDto.getPhone());
        }
        if(borrowId!= null){
            BorrowDemandDto borrowDemandDto = borrowDemandAppService.findById(borrowId,Boolean.FALSE);
            this.setUserName(borrowDemandDto.getUserName());
            this.setAreaName(borrowDemandDto.getAreaName());
            this.setLoanStartPeriod(borrowDemandDto.getLoanPeriod());
            this.setLoanEndPeriod(borrowDemandDto.getLoanPeriod());
            this.setLoanStartFee(borrowDemandDto.getLoanFee());
            this.setLoanEndFee(borrowDemandDto.getLoanFee());
            //查询个人认证信息
            CwUserInfoDto cwUserInfoDto = cwUserInfoAppService.findById(borrowDemandDto.getUserId());
            this.setAccountNo(cwUserInfoDto.getAccountNo());
            this.setIdCard(cwUserInfoDto.getIdCard());
            this.setPhone(cwUserInfoDto.getPhone());
        }
    }

}

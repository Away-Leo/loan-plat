package com.pingxun.biz.borrowdemand.app.dto;

import com.pingxun.biz.common.dto.PageDto;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 借款放款需求
 * Created by dujy on 2017/12/11.
 */
@Getter
@Setter
public class BorrowDemandDto extends PageDto {

    private Long id;

    private Long userId;

    private String userName;

    private String headImg;

    private Boolean isAuthentication=Boolean.FALSE;

    private Integer loanType;

    private String area;

    private String areaName;

    private BigDecimal loanFee;


    private BigDecimal loanStartFee;

    private BigDecimal loanEndFee;

    private Integer loanPeriod;

    private Integer loanStartPeriod;

    private Integer loanEndPeriod;

    private String loanPurpose;

    private String socialIdentity;

    private BigDecimal salary;

    private Boolean isSesameScore;

    private Boolean isWechatAmount;

    private Boolean isSocialSecurity;

    private Boolean isGjj;

    private Boolean isCreditCard;

    private Boolean isHouse;

    private Boolean isCar;

    private Date publishDate;

    private Integer status;

    private Boolean isFriend;

    private String idCard;

    private String accountNo;

    private String phone;

    private String linkAddress;

    private Boolean isValid;

    private String loanRequire;

    private String loginPhone;
}

package com.pingxun.biz.loandemand.app.dto;

import com.pingxun.biz.common.dto.PageDto;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 借款和放款关系对接表
 * Created by dujy on 2017/7/31.
 */
@Getter
@Setter
public class LoanBorrowRelationDto extends PageDto {

    private Long id;

    private Long loanId;

    private Integer loanType;

    private Long loanUserId;

    private Long borrowId;

    private Long borrowUserId;

    private Integer status;

    private Date transferDate;

    private String userName;

    private String headImg;

    private Date publishDate;

    private BigDecimal loanStartFee=BigDecimal.ZERO;

    private BigDecimal loanEndFee=BigDecimal.ZERO;

    private BigDecimal loanFee=BigDecimal.ZERO;

    private Integer loanPeriod=0;

    private Integer loanStartPeriod=0;

    private Integer loanEndPeriod=0;

    private BigDecimal monthRate=BigDecimal.ZERO;

    private String other;

    private String idCard;

    private String phone;

    private String accountNo;

    private String socialIdentity;

    private BigDecimal salary=BigDecimal.ZERO;

    private String loanPurpose;

    private Boolean isSesameScore;

    private Boolean isWechatAmount;

    private Boolean isSocialSecurity;

    private Boolean isGjj;

    private Boolean isCreditCard;

    private Boolean isHouse;

    private Boolean isCar;

    private String areaName;

    private String linkAddress;

    private String loginPhone;

    private String channelNo;
}

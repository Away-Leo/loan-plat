package com.pingxun.biz.loandemand.app.dto;

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
public class LoanDemandDto extends PageDto {

    private Long id;

    private Long userId;

    private String userName;

    private String headImg;

    private Boolean isAuthentication=Boolean.FALSE;

    private Integer loanType;

    private String area;

    private String areaName;

    private BigDecimal loanStartFee;

    private BigDecimal loanEndFee;

    private BigDecimal loanFee;

    private Integer loanStartPeriod;

    private Integer loanEndPeriod;

    private Integer loanPeriod;

    private BigDecimal monthRate;

    private String other;

    private Date publishDate;

    private Integer status;

    private Boolean isFriend;//是否是好友

    private String idCard;

    private String accountNo;

    private String linkAddress;

    private String phone;

    private Boolean isValid;

    private Integer employeeLevel;

    private String loginPhone;
}

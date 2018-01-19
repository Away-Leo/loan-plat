package com.pingxun.biz.user.app.dto;

import com.pingxun.biz.common.dto.PageDto;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户信息完善类
 * Created by dujy on 2017/7/31.
 */
@Getter
@Setter
public class CwUserInfoDto extends PageDto{

    private Long id;

    private Long userId;

    private Integer userType=0;//1：借款方,2:出借方

    private String name;

    private String headImg;

    private String loginPhone;

    private String phone;

    private String ids;

    private String certNo;

    private String idCard;

    private String accountNo;
    //博达数据批次号
    private String batchNo;

    private String city;

    private String address;

    private String linkAddress;

    private Date effDate;

    //默认一次导入数据100条
    private Integer exportNum=100;

    private String domicilePlace;

    private String education;

    private String area;

    private String areaName;

    private Boolean isAuthentication=Boolean.FALSE;

    private Integer authTime;

    private Boolean isPay=Boolean.FALSE;

    private String socialIdentity;

    private BigDecimal salary=BigDecimal.ZERO;

    private Boolean isSesameScore;

    private Boolean isWechatAmount;

    private Boolean isSocialSecurity;

    private Boolean isGjj;

    private Boolean isCreditCard;

    private Boolean isHouse;

    private Boolean isCar;

    private String operateType;

    private Integer vipValidMonth=1;

    private Integer employeeLevel;

    private Date registerDate;

    private String startDate;

    private String endDate;

    private Boolean messageTipFlag;
}

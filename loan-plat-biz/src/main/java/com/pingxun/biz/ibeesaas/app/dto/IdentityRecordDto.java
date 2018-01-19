package com.pingxun.biz.ibeesaas.app.dto;

import lombok.Getter;
import lombok.Setter;

/**
* @Title: IdentityRecord.java
* @Description: 身份认证记录表DTO
* @author Away
* @date 2017/12/17 18:38
* @copyright 重庆平讯数据
* @version V1.0
*/
@Getter
@Setter
public class IdentityRecordDto{

    private Long id;

    private Long userId;

    private String identityType;

    private String identityResult;

    private String identityParams;

    private String identityTransId;

    private String identityTransNo;

    private String identityOrgCode;

    private String identityOrgDesc;

    private String identityBankId;

    private String identityFee;




}

package com.pingxun.biz.employeelevel.app.dto;

import com.pingxun.biz.common.dto.PageDto;
import lombok.Getter;
import lombok.Setter;

/**
 * 会员等级
 * Created by dujiangyu on 2017/6/21.
 */
@Getter
@Setter
public class EmployeeLevelDto extends PageDto {

    private Long id;

    private String name;

    private String memo;

    private Integer userNum;

    private Integer amountId;

    private Boolean isSesameScore=Boolean.FALSE;

    private Boolean isWechatAmount=Boolean.FALSE;

    private Boolean isSocialSecurity=Boolean.FALSE;

    private Boolean isGjj=Boolean.FALSE;

    private Boolean isCreditCard=Boolean.FALSE;

    private Boolean isHouse=Boolean.FALSE;

    private Boolean isCar=Boolean.FALSE;

}

package com.pingxun.biz.ibeesaas.app.dto;

import lombok.Getter;
import lombok.Setter;

/**
* @Title: IdentityParam.java
* @Description:  身份验证参数
* @author Away
* @date 2017/12/18 15:46
* @copyright 重庆平讯数据
* @version V1.0
*/
@Getter
@Setter
public class IdentityParamDto {

    private String acc_no;//银⾏行行卡号
    private String id_card;//身份证号
    private String id_holder;//身份证姓名
    private String mobile;//预留电话号
    private Integer userType;//预留电话号
    private Long userId;
}

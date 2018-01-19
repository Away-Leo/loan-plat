package com.pingxun.web.common.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserInfoDto {

    private Integer userType;

    private Boolean isAuthentication;

    private Boolean isPay;

    private Long id;
}

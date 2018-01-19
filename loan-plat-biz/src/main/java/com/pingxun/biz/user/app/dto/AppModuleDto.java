package com.pingxun.biz.user.app.dto;

import com.pingxun.biz.common.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;

/**
 * APP所属模块
 * Created by Administrator on 2017/7/28.
 */
@Getter
@Setter
public class AppModuleDto extends BaseDto {

    private String appName;

    private String moduleIds;

    private Boolean isValid=Boolean.TRUE;

}
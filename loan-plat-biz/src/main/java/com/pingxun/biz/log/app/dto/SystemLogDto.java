package com.pingxun.biz.log.app.dto;

import com.pingxun.biz.common.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 系统操作日志
 * Created by Administrator on 2017/6/1.
 */
@Setter
@Getter
public class SystemLogDto extends BaseDto {

    private Long userId;

    private Date logDate;

    private String url;

    private String operateArg;

    private String action;

    private String name;

    private String remoteAddress;
}

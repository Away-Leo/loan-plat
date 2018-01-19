package com.pingxun.biz.log.app.dto;

import com.pingxun.biz.common.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 短信发送日志
 * Created by Administrator on 2017/6/1.
 */
@Getter
@Setter
public class SendSmsLogDto extends BaseDto {
    private Long id;

    private String phone;

    private Date sendDate;

    private String deviceNumber;

    private String applyArea;

    private String channelNo;

    private String appName;

    private Boolean isSuccess;

}

package com.pingxun.biz.log.app.dto;

import com.pingxun.biz.common.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

/**
 * 短信发送次数
 * Created by Administrator on 2017/6/1.
 */
@Setter
@Getter
public class SendSmsTimeDto extends BaseDto {

    private String phone;

    private Date sendDate;

    private Integer sendTime;

    private String appName;

}

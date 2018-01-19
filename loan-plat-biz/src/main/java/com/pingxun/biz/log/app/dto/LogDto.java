package com.pingxun.biz.log.app.dto;

import com.pingxun.biz.common.dto.BaseDto;
import com.pingxun.biz.log.app.LogEnum;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 日志查询
 * Created by Administrator on 2017/6/1.
 */
@Setter
@Getter
public class LogDto extends BaseDto {

    private Long id;

    private Long productId;
    //推荐来源产品
    private Long sourceProductId;

    private Long userId;

    private String channelNo="ios";

    private LogEnum type;

    private String deviceNumber;

    private String applyArea;

    private Date applyDate;

    private String appName="RRJT";

    private Long inviteCode;

}

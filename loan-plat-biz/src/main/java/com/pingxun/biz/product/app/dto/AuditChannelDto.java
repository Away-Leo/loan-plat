package com.pingxun.biz.product.app.dto;

import com.pingxun.biz.common.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;

/**
 * 金融产品审核版本号
 * Created by Administrator on 2017/11/14.
 */
@Getter
@Setter
public class AuditChannelDto extends BaseDto{

    private Long id;

    private String channelNo;

    private String appName;
}

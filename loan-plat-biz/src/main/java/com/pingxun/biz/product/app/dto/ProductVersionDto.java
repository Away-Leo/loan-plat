package com.pingxun.biz.product.app.dto;

import com.pingxun.biz.common.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * 金融产品
 * Created by Administrator on 2017/6/1.
 */
@Getter
@Setter
public class ProductVersionDto extends BaseDto{
    @NotNull
    private Long id;

    private Integer dataVersion;

    private String channelNo;

    private String apkUrl;

    private String memo;

    private Boolean isAudit;

    private String appName;

}

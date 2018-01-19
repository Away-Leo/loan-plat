package com.pingxun.biz.product.app.dto;

import com.pingxun.biz.common.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;

/**
 * 金融产品审核版本号
 * Created by Administrator on 2017/6/1.
 */
@Getter
@Setter
public class ProductAuditVersionDto extends BaseDto{

    private String dataVersion;

    private Boolean isAudit;

    private String appName;
}

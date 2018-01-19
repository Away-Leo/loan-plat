package com.pingxun.biz.banner.app.dto;

import com.pingxun.biz.common.dto.PageDto;
import lombok.Getter;
import lombok.Setter;

/**
 * 系统banner
 * Created by dujiangyu on 2017/6/21.
 */
@Getter
@Setter
public class BannerDto extends PageDto {

    private Long id;

    private String name;

    private String bannerImg;

    private String bannerDetailImg;

    private String bannerPosition;

    private Integer showOrder;

    private Boolean isValid;

    private String jumpUrl;

    private String versionNo;

    private Boolean isAndroid=Boolean.FALSE;

    private Boolean isApple=Boolean.FALSE;

}

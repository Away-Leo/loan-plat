package com.pingxun.biz.product.app.dto;

import com.pingxun.biz.common.dto.PageDto;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * 金融产品搜索DTO
 * Created by Administrator on 2017/6/1.
 */
@Getter
@Setter
public class ProductSearchDto extends PageDto{

    private String applyStartDate;

    private String applyEndDate;

    private Integer period;

    private String dateCycle="day";

    private BigDecimal amount;

    private String loanAmount;

    private String loanPeriod;

    private String loanType;

    private String name;

    private String channelNo="android";

    private String versionNo;

    private String job;

    private Integer sysType=0;//0：app查询列表 1：后台管理产品

    private String appName="";//app名称

    private String sortColumn;

    private String sortDesc;

    private Boolean isValid;

    private String cooperationModel;

    private String belongApp;
    //产品列表中tab类型：all 全部  xkz :新口子
    private String productType;
    //综合指数类型
    private String zsType;

    private String appSysType;
}

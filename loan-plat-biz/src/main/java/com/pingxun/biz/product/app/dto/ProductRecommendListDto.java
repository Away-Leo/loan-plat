package com.pingxun.biz.product.app.dto;

import com.pingxun.biz.common.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 产品推荐列表对象
 * Created by Administrator on 2017/6/1.
 */
@Setter
@Getter
public class ProductRecommendListDto extends BaseDto{

    private Long id;
    private String name;
    private String img;
    private String recommendImg;
    private BigDecimal serviceRate;
    private Integer startPeriod;
    private Integer endPeriod;
    private String periodType;
    private BigDecimal startAmount;
    private BigDecimal endAmount;
    private Boolean isValid=Boolean.TRUE;
    private Integer viewNum;
    private Integer clickNum;
    private Integer applyNum;
    private String productFlag;
    private String productLabel;
    private Integer isRecommend;
    private String productDesc;
    private String url;
    //合作模式
    private String cooperationModel;
    //合作价格
    private String unitPrice;
    private Date onlineDate;
    private Integer loanDay;
    private Boolean isDownloadApp=Boolean.FALSE;
    private BigDecimal loanAmount=BigDecimal.ZERO;
    private Long productId;
    private String recommendProductId;
    private String recommendType;
    public String getPeriodType() {
        if("month".equals(periodType))
        {
            return "月";
        }else {
            return "日";
        }
    }
}

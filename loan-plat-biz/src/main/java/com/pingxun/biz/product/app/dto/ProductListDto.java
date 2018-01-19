package com.pingxun.biz.product.app.dto;

import com.pingxun.biz.common.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 金融产品
 * Created by Administrator on 2017/6/1.
 */
@Setter
@Getter
public class ProductListDto extends BaseDto{

    private Long id;
    private String name;
    private String img;
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
    private Integer recommendApplyNum;
    private String productFlag;
    private String productLabel;
    private Integer isRecommend;
    private String productDesc;
    private String url;
    //后台登录地址
    private String backendLoginUrl;
    //用户名
    private String loginUser;
    //密码
    private String loginPwd;
    //合作模式
    private String cooperationModel;
    //合作价格
    private String unitPrice;
    private Date onlineDate;
    private Integer loanDay;
    private Boolean isDownloadApp=Boolean.FALSE;

    private Integer showOrder=0;

    private Integer showOrderJds=0;
    //讯闪贷排序
    private Integer showOrderXsd=0;
    //借钱帝排序
    private Integer showOrderJqw=0;
    //借钱乐乐排序
    private Integer showOrderDsqb=0;
    //贷款钱包
    private Integer showOrderDkqb=0;
    //贷款部落排序
    private Integer showOrderLyb=0;
    //贷款帝
    private Integer showOrderPxqb=0;
    //叮叮招财排序
    private Integer showOrderLsqd=0;
    //速亿贷
    private Integer showOrderSyd=0;
    //速融贷排序
    private Integer showOrderSrd=0;
    //借贷帮排序
    private Integer showOrderJdb=0;
    //贷钱来排序
    private Integer showOrderDql=0;
    //贷款掌柜排序
    private Integer showOrderDkzg=0;
    //借借款排序
    private Integer showOrderJjk=0;

    //信而贷排序
    private Integer showOrderXed=0;
    //借钱树排序
    private Integer showOrderJqs=0;
    //贷款汇排序
    private Integer showOrderDkh=0;
    //借钱花花排序
    private Integer showOrderJqhh=0;
    //借贷金钱
    private Integer showOrderJdjq=0;
    //贷款堂
    private Integer showOrderDkt=0;

    //快借王
    private Integer showOrderKjw;
    //秒贷宝
    private Integer showOrderMdb;
    //好贷宝
    private Integer showOrderHdb;
    //贷款神速
    private Integer showOrderDkss;
    //借钱富
    private Integer showOrderJqf;
    //易借侠
    private Integer showOrderYjx;
    private Integer showOrderJdk;
    //贷款达
    private Integer showOrderDkd;
    private String sortDesc;

    private Integer limitUserTop=0;
    private Integer isHidden;
    private String jumpUrl;

    private Integer todayApplyUser;

    private String appSysType;

    private String subTitle;
    private Integer startLevel;
    public String getPeriodType() {
        if("month".equals(periodType))
        {
            return "月";
        }else {
            return "日";
        }
    }
}

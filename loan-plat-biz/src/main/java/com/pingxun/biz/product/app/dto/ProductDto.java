package com.pingxun.biz.product.app.dto;

import com.pingxun.biz.common.dto.BaseDto;
import com.pingxun.core.common.util.AppUtils;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 金融产品
 * Created by Administrator on 2017/6/1.
 */
@Setter
@Getter
public class ProductDto extends BaseDto{

    private Long id;
    @NotNull
    private String name;
    @NotNull
    private String img;
    @NotNull
    private String detailImg;
    private String recommendImg;

    private String productType="";
    private String productSubType;
    private String serviceType="day";
    @NotNull
    private BigDecimal serviceRate;
    @NotNull
    private String rateMemo;
    private String periodType;
    private String periodTypeStr;
    @NotNull
    private Integer startPeriod;
    @NotNull
    private Integer endPeriod;
    @NotNull
    private BigDecimal startAmount;
    @NotNull
    private BigDecimal endAmount;
    @NotNull
    private String loanDay;
    private String applyFlow;
    @NotNull
    private String applyCondition;
    @NotNull
    private String applyMaterial;
    private String productDesc;
    private String sourceType;

    private String channel;

    private String url;

    private Integer showOrder;

    private Integer viewNum;

    private Integer clickNum;

    private Date startDate;

    private Date endDate;

    private Date onlineDate;
    private Date saveDate;

    private Boolean isValid=Boolean.FALSE;

    private Boolean androidFlag=Boolean.FALSE;

    private Boolean appleFlag=Boolean.FALSE;

    private Boolean wechatFlag=Boolean.FALSE;

    private String productFlag;

    private String belongApp= AppUtils.appNameMBD;

    private Integer showOrderJds;
    //讯闪贷排序
    private Integer showOrderXsd;
    //借钱帝排序
    private Integer showOrderJqw;
    //贷款钱包
    private Integer showOrderDkqb;
    //借钱乐乐排序
    private Integer showOrderDsqb;
    //叮叮招财排序
    private Integer showOrderLsqd;
    //贷款部落排序
    private Integer showOrderLyb;
    //贷款帝
    private Integer showOrderPxqb;
    //速亿贷
    private Integer showOrderSyd;
    //速融贷排序
    private Integer showOrderSrd;
    //借贷帮排序
    private Integer showOrderJdb;
    //贷钱来排序
    private Integer showOrderDql;
    //贷款掌柜排序
    private Integer showOrderDkzg;
    //借借款排序
    private Integer showOrderJjk;

    //信而贷排序
    private Integer showOrderXed;
    //借钱树排序
    private Integer showOrderJqs;
    //贷款汇排序
    private Integer showOrderDkh;
    //借钱花花排序
    private Integer showOrderJqhh;
    //借贷金钱
    private Integer showOrderJdjq;
    //贷款堂
    private Integer showOrderDkt;

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
    //借贷款
    private Integer showOrderJdk;
    //贷款达
    private Integer showOrderDkd;


    //是否推荐 1:推荐，2：热门，3:新口子
    private Integer isRecommend;
    //产品标签
    private String productLabel;
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

    private Boolean isDownloadApp=Boolean.FALSE;

    private Integer limitUserTop;

    private Integer isHidden;

    private String jumpUrl;

    private Integer todayApplyUser;

    private Long operateNo;

    private String accountName;

    private String openBankName;

    private String bankAccount;

    private String linkAddress;

    private String linkPhone;

    private Integer invoiceType;

    private String invoiceItem;

    private String taxesObject;

    private String taxpayerNo;

    private String invoiceLinkAddress;

    private String invoiceLinkPhone;

    private String invoiceMemo;
    private Integer recommendFlag;
    private BigDecimal loanAmount;
    private String subTitle;
    private Integer startLevel;
    private Integer settleCycle;

    public String getPeriodTypeStr() {
        if("month".equals(periodType))
        {
            periodTypeStr = "月";
        }else {
            periodTypeStr = "天";
        }
        return periodTypeStr;
    }
}

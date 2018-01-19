package com.pingxun.biz.product.app.dto;

import com.pingxun.biz.common.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;

/**
 * 产品排序DTO
 * Created by Administrator on 2017/9/5.
 */
@Setter
@Getter
public class ProductSortDto extends BaseDto {
    private String id;

    private String productId;

    private String showOrder;

    private String showOrderJds;
    //讯闪贷排序
    private String showOrderXsd;
    //借钱帝排序
    private String showOrderJqw;
    //贷款钱包
    private String showOrderDkqb;
    //借钱乐乐排序
    private String showOrderDsqb;
    //叮叮招财排序
    private String showOrderLsqd;
    //贷款部落排序
    private String showOrderLyb;
    //贷款帝
    private String showOrderPxqb;
    //速亿贷
    private String showOrderSyd;
    //速融贷排序
    private String showOrderSrd;
    //借贷帮排序
    private String showOrderJdb;
    //贷钱来排序
    private String showOrderDql;
    //贷款掌柜排序
    private String showOrderDkzg;
    //借借款排序
    private String showOrderJjk;
    //信而贷排序
    private String showOrderXed;
    //借钱树排序
    private String showOrderJqs;
    //贷款汇排序
    private String showOrderDkh;
    //借钱花花排序
    private String showOrderJqhh;
    //借贷金钱
    private String showOrderJdjq;
    //贷款堂
    private String showOrderDkt;

    //快借王
    private String showOrderKjw;
    //秒贷宝
    private String showOrderMdb;
    //好贷宝
    private String showOrderHdb;
    //贷款神速
    private String showOrderDkss;
    //借钱富
    private String showOrderJqf;
    //易借侠
    private String showOrderYjx;

    private String limitUserTop;

    private String isHidden;

    private String jumpUrl;

    private String appSysType;
}

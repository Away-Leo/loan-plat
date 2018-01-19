package com.pingxun.biz.order.enums;

/**
* @Title: ENUM_PAY_URL.java
* @Description: 支付接口地址
* @author Away
* @date 2017/12/13 15:25
* @copyright 重庆平讯数据
* @version V1.0
*/
public enum ENUM_PAY_URL {

    /**
     * 微信统一下单
     */
    WX_UNIFIEDORDER("https://api.mch.weixin.qq.com/pay/unifiedorder"),
    /**
     * 微信查询订单
     */
    WX_ORDERQUERY("https://api.mch.weixin.qq.com/pay/orderquery");

    public final  String value;

    ENUM_PAY_URL(String val){
        this.value=val;
    }
}

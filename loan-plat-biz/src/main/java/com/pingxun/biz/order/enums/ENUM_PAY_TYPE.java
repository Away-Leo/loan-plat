package com.pingxun.biz.order.enums;

/**
* @Title: ENUM_PAY_URL.java
* @Description: 支付类型
* @author Away
* @date 2017/12/13 15:25
* @copyright 重庆平讯数据
* @version V1.0
*/
public enum ENUM_PAY_TYPE {

    /**
     * 微信
     */
    WECHAT("wechat"),

    /**
     * 支付宝
     */
    ALIPAY("alipay");

    public final  String value;

    ENUM_PAY_TYPE(String val){
        this.value=val;
    }
}

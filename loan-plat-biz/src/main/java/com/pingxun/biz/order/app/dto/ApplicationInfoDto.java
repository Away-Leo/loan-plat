package com.pingxun.biz.order.app.dto;

import lombok.Getter;
import lombok.Setter;

/**
* @Title: ApplicationInfoDto.java
* @Description:  应用信息数据载体
* @author Away
* @date 2017/12/13 17:43
* @copyright 重庆平讯数据
* @version V1.0
*/
@Getter
@Setter
public class ApplicationInfoDto {

    /**在微信平台上申请的appid*/
    private String wxAppId;

    /**微信支付设置的安全key*/
    private String wxKey;

    /**微信支付分配的商户号*/
    private String wxMchId;

    /**微信支付后回调地址*/
    private String wxNotifyUrl;

    /**支付宝appid*/
    private String alipayAppId;

    /**支付宝我方生成私钥*/
    private String alipayUsPrivateKey;

    /**支付宝给我方的公钥*/
    private String alipayPublicKey;

    /**支付宝给我方的商户UUID*/
    private String alipayBusiUuid;

    /**支付宝支付结果回调地址*/
    private String alipayNotifyUrl;

    /**支付宝网关*/
    private String alipayGateway;

    /**内部支付服务生成预付单地址*/
    private String payServiceStartUrl;

    /**内部支付服务查询支付结果地址*/
    private String payServiceQueryUrl;

    /**内部支付服务微信回调*/
    private String payServiceWxNotifyUrl;

    /**内部支付服务支付宝回调*/
    private String payServiceAliNotifyUrl;

    /**内部实名认证服务地址*/
    private String identServiceUrl;

}

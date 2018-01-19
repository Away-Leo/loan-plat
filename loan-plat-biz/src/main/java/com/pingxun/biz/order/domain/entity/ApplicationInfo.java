package com.pingxun.biz.order.domain.entity;

import com.pingxun.biz.common.entity.AggEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
* @Title: ApplicationInfo.java
* @Description: app应用信息
* @author Away
* @date 2017/12/13 17:38
* @copyright 重庆平讯数据
* @version V1.0
*/
@Entity
@Table(name="loan_app_info")
@Getter
@Setter
public class ApplicationInfo extends AggEntity {

    @Column(name="info_wx_appid",columnDefinition="varchar(100) not null comment '在微信平台上申请的appid'")
    private String wxAppId;

    @Column(name="info_wx_key",columnDefinition="varchar(32) not null comment '微信支付设置的安全key'")
    private String wxKey;

    @Column(name="info_wx_mchid",columnDefinition="varchar(32) not null  comment '微信支付分配的商户号'")
    private String wxMchId;

    @Column(name="info_wx_notify_url",columnDefinition="varchar(200) not null  comment '微信支付后回调地址'")
    private String wxNotifyUrl;

    @Column(name="info_alipay_appid",columnDefinition="varchar(32) not null  comment '支付宝appid'")
    private String alipayAppId;

    @Column(name="info_alipay_usprivatekey",columnDefinition="varchar(2048) not null  comment '支付宝我方生成私钥'")
    private String alipayUsPrivateKey;

    @Column(name="info_alipay_publickey",columnDefinition="varchar(2048) not null  comment '支付宝给我方的公钥'")
    private String alipayPublicKey;

    @Column(name="info_alipay_busiuuid",columnDefinition="varchar(32)  comment '支付宝给我方的商户UUID'")
    private String alipayBusiUuid;

    @Column(name="info_alipay_notyurl",columnDefinition="varchar(200)  comment '支付宝支付结果回调地址'")
    private String alipayNotifyUrl;

    @Column(name="info_alipay_gateway",columnDefinition="varchar(200)  comment '支付宝网关'")
    private String alipayGateway;

    @Column(name="info_payservice_start_url",columnDefinition="varchar(200)  comment '内部支付服务生成预付单地址'")
    private String payServiceStartUrl;

    @Column(name="info_payservice_query_url",columnDefinition="varchar(200)  comment '内部支付服务查询支付结果地址'")
    private String payServiceQueryUrl;

    @Column(name="info_payservice_wxnotifyurl",columnDefinition="varchar(200)  comment '内部支付服务微信回调'")
    private String payServiceWxNotifyUrl;

    @Column(name="info_payservice_alinotifyurl",columnDefinition="varchar(200)  comment '内部支付服务支付宝回调'")
    private String payServiceAliNotifyUrl;

    @Column(name="info_identservice_url",columnDefinition="varchar(200)  comment '内部实名认证服务地址'")
    private String identServiceUrl;

}

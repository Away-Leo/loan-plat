package com.pingxun.biz.ibeesaas.util;

/**
 * 常量定义：appkey，ak，sk，各个taskTypes等
 */
public class Config {
    public final static String APPKEY = "jabyc7qxup"; //TODO: 需要替换成分配的appkey
    public final static String AK = "3i51qakUFRBF4ehQ1ByMc3gvf94yBzV3"; //TODO: 需要替换成分配的ak
    public final static String SK = "Em1XgJ0PkXOO6HUS69cFTnrnH5cRe0k8"; //TODO: 需要替换成分配的sk，此值务必妥善保管，不可泄露。

//    public static String BASE_URL = "https://testapi.ibeesaas.com"; //测试环境url
    public static String BASE_URL = "https://api.ibeesaas.com"; //正式环境url
    public final static String URLPATH_PREFIX = "/daas/v1/tasks";
    public final static String IBEEAUTH_TOKEN = "X-IbeeAuth-Token";
    public final static String DEF_CHATSET = "UTF-8";
    public final static String CONTENTTYPE = "application/json";

    //TaskTypes
    public final static String TYPE_CARRIER = "carrier"; //运营商
    public final static String TYPE_TAOBAO = "taobao"; //淘宝
    public final static String TYPE_INSURANCE = "insurance"; //社保
    public final static String TYPE_ELEMENT4 = "element4";//银行卡实名认证
    public final static String TYPE_ELEMENT3 = "element3";//手机实名认证
    public final static String TYPE_BLACK = "black"; //网贷黑名单
    public final static String TYPE_PBC = "pbc";//人行征信报告
    public final static String TYPE_FUND = "fund";//公积金
    public final static String TYPE_ALIPAY = "alipay";//支付宝
    public final static String TYPE_EMAILBILL = "email_bill";//邮箱账单
    public final static String TYPE_BANK = "bank";//网银
    public final static String TYPE_EDUCATION = "education";//学历
    public final static String TYPE_RESUME = "resume";//简历
    public final static String TYPE_MAIMAI = "maimai";//脉脉
    public final static String TYPE_WEIBO = "weibo";//微博
    public final static String TYPE_BAIDU = "baidu";//百度
    public final static String TYPE_JD = "jd";//京东
    public final static String TYPE_BLACKCOURT = "blackcourt";//高法黑名单
    public final static String TYPE_BLACKCRIME = "blackcrime";//犯罪记录黑名单
}

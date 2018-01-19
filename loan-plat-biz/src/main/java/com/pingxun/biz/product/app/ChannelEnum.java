package com.pingxun.biz.product.app;

/**
 * app来源渠道
 * Created by Administrator on 2017/6/1.
 */
public enum ChannelEnum {

    IOS("ios","苹果端"),
    ANDROID("android","安卓端"),
    WECHAT("wechat","微信端");

    private String key;
    private String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    ChannelEnum(String key, String value){
        this.key = key;
        this.value = value;
    }
}

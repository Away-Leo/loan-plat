package com.pingxun.biz.log.app;

/**
 * 日志类型枚举
 * Created by Administrator on 2017/6/1.
 */
public enum LogEnum {

    USER_REGISTER("USER_REGISTER","用户注册"),
    USER_LOGIN("USER_LOGIN","用户登陆"),
    APPLY_LOAN("APPLY_LOAN","贷款申请"),
    DOWNLOAD_APK("DOWNLOAD_APK","下载APK"),
    APPLICATION_LINK("APPLICATION_LINK","应用市场链接"),
    BANNER_LINK("BANNER_LINK","首页链接"),
    PUSH_LINK("PUSH_LINK","推送链接");

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

    LogEnum(String key, String value){
        this.key = key;
        this.value = value;
    }
}

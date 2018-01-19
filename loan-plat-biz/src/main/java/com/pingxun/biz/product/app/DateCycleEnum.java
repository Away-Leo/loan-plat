package com.pingxun.biz.product.app;

/**
 * 产品类型枚举
 * Created by Administrator on 2017/6/1.
 */
public enum DateCycleEnum {

    DAY("day","日"),
    MONTH("month","月");

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

    DateCycleEnum(String key, String value){
        this.key = key;
        this.value = value;
    }
}

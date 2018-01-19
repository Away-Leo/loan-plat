package com.pingxun.biz.parameter.app;

/**
 *
 * Created by Administrator on 2017/6/4.
 */
public enum ParameterEnum {

    PAYMODE_MEMO("payModeMemo","支付方式说明"),
    APPLY_LIMIT("applyLimit","申请限制"),
    AUTH_FLOW("authFlow","认证流程");
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

    ParameterEnum(String key,String value){
        this.key = key;
        this.value = value;
    }
}

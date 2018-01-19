package com.pingxun.biz.order.enums;

/**
* @Title: ENUM_PAY_URL.java
* @Description: 支付类型
* @author Away
* @date 2017/12/13 15:25
* @copyright 重庆平讯数据
* @version V1.0
*/
public enum ENUM_WX_RETURN_RESULT {

    SUCCESS("支付成功"),
    REFUND("转入退款"),
    NOTPAY("未支付"),
    CLOSED("已关闭"),
    REVOKED("已撤销刷卡支付"),
    USERPAYING("用户支付中"),
    PAYERROR("支付失败");

    public final  String value;

    ENUM_WX_RETURN_RESULT(String val){
        this.value=val;
    }

    public static String getMsg(String type) {
        String returnData="";
        for(ENUM_WX_RETURN_RESULT temp: ENUM_WX_RETURN_RESULT.values()){
            if(temp.toString().equalsIgnoreCase(type)){
                returnData=temp.value;
            }
        }
        return returnData;
    }
}

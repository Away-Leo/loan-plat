package com.pingxun.core.common.util;

import java.lang.reflect.Field;

/**
* @Title: RequestParamaterUtil.java
* @Description:  请求参数工具类
* @author Away
* @date 2017/12/22 15:32
* @copyright 重庆平讯数据
* @version V1.0
*/
public class RequestParamaterUtil {

    /**
     * @Author: Away
     * @Description: 实体对象将属性拼接为请求字符串
     * @Param: pojoParam
     * @Return java.lang.String
     * @Date 2017/12/22 15:33
     * @Copyright 重庆平讯数据
     */
    public static String pojoToString(Object pojoParam){
        if(ObjectHelper.isNotEmpty(pojoParam)){
            String returnData="?";
            Field[] fields=pojoParam.getClass().getDeclaredFields();
            for(Field temp:fields){
                temp.setAccessible(true);
                try {
                    Object val = temp.get(pojoParam);//得到此属性的值
                    String name=temp.getName();
                    returnData=returnData+name+"="+val.toString()+"&";
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            return returnData;
        }else{
            return "";
        }
    }
}

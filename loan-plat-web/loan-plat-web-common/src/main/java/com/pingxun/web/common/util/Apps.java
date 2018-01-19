package com.pingxun.web.common.util;

/**
 * Created by dujy on 2017-05-29.
 */
public class Apps {

     public static String getAppName() {
    String name = System.getProperty("loan.appName");
    if (name == null) {
      throw new RuntimeException("没有设置应用名称,请设置系统变量loan.appName");
    }
    return name;
  }

 public static String getAppSessionCookieName() {
    return getAppName() + "-session";
  }
}

package com.pingxun.core.common.util;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.StringReader;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;

/**
 * 工具类
 * Created by Administrator on 2017/6/1.
 */
public class Utils {
    /**
     * 产生随机验证码
     *
     * @return
     */
    public static int getRandNum() {
        int randomInt = new Random().nextInt(999999);
        return randomInt;
    }


    /**
     * 时间格式转换
     *
     * @param date
     * @return
     */
    public static String convertDate(Date date) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String nowDate = simpleDateFormat.format(date);
            return nowDate;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 前一个月的数据
     *
     * @param date
     * @return
     */
    public static String convertNowBeforeDate(Date date) {
        try {
            Date dNow = new Date();   //当前时间
            Date dBefore = new Date();
            Calendar calendar = Calendar.getInstance(); //得到日历
            calendar.setTime(dNow);//把当前时间赋给日历
            calendar.add(Calendar.DAY_OF_MONTH, -30);  //设置为前一天
            dBefore = calendar.getTime();   //得到前一天的时间

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //设置时间格式
            String defaultStartDate = sdf.format(dBefore);    //格式化前一天
            return defaultStartDate;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Date addDate(Date startData, String addType, int addNum) {
        if (ObjectHelper.isEmpty(startData)) startData = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(startData);
        if(ObjectHelper.isNotEmpty(addType)&&ObjectHelper.isNotEmpty(addNum)){
            //月
            if(addType.equalsIgnoreCase("M")){
                calendar.add(Calendar.MONTH,addNum);
            }else if(addType.equalsIgnoreCase("Y")){//年
                calendar.add(Calendar.YEAR,addNum);
            }else{//日
                calendar.add(Calendar.DAY_OF_YEAR,addNum);
            }
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            return calendar.getTime();
        }else{
            return null;
        }
    }

    /**
     * @Author: Away
     * @Description: 得到当前不包含时分秒的日期
     * @Return java.util.Date
     * @Date 2018/1/5 14:31
     * @Copyright 重庆平讯数据
     */
    public static Date getCurrentDayDate(){
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 前一个月的数据
     * @param monthId
     * @return
     */
    public static Date dateAddMonth(Integer monthId){
        try {
            Date dNow = new Date();   //当前时间
            Date nextMonth = new Date();
            Calendar calendar = Calendar.getInstance(); //得到日历
            calendar.setTime(dNow);//把当前时间赋给日历
            calendar.add(Calendar.MONTH, monthId);  //当前时间增加月份
            nextMonth = calendar.getTime();   //得到前一天的时间

            return nextMonth;
        }catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取系统时间前一天
     *
     * @return
     */
    public static String beforeDay() {
        Date dNow = new Date();   //当前时间
        Date dBefore = new Date();
        Calendar calendar = Calendar.getInstance(); //得到日历
        calendar.setTime(dNow);//把当前时间赋给日历
        calendar.add(Calendar.DAY_OF_MONTH, -1);  //设置为前一天
        dBefore = calendar.getTime();   //得到前一天的时间

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //设置时间格式
        String defaultStartDate = sdf.format(dBefore);    //格式化前一天
        return defaultStartDate;
    }

    public static Date strConvertDate(String date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//小写的mm表示的是分钟
            return sdf.parse(date);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * string to xml
     *
     * @param xmlStr
     * @return
     */
    public static String strConvertXml(String xmlStr) {
        String result = null;
        try {
            Document document = DocumentHelper.parseText(xmlStr);
            Element root = document.getRootElement();
            result = root.element("returnstatus").getText();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String randomStr() {
        String str = "";
        str += (int) (Math.random() * 9 + 1);
        for (int i = 0; i < 5; i++) {
            str += (int) (Math.random() * 10);
        }
        return str;
    }

    /**
     * 发送数据接口
     *
     * @param url
     * @return
     */
    public static String sendDateInterface(String url, String send, String sendType) {

        try {
            String backEncodType = "UTF-8";
            String sendResult = "";
            if (sendType != null && (sendType.toLowerCase()).equals("get")) {
                sendResult = SmsClientAccessTool.getInstance().doAccessHTTPGet(
                        url + "?" + send.toString(), backEncodType);
            } else {
                sendResult = SmsClientAccessTool.getInstance().doAccessHTTPPost(url,
                        send.toString(), backEncodType);
            }
            return sendResult;
        } catch (Exception e) {
            return "error";
        }
    }


    public static long calcDateDiffMinute(Date one, Date two) {
        long day = 0;
        long hour = 0;
        long min = 0;
        try {
            long time1 = one.getTime();
            long time2 = two.getTime();
            long diff ;
            if(time1<time2) {
                diff = time2 - time1;
            } else {
                diff = time1 - time2;
            }
            day = diff / (24 * 60 * 60 * 1000);
            hour = (diff / (60 * 60 * 1000) - day * 24);
            min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return min;
    }

    /**
     * 中文解析成字母拼音
     * @param str
     * @return
     */
    public static String convertNameToPingy(String str) {
        PingyinUtils hanyu = new PingyinUtils();
        // 中英文混合的一段文字
        String strPinyin = hanyu.getStringPinYin(str);
        System.out.println(strPinyin);
        return strPinyin;
    }

    /**
     * 对客户姓名做掩码
     * @throws Exception
     * */
    public static String maskUserName(String userName)
    {
        try {
            if (userName == null || userName.length() == 0) return "";
            String v = userName.substring(0, 1);
            return StringUtils.rightPad(v, userName.length(), "*");
            //StringUtils.rightPad方法做一个字符串右补齐
        }catch (Exception e){
            e.getMessage();
        }
        return "";
    }

    /**
     * 对客户证件号码做掩码
     *
     * */
    public static String maskCertId(String certId)
    {
        try {
            if (certId == null || certId.length() == 0) return "";
            if (certId.length() == 18) {
                String v = certId.substring(0, 4);
                String end = certId.substring(certId.length() - 4);
                return v + StringUtils.repeat("*", 10) + end;
            } else
                return "";
        }catch (Exception e){}
        return "";
    }
    //手机号掩码
    public static String maskPhone(String str){
        String newStr="";
        if(str!=null)
        if(str.length()==11) {
            newStr = str.substring(0, str.length() - (str.substring(3)).length()) + "****" + str.substring(7);
        }
        return newStr;
    }

    /**
     * 银行账号掩码
     * @param accountNo
     * @return
     */
    public static String maskAccountNo(String accountNo) {
        if(accountNo!=null) {
            int length = accountNo.length();
            if (accountNo.length() > 15) {
                String str = accountNo.substring(0, length - 11) + "*******" + accountNo.substring(length - 2);
                return str;
            } else {
                return accountNo;
            }
        }else{
            return accountNo;
        }
    }
            /**
             * @Author: Away
             * @Description: 获得请求客户端的IP地址
             * @Param: request
             * @Return java.lang.String
             * @Date 2017/12/14 17:01
             * @Copyright 重庆平讯数据
             */
        public static String getIp2 (HttpServletRequest request){
            try {
                String ip = request.getHeader("X-Forwarded-For");
                if (ObjectHelper.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
                    //多次反向代理后会有多个ip值，第一个ip才是真实ip
                    int index = ip.indexOf(",");
                    if (index != -1) {
                        return ip.substring(0, index);
                    } else {
                        return ip;
                    }
                }
                ip = request.getHeader("X-Real-IP");
                if (ObjectHelper.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
                    return ip;
                }
                return request.getRemoteAddr();
            } catch (Exception e) {
                e.printStackTrace();
                return "";
            }
        }
}
package com.pingxun.biz.ibeesaas;//package com.pingxun.biz.ibeesaas;
//
//import com.pingxun.biz.ibeesaas.util.JsonUtils;
//import com.pingxun.biz.ibeesaas.util.TaskUtil;
//
//import java.util.LinkedHashMap;
//import java.util.Map;
//
//public class TaskPatch {
//
//	public static void main(String[] args) throws InterruptedException {
//		String taskNo = "7ddfe6d1e5f44b92a9dc8d114bdfd8601501307443119"; //需要补充信息的taskNo
//
//		Map<String, Object> pBody = new LinkedHashMap<String, Object>();
//
//		//patchCode=2000, 提交手机验证码
//		pBody.put("patchCode", 2000);
//		pBody.put("data", "gnke");
//
//		//patchCode=2001, 重发手机短信验证码
////		pBody.put("patchCode", 2001);
//
//		//patchCode=2002, 提交图片验证码
////		pBody.put("patchCode", 2002);
////		pBody.put("data", "gnke");
//
//		//patchCode=2003, 重发图片验证码
////		pBody.put("patchCode", 2003);
//
//		//patchCode=2004, 用户已扫描二维码并确认
////		pBody.put("patchCode", 2004);
//
//		//patchCode=2005, 提交账号，密码
////		pBody.put("patchCode", 2005);
////		Map<String, Object> pMap = new LinkedHashMap<String, Object>();
////		pMap.put("account", "user_xxxx");
////		pMap.put("password", "pwd_xxxx");
////		pBody.put("data", pMap);
//
//		//patchCode=2006, 提交身份证号码和姓名
////		pBody.put("patchCode", 2006);
////		Map<String, Object> pMap = new LinkedHashMap<String, Object>();
////		pMap.put("idCardNo", "用户身份证号");
////		pMap.put("realName", "用户姓名");
////		pBody.put("data", pMap);
//
//		//patchCode=2007, 重发二维码图片
////		pBody.put("patchCode", 2007);
//
//        //patchCode=2008, 提交地区编码，用于社保和公积金数据
////		pBody.put("patchCode", 2008);
////		pBody.put("data", "00506530");
//
//        //patchCode=2009, 该patchCode针对社保、公积金查询，data里面的字段，需要根据patchCode=2008的返回json，动态调整
////		pBody.put("patchCode", 2009);
////		Map<String, Object> pMap = new LinkedHashMap<String, Object>();
////		pMap.put("xxx", "xxx");
////		pMap.put("yyy", "yyy");
////		pBody.put("data", pMap);
//
//        //patchCode=2010, 提交人行登录信息，用于人行征信报告
////		pBody.put("patchCode", 2010);
////		Map<String, Object> pMap = new LinkedHashMap<String, Object>();
////		pMap.put("account", "人行账号");
////		pMap.put("password", "密码");
////		pMap.put("smsCode", "人行征信官网下发的授权短信验证码");
////		pBody.put("data", pMap);
//
//        //patchCode=2011, 提交独立密码
////		pBody.put("patchCode", 2011);
////		pBody.put("data", "独立密码");
//
//        //patchCode=2012, 提交选择登陆的银行编码，用于网银数据
////		pBody.put("patchCode", 2012);
////		pBody.put("data", "银行编码");
//
//        //patchCode=2013, 提交网银登录信息，用于网银数据
////		pBody.put("patchCode", 2013);
////		Map<String, Object> pMap = new LinkedHashMap<String, Object>();
////		pMap.put("bankCode", "银行编号");
////		pMap.put("loginParamType", 16);
////		pMap.put("loginEntrance", 0);
////		pMap.put("loginTypeId", 1197);
////      Map<String, Object> pSubMap = new LinkedHashMap<String, Object>();
////      pSubMap.put("Mobile", "137*****472");
////      pSubMap.put("LoginPwd", "324443");
////      pMap.put("loginFields", pSubMap);
////		pBody.put("data", pMap);
//
//        //patchCode=2014, 提交账户名、密码和图片验证码
////		pBody.put("patchCode", 2014);
////		Map<String, Object> pMap = new LinkedHashMap<String, Object>();
////		pMap.put("account", "user_xxxx");
////		pMap.put("password", "pwd_xxxx");
////		pMap.put("data", "图片验证码字符串");
////		pBody.put("data", pMap);
//
//        //patchCode=2015, 提交邮箱验证码
////		pBody.put("patchCode", 2015);
////		pBody.put("data", "邮箱验证码");
//
//        //patchCode=2016, 重发邮箱验证码
////		pBody.put("patchCode", 2016);
//
//		String bodyJson = JsonUtils.objectToJson(pBody);
//
//		String res = TaskUtil.patchTask(taskNo, bodyJson);
//		System.out.println(res);
//	}
//}

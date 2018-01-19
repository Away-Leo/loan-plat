package com.pingxun.biz.pay.app.service;

import com.alibaba.fastjson.JSONObject;
import com.pingxun.biz.CPContext;
import com.pingxun.biz.order.app.dto.PayParamDto;
import com.pingxun.biz.order.app.dto.PayResultQueryDto;
import com.pingxun.biz.order.app.service.AliPayAppService;
import com.pingxun.biz.order.app.service.ApplicationInfoAppService;
import com.pingxun.biz.order.app.service.PayResultQueryAppService;
import com.pingxun.biz.order.enums.ENUM_PAY_TYPE;
import com.pingxun.biz.user.app.dto.CwUserInfoDto;
import com.pingxun.core.common.util.ObjectHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
* @Title: PayAppService.java
* @Description:  支付服务
* @author Away
* @date 2017/12/22 16:34
* @copyright 重庆平讯数据
* @version V1.0
*/
@Service
public class PayAppService {

    @Autowired
    private ApplicationInfoAppService applicationInfoAppService;

    @Autowired
    private AliPayAppService aliPayAppService;

//    @Autowired
//    private WXPayAppService wxPayAppService;

    @Autowired
    private PayResultQueryAppService payResultQueryAppService;

    public String startPay(HttpServletRequest request, String userId, String payMethod, String feeCode, String orderId) throws Exception{
//        String params="creatIp='"+ Utils.getIp2(request)+"'&userId="+userId+"&payMethod="+payMethod+"&feeCode="+feeCode+"&orderId="+orderId;
        String returnData="";
        if(ObjectHelper.isNotEmpty(payMethod)){
            if(payMethod.equalsIgnoreCase(ENUM_PAY_TYPE.ALIPAY.value)){
                returnData=aliPayAppService.unifiedorder(Long.parseLong(userId),Long.parseLong(orderId), feeCode);
            }else{
//                returnData=wxPayAppService.unifiedorder(Long.parseLong(userId),Utils.getIp2(request), feeCode, Long.parseLong(orderId));
            }
        }
        return returnData;
    }

    public CwUserInfoDto queryPay(PayParamDto payParamDto) throws Exception{
        payParamDto.setUserId(CPContext.getContext().getSeUserInfo().getId());
        //保存查询记录
        PayResultQueryDto payResultQueryDto=new PayResultQueryDto();
        payResultQueryDto.setQueryParam(JSONObject.toJSONString(payParamDto));
        this.payResultQueryAppService.saveData(payResultQueryDto);

//        if(payParamDto.getPayMethod().equalsIgnoreCase(ENUM_PAY_TYPE.WECHAT.value)){
//            return this.wxPayAppService.orderQuery(payParamDto);
//        }else {
            return this.aliPayAppService.queryTradeResult(payParamDto);
//        }
    }

    public String alipayNotify(Map<String,String> params) throws Exception{
        String result=this.aliPayAppService.responseAliPayNotify(params);
        return result;
    }

//    public String wxNotify(String params) throws BusinessException{
//        return wxPayAppService.responseWechatPayNotify(params);
//    }
}

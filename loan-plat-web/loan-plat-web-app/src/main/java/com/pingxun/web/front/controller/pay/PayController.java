package com.pingxun.web.front.controller.pay;

import com.alibaba.fastjson.JSONObject;
import com.pingxun.biz.CPContext;
import com.pingxun.biz.order.app.dto.OrderDto;
import com.pingxun.biz.order.app.dto.PayParamDto;
import com.pingxun.biz.order.app.service.OrderAppService;
import com.pingxun.biz.pay.app.service.PayAppService;
import com.pingxun.biz.user.app.dto.CwUserInfoDto;
import com.pingxun.web.common.dto.CPViewResultInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Controller
@RequestMapping("/")
public class PayController {

    @Autowired
    private PayAppService payAppService;

    @Autowired
    private OrderAppService orderAppService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * @Author: Away
     * @Description: 支付宝支付回调
     * @Param: aliPayResultDto
     * @Return java.lang.String
     * @Date 2017/12/22 18:21
     * @Copyright 重庆平讯数据
     */
    @RequestMapping(value = "common/notify/alipay", method = RequestMethod.POST)
    @ResponseBody
    public String alipayNotify(HttpServletRequest request) {
        logger.info("进入服务-支付宝回调:{}"+JSONObject.toJSONString(request.getParameterMap()));
        try{
            //获取支付宝POST过来反馈信息
            Map<String, String> params = new HashMap<>();
            Map requestParams = request.getParameterMap();
            for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
                String name = (String) iter.next();
                String[] values = (String[]) requestParams.get(name);
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i]
                            : valueStr + values[i] + ",";
                }
                //乱码解决，这段代码在出现乱码时使用
                //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
                params.put(name, valueStr);
            }
            String result=payAppService.alipayNotify(params);
            logger.info("离开服务-支付宝回调:{}"+result);
            return result;
        }catch (Exception e){
            return "ERROR";
        }

    }


    /**
     * @Author: Away
     * @Description: 微信支付回调
     * @Param: params
     * @Return java.lang.String
     * @Date 2017/12/22 18:29
     * @Copyright 重庆平讯数据
     */
//    @PostMapping("common/notify/weixin.json")
//    @ResponseBody
//    public String wxNotify(String params) {
//        return payAppService.wxNotify(params);
//    }


    @PostMapping("front/pay/startpay.json")
    @ResponseBody
    public CPViewResultInfo startPay(HttpServletRequest request, @RequestBody PayParamDto payParamDto) {
        CPViewResultInfo info = new CPViewResultInfo();
        try {
            String result = payAppService.startPay(request, CPContext.getContext().getSeUserInfo().getId() + "", payParamDto.getPayMethod(), payParamDto.getFeeCode(), payParamDto.getOrderId() + "");
            info.setSuccess(true);
            info.setData(result);
        } catch (Exception e) {
            info.setSuccess(false);
            info.setMessage(e.getMessage());
            logger.error("PX004", "生成预付单出错", e);
        }
        return info;
    }

    @GetMapping("front/pay/querypay.json")
    @ResponseBody
    public CPViewResultInfo queryPay(String resultStr,String payMethod) {
        CPViewResultInfo returnInfo = new CPViewResultInfo();
        try {
            PayParamDto payParamDto=JSONObject.parseObject(resultStr,PayParamDto.class);
            payParamDto.setPayMethod(payMethod);
            CwUserInfoDto result = payAppService.queryPay(payParamDto);
            returnInfo.setSuccess(true);
            returnInfo.setData(result);
        } catch (Exception e) {
            returnInfo.setSuccess(false);
            returnInfo.setMessage(e.getMessage());
            logger.error("PX004", "查询支付结果出错", e);
        }
        return returnInfo;
    }

    @GetMapping("front/order/createOrder")
    @ResponseBody
    public CPViewResultInfo createOrder(String feeCode) {
        CPViewResultInfo info = new CPViewResultInfo();
        try {
            OrderDto orderDto = this.orderAppService.newOrder(feeCode);
            info.setSuccess(true);
            info.setData(orderDto);
        } catch (Exception e) {
            info.setSuccess(false);
            info.setMessage("系统错误");
            logger.error("PX004", "新建订单出错", e);
        }
        return info;
    }

}

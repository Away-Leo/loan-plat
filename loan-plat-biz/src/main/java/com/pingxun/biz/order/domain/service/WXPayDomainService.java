//package com.pingxun.biz.order.domain.service;
//
//import com.alibaba.fastjson.JSONObject;
//import com.pingxun.biz.order.app.dto.ApplicationInfoDto;
//import com.pingxun.biz.order.app.dto.OrderDto;
//import com.pingxun.biz.order.app.dto.OrderPayDto;
//import com.pingxun.biz.order.app.dto.PayResultDto;
//import com.pingxun.biz.order.app.service.ApplicationInfoAppService;
//import com.pingxun.biz.order.domain.entity.Order;
//import com.pingxun.biz.order.domain.entity.OrderPay;
//import com.pingxun.biz.order.domain.entity.PayResult;
//import com.pingxun.biz.order.enums.ENUM_PAY_TYPE;
//import com.pingxun.biz.order.enums.ENUM_WX_RETURN_RESULT;
//import com.pingxun.biz.order.sdk.wxsdk.WXPay;
//import com.pingxun.biz.order.sdk.wxsdk.WXPayConfigImpl;
//import com.pingxun.biz.order.sdk.wxsdk.WXPayUtil;
//import com.pingxun.biz.price.domain.entity.Price;
//import com.pingxun.biz.price.domain.service.PriceDomainService;
//import com.pingxun.biz.user.app.dto.CwUserInfoDto;
//import com.pingxun.biz.user.app.service.CwUserInfoAppService;
//import com.pingxun.core.common.util.ObjectHelper;
//import com.pingxun.core.common.util.Utils;
//import com.zds.common.lang.exception.BusinessException;
//import com.zds.common.lang.util.DateUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.math.BigDecimal;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//
///**
//* @Title: WXPayDomainService.java
//* @Description:
//* @author Away
//* @date 2017/12/16 13:49
//* @copyright 重庆平讯数据
//* @version V1.0
//*/
//@Service
//public class WXPayDomainService {
//
//    @Autowired
//    private PayResultDomainService payResultDomainService;
//
//    @Autowired
//    private ApplicationInfoAppService applicationInfoAppService;
//
//    @Autowired
//    private PriceDomainService priceDomainService;
//
//    @Autowired
//    private WxPayResultDomainService wxPayResultDomainService;
//
//    @Autowired
//    private OrderDomainService orderDomainService;
//
//    @Autowired
//    private OrderPayDomainService orderPayDomainService;
//
//    @Autowired
//    private CwUserInfoAppService cwUserInfoAppService;
//
//    private WXPay wxpay;
//    private WXPayConfigImpl config;
//
//    public String responseWechatPayNotify(String paramse) {
//        //定义失败的返回数据，以让微信持续回调此函数
//        String returnData = "<xml><return_code>FAIL</return_code><return_msg></return_msg></xml>";
//        try {
//            Map<String, String> wxReturn = WXPayUtil.xmlToMap(paramse);
//            if (!"SUCCESS".equalsIgnoreCase(wxReturn.get("return_code")) || !"SUCCESS".equalsIgnoreCase(wxReturn.get("result_code"))||!"SUCCESS".equalsIgnoreCase(wxReturn.get("trade_state")))
//                return returnData;
//            //微信返回流水号
//            String backSerialNo = wxReturn.get("transaction_id");
//            PayResult payResultDto = payResultDomainService.findByBackSeriNoAndPayType(backSerialNo, ENUM_PAY_TYPE.WECHAT.value);
//            //如果数据库中已经存在此支付返回数据则告诉微信服务器成功接收数据，不用再回调此函数
//            if (ObjectHelper.isNotEmpty(payResultDto)&&payResultDto.getPayStatus()) {
//                returnData = "<xml><return_code>SUCCESS</return_code><return_msg>OK</return_msg></xml>";
//                return returnData;
//            }
//            ApplicationInfoDto applicationInfoDto=this.applicationInfoAppService.getAppInfo();
//            //验签
//            boolean signCheck= WXPayUtil.isSignatureValid(paramse,applicationInfoDto.getWxKey());
//            if(!signCheck)return returnData;
//            //按照返回签名查找支付数据，此步骤是为了校验数据来源的安全性
//            OrderPay orderPay = orderPayDomainService.findByPayNo(wxReturn.get("out_trade_no"));
//            //对比金额
//            Integer total_fee = Integer.parseInt(wxReturn.get("total_fee"));
//            //转换为元
//            int total_fee_yuan = total_fee / 100;
//            //如果数据校对成功则将此笔数据写入数据库
//            if (orderPay.getPayFee().compareTo(new BigDecimal(total_fee_yuan)) == 0&&!orderPay.getIsPay()) {
//                //写入支付结果表
//                PayResultDto payResultSaveData = new PayResultDto();
//                payResultSaveData.setBackSerialNo(backSerialNo);
//                payResultSaveData.setPayDate(DateUtil.stringToDate(DateUtil.dtLong, wxReturn.get("time_end")));
//                payResultSaveData.setPayFee(new BigDecimal(wxReturn.get("total_fee")).divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP));
//                payResultSaveData.setPayNo(wxReturn.get("out_trade_no"));
//                payResultSaveData.setPayStatus(wxReturn.get("trade_state").equalsIgnoreCase("SUCCESS"));
//                payResultSaveData.setPayType(ENUM_PAY_TYPE.WECHAT.value);
//                PayResult savedData = payResultDomainService.create(payResultSaveData);
//
//                //修改用户数据
//                CwUserInfoDto cwUserInfoDto=this.cwUserInfoAppService.findAuthInfoById(orderPay.getUserId());
//                cwUserInfoDto.setIsPay(wxReturn.get("trade_state").equalsIgnoreCase("SUCCESS"));
//                if(cwUserInfoDto.getUserType()==2&&wxReturn.get("trade_state").equalsIgnoreCase("SUCCESS")) {
//                    cwUserInfoDto.setEffDate(Utils.dateAddMonth(cwUserInfoDto.getVipValidMonth()));
//                }
//                cwUserInfoAppService.update(cwUserInfoDto);
//                if (ObjectHelper.isNotEmpty(savedData)) {
//                    returnData = "<xml><return_code>SUCCESS</return_code><return_msg>OK</return_msg></xml>";
//                    //更新支付表,改为已支付
//                    orderPay.setIsPay(wxReturn.get("trade_state").equalsIgnoreCase("SUCCESS"));
//                    orderPay.setBackSerialNo(backSerialNo);
//                    this.orderPayDomainService.update(orderPay.to(OrderPayDto.class));
//                    //向微信支付结果表写入数据
//                    wxReturn.put("id",savedData.getId()+"");
//                    wxPayResultDomainService.saveWithMap(wxReturn);
//                }
//            }
//        } catch (Exception e) {
//            WXPayUtil.getLogger().error("WXPayAppService.responseWechatPayNotify出错", e);
//            e.printStackTrace();
//        }
//        return returnData;
//    }
//
//    /**
//     * @Author: Away
//     * @Description: 生成预支付交易单
//     * @Return java.lang.String
//     * @Date 2017/12/14 15:03
//     * @Copyright 重庆平讯数据
//     */
//    public String unifiedorder(Long userId,String crateIp,String feeCode,Long orderId) throws Exception {
//        if(ObjectHelper.isEmpty(userId))throw new BusinessException("PX001","用户ID为空");
//        //根据订单id查找并更新订单数据（绑定支付编码）
//        Order orderDto=orderDomainService.findById(orderId);
//        //查找app配置信息
//        ApplicationInfoDto applicationInfoDto = applicationInfoAppService.getAppInfo();
//        //查找费用项信息
//        Price priceDto = priceDomainService.findByFeeCode(feeCode);
//        if(ObjectHelper.isEmpty(priceDto)){
//            throw new BusinessException("PX002","按照费用项编码查询费用项出错，无此数据");
//        }
//        //生成订单支付数据
//        Map<String, String> requestCondition = new HashMap<String, String>();
//        requestCondition.put("appid", applicationInfoDto.getWxAppId());
//        requestCondition.put("mch_id", applicationInfoDto.getWxMchId());
//        requestCondition.put("device_info",userId+"" );
//        requestCondition.put("nonce_str", WXPayUtil.generateNonceStr());
//        requestCondition.put("sign_type", "MD5");
//        requestCondition.put("body", priceDto.getFeeItem());
//        requestCondition.put("attach",feeCode);
//        requestCondition.put("out_trade_no",feeCode+new Date().getTime()+ WXPayUtil.MD5(WXPayUtil.generateNonceStr()));
//        requestCondition.put("total_fee",priceDto.getPrice().multiply(BigDecimal.valueOf(100)).longValue()+"");
//        requestCondition.put("spbill_create_ip",crateIp);
//        requestCondition.put("time_start", DateUtil.longDate(new Date()));
//        requestCondition.put("time_expire", getCurrent30minitsAfter());
//        requestCondition.put("notify_url", applicationInfoDto.getWxNotifyUrl());
//        requestCondition.put("trade_type", "APP");
//        //加上签名信息
//        String lastParam= WXPayUtil.generateSignedXml(requestCondition,applicationInfoDto.getWxKey());
//        requestCondition= WXPayUtil.xmlToMap(lastParam);
//
//        OrderPay orderPayDto=this.findOrSaveOrderPay(userId,requestCondition,orderDto);
//        if(orderPayDto.getIsPay()){
//            throw new BusinessException("PX004","该订单已经支付成功");
//        }
//        //更新订单
//        orderDto.setPayNo(orderPayDto.getPayNo());
//        this.orderDomainService.update(orderDto.to(OrderDto.class));
//        if(ObjectHelper.isEmpty(config)){
//            config = WXPayConfigImpl.getInstance();
//        }
//        if(ObjectHelper.isEmpty(wxpay)){
//            wxpay = new WXPay(config);
//        }
//        Map<String,String> sourceResponseData=wxpay.unifiedOrder(requestCondition);
//        if(ObjectHelper.isNotEmpty(sourceResponseData.get("prepay_id"))){
//            //组装返回给APP的数据
//            Map<String,String> returnParams=new HashMap<>();
//            returnParams.put("appid",sourceResponseData.get("appid"));
//            returnParams.put("partnerid",sourceResponseData.get("mch_id"));
//            returnParams.put("prepayid",sourceResponseData.get("prepay_id"));
//            returnParams.put("package",sourceResponseData.get("Sign=WXPay"));
//            returnParams.put("noncestr", WXPayUtil.generateNonceStr());
//            returnParams.put("timestamp",Integer.valueOf(String.valueOf(new Date().getTime()/1000))+"");
//            String signedStr= WXPayUtil.generateSignedXml(returnParams,applicationInfoDto.getWxKey());
//            return JSONObject.toJSONString(WXPayUtil.xmlToMap(signedStr));
//        }else{
//            throw new BusinessException("PX004",sourceResponseData.get("err_code_des")+"");
//        }
//    }
//
//    /**
//     * @Author: Away
//     * @Description
//     * @Param: params 发给微信的参数
//     * @Param: orderDto 查找的订单
//     * @Return com.pingxun.biz.order.app.dto.OrderPayDto
//     * @Date 2017/12/14 17:45
//     * @Copyright 重庆平讯数据
//     */
//    private OrderPay findOrSaveOrderPay(Long userId,Map<String,String> params,Order orderDto) throws BusinessException{
//        OrderPay orderPayDto=this.orderPayDomainService.findByUserIdAndFeeCode(userId,params.get("attach"));
//        //如果有此费用项数据并且为未支付状态
//        if(ObjectHelper.isNotEmpty(orderPayDto)&&!orderPayDto.getIsPay()){
//            orderPayDto.setOrderDate(orderDto.getOrderDate());
//            orderPayDto.setPayDate(DateUtil.string2Date(params.get("time_start"),DateUtil.dtLong));
//            orderPayDto.setOrderFee(orderDto.getOrderFee());
//            orderPayDto.setPayNo(params.get("out_trade_no"));
//            orderPayDto.setPayFee(new BigDecimal(params.get("total_fee")).divide(BigDecimal.valueOf(100),2));
//            orderPayDto.setSignCode(params.get("sign"));
//            orderPayDto.setPayType(ENUM_PAY_TYPE.WECHAT.value);
//            orderPayDto=this.orderPayDomainService.update(orderPayDto.to(OrderPayDto.class));
//            return orderPayDto;
//        }//没有此项费用记录
//        else if(ObjectHelper.isEmpty(orderPayDto)){
//            orderPayDto=new OrderPay();
//            orderPayDto.setUserId(userId);
//            orderPayDto.setFeeItem(params.get("body"));
//            orderPayDto.setFeeCode(params.get("attach"));
//            orderPayDto.setIsPay(false);
//            orderPayDto.setPayDate(DateUtil.string2Date(params.get("time_start"),DateUtil.dtLong));
//            orderPayDto.setOrderDate(orderDto.getOrderDate());
//            orderPayDto.setOrderFee(orderDto.getOrderFee());
//            orderPayDto.setPayNo(params.get("out_trade_no"));
//            orderPayDto.setPayFee(new BigDecimal(params.get("total_fee")).divide(BigDecimal.valueOf(100),2));
//            orderPayDto.setSignCode(params.get("sign"));
//            orderPayDto.setPayType(ENUM_PAY_TYPE.WECHAT.value);
//            return this.orderPayDomainService.create(orderPayDto.to(OrderPayDto.class));
//        }else{
//            return orderPayDto;
//        }
//    }
//
//    /**
//     * @Author: Away
//     * @Description
//     * @Param: transaction_id 微信支付返回支付流水号
//     * @Param: out_trade_no 支付编码
//     * @Return java.util.Map<java.lang.String,java.lang.String>
//     * @Date 2017/12/14 21:39
//     * @Copyright 重庆平讯数据
//     */
//    public CwUserInfoDto orderQuery(Long userId, String transaction_id, String out_trade_no) throws Exception{
//        if(ObjectHelper.isEmpty(userId))throw new BusinessException("PX001","用户ID为空");
//        //查找app配置信息
//        ApplicationInfoDto applicationInfoDto = applicationInfoAppService.getAppInfo();
//        //查找支付结果
//        PayResult payResultDto=payResultDomainService.findByPayNo(out_trade_no);
//        //查找支付订单信息
//        OrderPay orderPayDto=orderPayDomainService.findByPayNo(out_trade_no);
//        Map<String,String> queryCondition=new HashMap<String, String>();
//        queryCondition.put("appid",applicationInfoDto.getWxAppId());
//        queryCondition.put("mch_id",applicationInfoDto.getWxMchId());
//        queryCondition.put("transaction_id",transaction_id);
//        queryCondition.put("nonce_str", WXPayUtil.generateNonceStr());
//        String lastConditions= WXPayUtil.generateSignedXml(queryCondition,applicationInfoDto.getWxKey());
//        queryCondition= WXPayUtil.xmlToMap(lastConditions);
//        if(ObjectHelper.isEmpty(config)){
//            config = WXPayConfigImpl.getInstance();
//        }
//        if(ObjectHelper.isEmpty(wxpay)){
//            wxpay = new WXPay(config);
//        }
//        CwUserInfoDto returnData=null;
//        Map<String,String> queryResult=wxpay.orderQuery(queryCondition);
//        if("SUCCESS".equalsIgnoreCase(queryResult.get("return_code"))){
//            if("SUCCESS".equalsIgnoreCase(queryResult.get("result_code"))){
//                if("SUCCESS".equalsIgnoreCase(queryResult.get("trade_state"))){
//                    //更新用户
//                    returnData=this.cwUserInfoAppService.findAuthInfoById(userId);
//                    returnData.setIsPay(true);
//                    returnData=this.cwUserInfoAppService.updateUser(returnData).to(CwUserInfoDto.class);
//                    //强制修改支付结果表和订单支付表，以微信服务器返回为准
//                    if(ObjectHelper.isNotEmpty(payResultDto)){
//                        if(!payResultDto.getPayStatus()){
//                            payResultDto.setPayStatus(true);
//                            payResultDto.setBackSerialNo(queryResult.get("transaction_id"));
//                            payResultDomainService.updateData(payResultDto.to(PayResultDto.class));
//                        }
//                    }else{
//                        payResultDto=new PayResult();
//                        payResultDto.setPayStatus(true);
//                        payResultDto.setPayType(ENUM_PAY_TYPE.WECHAT.value);
//                        payResultDto.setPayNo(out_trade_no);
//                        payResultDto.setPayFee(new BigDecimal(queryResult.get("total_fee")).divide(BigDecimal.valueOf(100),2));
//                        payResultDto.setPayDate(DateUtil.string2Date(queryResult.get("time_end"),DateUtil.dtLong));
//                        payResultDto.setBackSerialNo(transaction_id);
//                        payResultDomainService.create(payResultDto.to(PayResultDto.class));
//                    }
//                    if(ObjectHelper.isNotEmpty(orderPayDto)){
//                        if(!orderPayDto.getIsPay()){
//                            orderPayDto.setIsPay(true);
//                            orderPayDto.setBackSerialNo(transaction_id);
//                            orderPayDto=orderPayDomainService.update(orderPayDto.to(OrderPayDto.class));
//                        }
//                    }else{
//                        orderPayDto=new OrderPay();
//                        orderPayDto.setIsPay(true);
//                        orderPayDto.setOrderDate(DateUtil.string2Date(queryResult.get("time_end"),DateUtil.dtLong));
//                        orderPayDto.setFeeCode(queryResult.get("attach"));
//                        orderPayDto.setPayFee(new BigDecimal(queryResult.get("total_fee")).divide(BigDecimal.valueOf(100),2));
//                        orderPayDto.setPayNo(out_trade_no);
//                        orderPayDto.setOrderFee(new BigDecimal(queryResult.get("total_fee")).divide(BigDecimal.valueOf(100),2));
//                        orderPayDto.setPayDate(DateUtil.string2Date(queryResult.get("time_end"),DateUtil.dtLong));
//                        orderPayDto.setBackSerialNo(transaction_id);
//                        orderPayDto.setPayType(ENUM_PAY_TYPE.WECHAT.value);
//                        orderPayDto.setUserId(userId);
//                        orderPayDto=orderPayDomainService.update(orderPayDto.to(OrderPayDto.class));
//                    }
//                }else{
//                    throw new BusinessException("PX004", ENUM_WX_RETURN_RESULT.getMsg(queryResult.get("trade_state")));
//                }
//            }else{
//                //错误信息描述
//                throw new BusinessException("PX004",queryResult.get("err_code_des"));
//            }
//        }else{
//            throw new BusinessException("PX004",queryResult.get("return_msg"));
//        }
//        return returnData;
//    }
//
//    /**
//     * @Author: Away
//     * @Description: 获得当前日期后30分钟的时间
//     * @Return java.lang.String
//     * @Date 2017/12/14 17:30
//     * @Copyright 重庆平讯数据
//     */
//    private String getCurrent30minitsAfter(){
//        long curren = System.currentTimeMillis();
//        curren += 30 * 60 * 1000;
//        Date da = new Date(curren);
//        return DateUtil.longDate(da);
//    }
//
//}

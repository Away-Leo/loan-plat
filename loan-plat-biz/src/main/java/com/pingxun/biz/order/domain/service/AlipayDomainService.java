package com.pingxun.biz.order.domain.service;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.pingxun.biz.order.app.dto.*;
import com.pingxun.biz.order.domain.entity.ApplicationInfo;
import com.pingxun.biz.order.domain.entity.Order;
import com.pingxun.biz.order.domain.entity.OrderPay;
import com.pingxun.biz.order.domain.entity.PayResult;
import com.pingxun.biz.order.enums.ENUM_PAY_TYPE;
import com.pingxun.biz.order.sdk.wxsdk.WXPayUtil;
import com.pingxun.biz.price.domain.entity.Price;
import com.pingxun.biz.price.domain.service.PriceDomainService;
import com.pingxun.biz.user.app.dto.CwUserInfoDto;
import com.pingxun.biz.user.app.service.CwUserInfoAppService;
import com.pingxun.core.common.util.BeanToMapUtil;
import com.pingxun.core.common.util.ObjectHelper;
import com.pingxun.core.common.util.Utils;
import com.zds.common.lang.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * @author Away
 * @version V1.0
 * @Title: AlipayDomainService.java
 * @Description: 支付宝支付service
 * @date 2017/12/15 16:19
 * @copyright 重庆平讯数据
 */
@Service
public class AlipayDomainService {

    @Autowired
    private ApplicationInfoDomainService applicationInfoDomainService;

    @Autowired
    private PriceDomainService priceDomainService;

    @Autowired
    private OrderDomainService orderDomainService;

    @Autowired
    private OrderPayDomainService orderPayDomainService;

    @Autowired
    private PayResultDomainService payResultDomainService;

    @Autowired
    private AlipayResultDomainService alipayResultDomainService;

    @Autowired
    private CwUserInfoAppService cwUserInfoAppService;

    /**
     * 支付宝客户端
     */
    protected AlipayClient alipayClient;

    /**
     * @Author: Away
     * @Description： 支付宝生成预付单
     * @Param: orderId 订单ID
     * @Param: feeCode 费用项code
     * @Return java.lang.String
     * @Date 2017/12/15 16:56
     * @Copyright 重庆平讯数据
     */
    public String unifiedorder(Long userId,Long orderId, String feeCode) throws Exception {
        if(ObjectHelper.isEmpty(userId))throw new BusinessException("PX001","用户ID为空");
        //查找app配置信息
        ApplicationInfo applicationInfo = this.applicationInfoDomainService.getApplicationInfo();
        //查找费用项
        Price price = this.priceDomainService.findByFeeCode(feeCode);
        //查找订单
        Order order = orderDomainService.findById(orderId);
        //判断支付宝客户端是否实例化，并保持单例
        if (ObjectHelper.isEmpty(alipayClient)) {
            alipayClient = new DefaultAlipayClient(applicationInfo.getAlipayGateway(), applicationInfo.getAlipayAppId(), applicationInfo.getAlipayUsPrivateKey(), "json", "UTF-8", applicationInfo.getAlipayPublicKey(),"RSA2");
        }
        /**
         * 生成支付信息
         */
        //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        //SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setBody(ObjectHelper.isNotEmpty(order.getOrderDescribe()) ? order.getOrderDescribe() : "支付宝");
        model.setSubject(ObjectHelper.isNotEmpty(price.getFeeItem()) ? price.getFeeItem() : "支付宝");
        //限定订单有效期未30分钟
        model.setTimeoutExpress("30m");
        model.setTotalAmount(price.getPrice().setScale(2) + "");
        model.setProductCode("QUICK_MSECURITY_PAY");
        model.setPassbackParams(feeCode);
        model.setOutTradeNo(feeCode + new Date().getTime() + WXPayUtil.MD5(WXPayUtil.generateNonceStr()));//我方支付编号
        //核对订单支付表数据
        OrderPay orderPay = this.findOrSaveOrderPay(userId,model, order, feeCode,price);
        if (orderPay.getIsPay()) {
            throw new BusinessException("PX004", "此费用已支付");
        }
        //修改订单数据
        order.setPayNo(orderPay.getPayNo());
        this.orderDomainService.update(order.to(OrderDto.class));
        //这里和普通的接口调用不同，使用的是sdkExecute
        request.setBizModel(model);
        //设置交易后的回调地址
        request.setNotifyUrl(applicationInfo.getAlipayNotifyUrl());
        AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
        System.out.println(response.getBody());
        return response.getBody();//就是orderString 可以直接给客户端请求，无需再做处理。
    }


    public String responseAliPayNotify(Map<String,String> paraMap) throws Exception {
        ApplicationInfo applicationInfo = applicationInfoDomainService.getApplicationInfo();
        //对返回结果进行验签

        boolean signVerified = AlipaySignature.rsaCheckV1(paraMap, applicationInfo.getAlipayPublicKey(), "UTF-8","RSA2"); //调用SDK验证签名
        if(!signVerified)return "FALSE";
        //定义失败的返回数据，以让支付宝服务器持续回调此函数
        AliPayResultDto paramse= BeanToMapUtil.toBean(AliPayResultDto.class,paraMap);
        String returnData = "FALSE";
        String tradeResult = paramse.getTrade_status();
        if (ObjectHelper.isEmpty(tradeResult)) return "FALSE";
        //判断交易是否成功
        boolean tradeIsSuccess = tradeResult.equalsIgnoreCase("TRADE_FINISHED") || tradeResult.equalsIgnoreCase("TRADE_SUCCESS");
        if (!tradeIsSuccess) return "FALSE";
        //如果数据是来自支付宝
        if(signVerified){
            //验证数据
            OrderPay orderPay=this.orderPayDomainService.findByPayNo(paramse.getOut_trade_no());
            //比较金额
            boolean amountConpare=orderPay.getPayFee().compareTo(new BigDecimal(paramse.getTotal_amount()))==0;
            boolean appidConpare=applicationInfo.getAlipayAppId().equalsIgnoreCase(paramse.getApp_id());
            //如果数据校验成功
            if(amountConpare&&appidConpare){
                //获取支付宝支付号
                String backSerialNo = paramse.getTrade_no();
                PayResult payResult = payResultDomainService.findByBackSeriNoAndPayType(backSerialNo, ENUM_PAY_TYPE.ALIPAY.value);
                //如果数据库中已经存在此支付返回数据则告诉微信服务器成功接收数据，不用再回调此函数
                if (ObjectHelper.isNotEmpty(payResult) && payResult.getPayStatus()&&orderPay.getIsPay()) {
                    //向微信支付结果表写入数据
                    paramse.setResultId(payResult.getId());
                    alipayResultDomainService.createData(paramse);
                    return "SUCCESS";
                }
                PayResultDto payResultSaveData = new PayResultDto();
                payResultSaveData.setBackSerialNo(backSerialNo);
                payResultSaveData.setPayDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(paramse.getGmt_payment()));
                payResultSaveData.setPayFee(new BigDecimal(paramse.getTotal_amount()));
                payResultSaveData.setPayNo(paramse.getOut_trade_no());
                payResultSaveData.setPayStatus(tradeIsSuccess);
                payResultSaveData.setPayType(ENUM_PAY_TYPE.ALIPAY.value);
                PayResult savedPayResult = payResultDomainService.create(payResultSaveData);
                //修改用户数据
                updateUserInfo(orderPay.getUserId(),orderPay,tradeIsSuccess);
                if (ObjectHelper.isNotEmpty(savedPayResult)) {
                    //更新支付表,改为已支付
                    orderPay.setIsPay(true);
                    orderPay.setBackSerialNo(paramse.getTrade_no());
                    this.orderPayDomainService.update(orderPay.to(OrderPayDto.class));
                    //向微信支付结果表写入数据
                    paramse.setResultId(savedPayResult.getId());
                    alipayResultDomainService.createData(paramse);
                    returnData = "SUCCESS";
                }
            }
        }else{
            returnData="FALSE";
        }
        return returnData;
    }

    public CwUserInfoDto updateUserInfo(Long userId, OrderPay orderPay, boolean isPay){
        CwUserInfoDto cwUserInfoDto=this.cwUserInfoAppService.findAuthInfoById(userId);
        if(orderPay.getIsPay())return cwUserInfoDto;
        Price price=this.priceDomainService.findByFeeCode(orderPay.getFeeCode());
        //如果此字段不为空则为会员充值，要加上对应充值时间
        String rightTime=price.getRightAddTime();
        Date newEffDate=null;
        if(ObjectHelper.isNotEmpty(rightTime)){
            //单位
            String timeUnit=rightTime.substring(0,1);
            //数量
            String addNum=rightTime.substring(1);
            if(ObjectHelper.isNotEmpty(timeUnit)&& ObjectHelper.isNotEmpty(addNum)){
                //如果用户会员有效期在当前日期之后
                if(ObjectHelper.isNotEmpty(cwUserInfoDto.getEffDate())&&cwUserInfoDto.getEffDate().after(Utils.getCurrentDayDate())){
                    newEffDate= Utils.addDate(cwUserInfoDto.getEffDate(),timeUnit,Integer.parseInt(addNum));
                }else{
                    newEffDate= Utils.addDate(new Date(),timeUnit,Integer.parseInt(addNum));
                }
            }
        }
        //修改用户数据
        cwUserInfoDto.setIsPay(isPay);
        if(ObjectHelper.isNotEmpty(newEffDate)&&isPay)cwUserInfoDto.setEffDate(newEffDate);
        return this.cwUserInfoAppService.updateUser(cwUserInfoDto).to(CwUserInfoDto.class);
    }

    /**
     * @Author: Away
     * @Description: 核对订单支付数据
     * @Param: orderDto 查找的订单
     * @Return com.pingxun.biz.order.app.dto.OrderPayDto
     * @Date 2017/12/14 17:45
     * @Copyright 重庆平讯数据
     */
    private OrderPay findOrSaveOrderPay(Long userId,AlipayTradeAppPayModel alipayTradeAppPayModel, Order order, String feeCode,Price price) throws BusinessException {
        OrderPay orderPay=null;
        if(!ObjectHelper.isNotEmpty(price.getRightAddTime())){//如果不为会员充值
            orderPay = this.orderPayDomainService.findByUserIdAndFeeCode(userId, feeCode);
        }
        //如果有此费用项数据并且为未支付状态
        if (ObjectHelper.isNotEmpty(orderPay) && !orderPay.getIsPay()) {
            orderPay.setOrderDate(order.getOrderDate());
            orderPay.setPayDate(new Date());
            orderPay.setOrderFee(order.getOrderFee());
            orderPay.setPayNo(alipayTradeAppPayModel.getOutTradeNo());//极其重要
            orderPay.setPayFee(new BigDecimal(alipayTradeAppPayModel.getTotalAmount()));
            orderPay.setPayType(ENUM_PAY_TYPE.ALIPAY.value);
            orderPay = this.orderPayDomainService.update(orderPay.to(OrderPayDto.class));
            return orderPay;
        }//没有此项费用记录
        else if (ObjectHelper.isEmpty(orderPay)) {
            OrderPayDto orderPayDto = new OrderPayDto();
            orderPayDto.setUserId(userId);
            orderPayDto.setFeeItem(alipayTradeAppPayModel.getSubject());
            orderPayDto.setFeeCode(alipayTradeAppPayModel.getPassbackParams());
            orderPayDto.setIsPay(false);
            orderPayDto.setPayDate(new Date());
            orderPayDto.setOrderDate(order.getOrderDate());
            orderPayDto.setOrderFee(order.getOrderFee());
            orderPayDto.setPayNo(alipayTradeAppPayModel.getOutTradeNo());
            orderPayDto.setPayFee(new BigDecimal(alipayTradeAppPayModel.getTotalAmount()).setScale(2));
            orderPayDto.setPayType(ENUM_PAY_TYPE.ALIPAY.value);
            return this.orderPayDomainService.create(orderPayDto);
        } else {
            return orderPay;
        }
    }

    public CwUserInfoDto queryTradeResult(PayParamDto payParamDto) throws Exception{
        if(ObjectHelper.isEmpty(payParamDto.getUserId()))throw new BusinessException("PX001","用户ID为空");
        //查找app配置信息
        ApplicationInfo applicationInfo = this.applicationInfoDomainService.getApplicationInfo();
        /**验签*/
        //获取原始字符窜
        String orignalParam= JSONObject.toJSONString(payParamDto.getAlipay_trade_app_pay_response());
        //获取签名类型
        String signType=payParamDto.getSign_type();
        //获取签名字符串
        String signCode=payParamDto.getSign();
        boolean checkSign=AlipaySignature.rsaCheck(orignalParam,signCode,applicationInfo.getAlipayPublicKey(),"UTF-8",signType);
//        if(!checkSign)throw new BusinessException("PX004","验签失败");
        //查找支付结果表
        PayResult payResult=this.payResultDomainService.findByPayNo(payParamDto.getAlipay_trade_app_pay_response().getOut_trade_no());
        //查找订单支付表
        OrderPay orderPay=this.orderPayDomainService.findByPayNo(payParamDto.getAlipay_trade_app_pay_response().getOut_trade_no());
        if(!orderPay.getUserId().toString().equalsIgnoreCase(payParamDto.getUserId().toString()))throw new BusinessException("PX004","订单异常，付款人与下单人不一致");
        //如果此订单已经支付则返回
        if(orderPay.getIsPay())return this.cwUserInfoAppService.findAuthInfoById(payParamDto.getUserId());
        //判断支付宝客户端是否实例化，并保持单例
        if (ObjectHelper.isEmpty(alipayClient)) {
            alipayClient = new DefaultAlipayClient(applicationInfo.getAlipayGateway(), applicationInfo.getAlipayUsPrivateKey(), "json", "UTF-8", applicationInfo.getAlipayPublicKey(), "RSA2");
        }
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        request.setBizContent("{\"out_trade_no\":\""+payParamDto.getAlipay_trade_app_pay_response().getOut_trade_no()+"\",\"trade_no\":\""+payParamDto.getAlipay_trade_app_pay_response().getTrade_no()+"\"" +"}");
        AlipayTradeQueryResponse response = alipayClient.execute(request);
        boolean tradeSuccess=response.getTradeStatus().equalsIgnoreCase("TRADE_SUCCESS")||response.getTradeStatus().equalsIgnoreCase("TRADE_FINISHED");
        if(ObjectHelper.isEmpty(payResult))payResult=new PayResult();
        //根据查询到的结果修改相应的数据
        payResult.setPayStatus(tradeSuccess);
        payResult.setPayDate(response.getSendPayDate());
        payResult.setBackSerialNo(response.getTradeNo());
        payResult.setPayFee(new BigDecimal(response.getBuyerPayAmount()).setScale(2));
        payResult.setPayNo(response.getOutTradeNo());
        payResult.setPayType(ENUM_PAY_TYPE.ALIPAY.value);
        this.payResultDomainService.saveOrUpdate(payResult.to(PayResultDto.class));

        //更新用户信息
        CwUserInfoDto cwUserInfoDto=updateUserInfo(payParamDto.getUserId(),orderPay,tradeSuccess);
        //更新订单支付表
        orderPay.setIsPay(tradeSuccess);
        orderPay.setBackSerialNo(response.getTradeNo());
        this.orderPayDomainService.update(orderPay.to(OrderPayDto.class));
        return cwUserInfoDto;
    }


}

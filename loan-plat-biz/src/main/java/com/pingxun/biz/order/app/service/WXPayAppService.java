//package com.pingxun.biz.order.app.service;
//
//import com.pingxun.biz.order.app.dto.PayParamDto;
//import com.pingxun.biz.order.domain.service.WXPayDomainService;
//import com.pingxun.biz.user.app.dto.CwUserInfoDto;
//import com.pingxun.core.common.util.ObjectHelper;
//import com.zds.common.lang.exception.BusinessException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//public class WXPayAppService {
//
//    @Autowired
//    private WXPayDomainService wxPayDomainService;
//
//    public String responseWechatPayNotify(String paramse) {
//        return wxPayDomainService.responseWechatPayNotify(paramse);
//    }
//
//    /**
//     * @Author: Away
//     * @Description: 生成预支付交易单
//     * @Return java.lang.String
//     * @Date 2017/12/14 15:03
//     * @Copyright 重庆平讯数据
//     */
//    public String unifiedorder(Long userId,String createIp,String feeCode,Long orderId) throws Exception {
//        return this.wxPayDomainService.unifiedorder(userId,createIp, feeCode, orderId);
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
//    public CwUserInfoDto orderQuery(PayParamDto payParamDto) throws Exception{
//        if(ObjectHelper.isNotEmpty(payParamDto)&& ObjectHelper.isNotEmpty(payParamDto.getUserId())
//                && ObjectHelper.isNotEmpty(payParamDto.getTransaction_id())&& ObjectHelper.isNotEmpty(payParamDto.getOut_trade_no())){
//            return this.wxPayDomainService.orderQuery(payParamDto.getUserId(),payParamDto.getTransaction_id(),payParamDto.getOut_trade_no());
//        }else{
//            throw new BusinessException("PX001","参数错误");
//        }
//    }
//
//}

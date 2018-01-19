package com.pingxun.biz.order.app.service;

import com.pingxun.biz.order.app.dto.PayParamDto;
import com.pingxun.biz.order.domain.service.AlipayDomainService;
import com.pingxun.biz.user.app.dto.CwUserInfoDto;
import com.pingxun.core.common.util.ObjectHelper;
import com.zds.common.lang.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Map;

/**
* @Title: AliPayAppService.java
* @Description:  支付宝service
* @author Away
* @date 2017/12/15 19:51
* @copyright 重庆平讯数据
* @version V1.0
*/
@Service
@Transactional
public class AliPayAppService {

    @Autowired
    private AlipayDomainService alipayDomainService;

    /**
     * @Author: Away
     * @Description: 生成预付单
     * @Param: orderId
     * @Param: feeCode
     * @Return java.lang.String
     * @Date 2017/12/15 19:54
     * @Copyright 重庆平讯数据
     */
    public String unifiedorder(Long userId,Long orderId,String feeCode) throws Exception{
        return alipayDomainService.unifiedorder(userId,orderId, feeCode);
    }

    /**
     * @Author: Away
     * @Description: 支付宝回调
     * @Param: paramse
     * @Return java.lang.String
     * @Date 2017/12/15 22:12
     * @Copyright 重庆平讯数据
     */
    public String responseAliPayNotify(Map<String,String> paraMap) throws Exception{
            return this.alipayDomainService.responseAliPayNotify(paraMap);
    }

    /**
     * @Author: Away
     * @Description: 支付宝查询交易结果
     * @Param: out_trade_no
     * @Param: trade_no
     * @Return com.alipay.api.response.AlipayTradeQueryResponse
     * @Date 2017/12/16 13:37
     * @Copyright 重庆平讯数据
     */
    public CwUserInfoDto queryTradeResult(PayParamDto payParamDto) throws Exception{
        if(ObjectHelper.isNotEmpty(payParamDto)&& ObjectHelper.isNotEmpty(payParamDto.getUserId())&& ObjectHelper.isNotEmpty(payParamDto.getSign())&&
                ObjectHelper.isNotEmpty(payParamDto.getAlipay_trade_app_pay_response())){
            return this.alipayDomainService.queryTradeResult(payParamDto);
        }else{
            throw new BusinessException("PX004","查询支付宝支付结果出错，参数错误");
        }
    }
}

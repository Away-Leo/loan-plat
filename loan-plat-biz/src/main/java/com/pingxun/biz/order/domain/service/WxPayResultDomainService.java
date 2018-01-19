//package com.pingxun.biz.order.domain.service;
//
//import com.pingxun.biz.order.app.dto.WxPayResultDto;
//import com.pingxun.biz.order.domain.entity.WxPayResult;
//import com.pingxun.biz.order.domain.repository.WxPayResultRepository;
//import com.pingxun.biz.order.sdk.wxsdk.WXPayUtil;
//import com.pingxun.core.common.util.BeanToMapUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import javax.transaction.Transactional;
//import java.util.Map;
//
///**
//* @Title: WxPayResultDomainService.java
//* @Description: 微信支付结果
//* @author Away
//* @date 2017/12/14 16:01
//* @copyright 重庆平讯数据
//* @version V1.0
//*/
//@Service
//public class WxPayResultDomainService {
//
//    @Autowired
//    private WxPayResultRepository wxPayResultRepository;
//
//    /**
//     * @Author: Away
//     * @Description: 保存
//     * @Param: wxPayResultDto
//     * @Return com.pingxun.biz.order.domain.entity.WxPayResult
//     * @Date 2017/12/14 16:08
//     * @Copyright 重庆平讯数据
//     */
//    @Transactional
//    public WxPayResult creat(WxPayResultDto wxPayResultDto){
//        WxPayResult wxPayResult=new WxPayResult();
//        wxPayResult.from(wxPayResultDto);
//        return wxPayResultRepository.save(wxPayResult);
//    }
//
//    @Transactional(rollbackOn = Exception.class)
//    public WxPayResultDto saveWithMap(Map<String,String> datas){
//        try{
//            return this.creat(BeanToMapUtil.toBean(WxPayResultDto.class,datas)).to(WxPayResultDto.class);
//        }catch (Exception e){
//            WXPayUtil.getLogger().error("PX000","保存微信支付结果数据出错",e);
//            return null;
//        }
//    }
//}

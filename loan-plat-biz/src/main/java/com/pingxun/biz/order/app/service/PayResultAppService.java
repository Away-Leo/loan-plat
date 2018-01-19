package com.pingxun.biz.order.app.service;

import com.pingxun.biz.order.app.dto.PayResultDto;
import com.pingxun.biz.order.domain.entity.PayResult;
import com.pingxun.biz.order.domain.service.PayResultDomainService;
import com.pingxun.core.common.util.ObjectHelper;
import com.zds.common.lang.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * 订单支付服务
 * Created by dujy on 2017-12-07.
 */
@Transactional
@Service
public class PayResultAppService {

    @Autowired
    private PayResultDomainService domainService;

    /**
     * 新增订单支付
     * @param payResultDto
     * @return
     */
    public Long create(PayResultDto payResultDto){
        return domainService.create(payResultDto).getId();
    }

    public PayResultDto updateData(PayResultDto payResultDto) throws Exception{
        PayResult payResult=this.domainService.updateData(payResultDto);
        if(ObjectHelper.isNotEmpty(payResult)){
            return payResult.to(PayResultDto.class);
        }else{
            throw new BusinessException("PX002","更新支付结果，更新对象为空");
        }
    }


    /**
     * @Author: Away
     * @Description: 按照支付类型和返回流水号查找
     * @Param: backSeri
     * @Param: type
     * @Return com.pingxun.biz.order.domain.entity.PayResult
     * @Date 2017/12/14 13:55
     * @Copyright 重庆平讯数据
     */
    public PayResultDto findByBackSeriNoAndPayType(String backSeri, String type){
        if(ObjectHelper.isNotEmpty(backSeri)&& ObjectHelper.isNotEmpty(type)){
            PayResult sourceData=domainService.findByBackSeriNoAndPayType(backSeri, type);
            if(ObjectHelper.isNotEmpty(sourceData)){
                return sourceData.to(PayResultDto.class);
            }else{
                return null;
            }
        }else{
            throw new BusinessException("PX001","按照支付类型和返回流水号查找支付结果数据，参数为空");
        }
    }

    /**
     * @Author: Away
     * @Description: 按照返回支付号和支付编码查找
     * @Param: backSerialNo
     * @Param: payNo
     * @Return com.pingxun.biz.order.domain.entity.PayResult
     * @Date 2017/12/14 20:33
     * @Copyright 重庆平讯数据
     */
    public PayResultDto findByBackSerialNoAndPayNo(String backSerialNo,String payNo) throws Exception{
        if(ObjectHelper.isNotEmpty(backSerialNo)&& ObjectHelper.isNotEmpty(payNo)){
            PayResult payResult=this.domainService.findByBackSerialNoAndPayNo(backSerialNo, payNo);
            if(ObjectHelper.isNotEmpty(payResult)){
                return payResult.to(PayResultDto.class);
            }else{
                return null;
            }
        }else{
            throw new BusinessException("PX001","按照返回支付号和支付编码查找支付结果参数为空");
        }
    }

}

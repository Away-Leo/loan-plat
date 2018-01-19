package com.pingxun.biz.order.domain.service;

import com.pingxun.biz.order.app.dto.PayResultDto;
import com.pingxun.biz.order.domain.entity.PayResult;
import com.pingxun.biz.order.domain.repository.PayResultRepository;
import com.pingxun.core.common.util.ObjectHelper;
import com.zds.common.lang.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 订单支付服务
 * Created by dujy on 2017-05-20.
 */
@Service
public class PayResultDomainService {

    @Autowired
    private PayResultRepository payResultRepository;

    /**
     * 支付结果
     * @param payResultDto
     * @return
     */
    public PayResult create(PayResultDto payResultDto){
        PayResult payResult = new PayResult();
        payResult.from(payResultDto);

        return payResultRepository.save(payResult);
    }

    public PayResult updateData(PayResultDto payResultDto){
        PayResult payResult=payResultRepository.findOne(payResultDto.getId());
        payResult.from(payResultDto);
        return payResult;
    }

    public PayResult saveOrUpdate(PayResultDto payResultDto){
        if(ObjectHelper.isNotEmpty(payResultDto.getId())){
            PayResult payResult=payResultRepository.findOne(payResultDto.getId());
            payResult.from(payResultDto);
            return payResult;
        }else{
            return this.create(payResultDto);
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
    public PayResult findByBackSeriNoAndPayType(String backSeri,String type){
        return payResultRepository.findByBackSerialNoAndPayType(backSeri, type);
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
    public PayResult findByBackSerialNoAndPayNo(String backSerialNo,String payNo){
        return payResultRepository.findByBackSerialNoAndPayNo(backSerialNo, payNo);
    }

    /**
     * @Author: Away
     * @Description: 按照支付号查找
     * @Param: payNo
     * @Return com.pingxun.biz.order.domain.entity.PayResult
     * @Date 2017/12/16 13:22
     * @Copyright 重庆平讯数据
     */
    public PayResult findByPayNo(String payNo) throws BusinessException{
        PayResult returnData =payResultRepository.findByPayNo(payNo);
        if(ObjectHelper.isNotEmpty(returnData)){
            return returnData;
        }else{
            return null;
        }
    }

}

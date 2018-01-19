package com.pingxun.biz.order.domain.service;

import com.pingxun.biz.order.app.dto.AliPayResultDto;
import com.pingxun.biz.order.domain.entity.AliPayResult;
import com.pingxun.biz.order.domain.repository.AlipayResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @Title: AlipayResultDomainService.java
* @Description:  支付宝支付结果service
* @author Away
* @date 2017/12/15 20:47
* @copyright 重庆平讯数据
* @version V1.0
*/
@Service
public class AlipayResultDomainService {

    @Autowired
    private AlipayResultRepository alipayResultRepository;

    /**
     * @Title: AlipayResultDomainService.java
     * @Description: 保存数据
     * @author Away
     * @date 2017/12/15 20:49
     * @copyright 重庆平讯数据
     * @version V1.0
     */
    public AliPayResult createData(AliPayResultDto aliPayResult){
        AliPayResult payResult=new AliPayResult();
        payResult.from(aliPayResult);
        return alipayResultRepository.save(payResult);
    }



}

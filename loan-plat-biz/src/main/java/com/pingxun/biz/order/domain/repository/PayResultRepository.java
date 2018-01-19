package com.pingxun.biz.order.domain.repository;

import com.pingxun.biz.order.domain.entity.PayResult;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 支付结果
 * Created by dujy on 2017-05-20.
 */
public interface PayResultRepository extends JpaRepository<PayResult,Long> {

    /**
     * @Author: Away
     * @Description： 按照支付类型和流水号查找
     * @Param: backSerialNo 返回流水号
     * @Param: payType 支付类型
     * @Return com.pingxun.biz.order.domain.entity.PayResult
     * @Date 2017/12/14 13:52
     * @Copyright 重庆平讯数据
     */
    public PayResult findByBackSerialNoAndPayType(String backSerialNo, String payType);

    /**
     * @Author: Away
     * @Description: 按照返回支付号和支付编码查找
     * @Param: backSerialNo
     * @Param: payNo
     * @Return com.pingxun.biz.order.domain.entity.PayResult
     * @Date 2017/12/14 20:33
     * @Copyright 重庆平讯数据
     */
    public PayResult findByBackSerialNoAndPayNo(String backSerialNo, String payNo);

    /**
     * @Author: Away
     * @Description: 按照我方交易号查找
     * @Param: payNo
     * @Return com.pingxun.biz.order.domain.entity.PayResult
     * @Date 2017/12/16 13:21
     * @Copyright 重庆平讯数据
     */
    public PayResult findByPayNo(String payNo);
}

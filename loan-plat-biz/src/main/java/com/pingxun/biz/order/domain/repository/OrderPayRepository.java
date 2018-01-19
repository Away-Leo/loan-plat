package com.pingxun.biz.order.domain.repository;

import com.pingxun.biz.order.domain.entity.OrderPay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 订单支付
 * Created by dujy on 2017-05-20.
 */
public interface OrderPayRepository extends JpaRepository<OrderPay,Long>,JpaSpecificationExecutor<OrderPay> {

    /**
     * @Author: Away
     * @Description: 按照签名码查找数据
     * @Param: signCode
     * @Return com.pingxun.biz.order.domain.entity.OrderPay
     * @Date 2017/12/14 10:56
     * @Copyright 重庆平讯数据
     */
    public OrderPay findBySignCode(String signCode);

    /**
     * @Author: Away
     * @Description： 按照费用项和用户查找数据
     * @Param: userId 用户ID
     * @Param: feeCode 费用项
     * @Return com.pingxun.biz.order.domain.entity.OrderPay
     * @Date 2017/12/14 18:04
     * @Copyright 重庆平讯数据
     */
    public OrderPay findByUserIdAndFeeCode(Long userId, String feeCode);

    /**
     * @Author: Away
     * @Description: 按照返回的支付号和支付编码查找订单支付信息
     * @Param: backSerialNo
     * @Param: payNo
     * @Return com.pingxun.biz.order.domain.entity.OrderPay
     * @Date 2017/12/14 20:44
     * @Copyright 重庆平讯数据
     */
    public OrderPay findByBackSerialNoAndPayNo(String backSerialNo, String payNo);

    /**
     * @Author: Away
     * @Description： 按照支付订单号查找支付订单
     * @Param: payNo 系统支付订单号
     * @Return com.pingxun.biz.order.domain.entity.OrderPay
     * @Date 2017/12/15 21:44
     * @Copyright 重庆平讯数据
     */
    public OrderPay findByPayNo(String payNo);
}

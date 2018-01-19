package com.pingxun.biz.order.domain.repository;

import com.pingxun.biz.order.app.dto.OrderPayDto;
import com.pingxun.biz.order.domain.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * 订单
 * Created by dujy on 2017-05-20.
 */
public interface OrderRepository extends JpaRepository<Order,Long>,JpaSpecificationExecutor<Order> {
    //修改订单支付编号
    @Query(value = "update loan_order set status=1,pay_date=now(),pay_serial_no=:#{#orderDto.paySerialNo} " +
            " where order_no=:#{#orderDto.orderNo}", nativeQuery = true)
    int updateOrder(@Param("orderDto") OrderPayDto orderDto);

    //修改订单支付结果信息
    @Query(value = "update loan_order set status=1,pay_date=now(),pay_serial_no=:#{#orderDto.paySerialNo}," +
            " back_serial_no=:#{#orderDto.backSerialNo} " +
            " where order_no=:#{#orderDto.orderNo} ", nativeQuery = true)
    int updateOrderPay(@Param("orderDto") OrderPayDto orderDto);
}

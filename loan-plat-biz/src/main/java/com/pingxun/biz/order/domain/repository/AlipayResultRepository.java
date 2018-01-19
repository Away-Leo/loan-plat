package com.pingxun.biz.order.domain.repository;

import com.pingxun.biz.order.domain.entity.AliPayResult;
import org.springframework.data.jpa.repository.JpaRepository;

/**
* @Title: AlipayResultRepository.java
* @Description:  支付宝返回数据
* @author Away
* @date 2017/12/15 20:43
* @copyright 重庆平讯数据
* @version V1.0
*/
public interface AlipayResultRepository extends JpaRepository<AliPayResult,Long>{

}

package com.pingxun.biz.order.domain.repository;


import com.pingxun.biz.order.domain.entity.WxPayResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WxPayResultRepository extends JpaRepository<WxPayResult,Long> {

}

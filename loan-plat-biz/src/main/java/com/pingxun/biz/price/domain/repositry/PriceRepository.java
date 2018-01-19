package com.pingxun.biz.price.domain.repositry;


import com.pingxun.biz.price.domain.entity.Price;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 会员价格
 * Created by dujy on 2017-05-20.
 */
public interface PriceRepository extends JpaRepository<Price,Long> {

    List<Price> findByUserType(Integer userType);

    /**
     * @Author: Away
     * @Description: 按照费用项编码查找费用项
     * @Param: feeCode
     * @Return com.pingxun.biz.price.domain.entity.Price
     * @Date 2017/12/14 15:31
     * @Copyright 重庆平讯数据
     */
    Price findByFeeCode(String feeCode);

}

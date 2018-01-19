package com.pingxun.biz.price.domain.service;


import com.pingxun.biz.price.app.dto.PriceDto;
import com.pingxun.biz.price.domain.entity.Price;
import com.pingxun.biz.price.domain.repositry.PriceRepository;
import com.pingxun.core.common.util.ObjectHelper;
import com.zds.common.lang.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 价格服务
 * Created by dujy on 2017-05-20.
 */
@Service
public class PriceDomainService {

    @Autowired
    private PriceRepository repository;

    /**
     * 价格列表
     * @return
     */
    public List<Price> getPriceList(){
        List<Price> priceList = repository.findAll();
        return priceList;
    }

    /**
     * 查询会员价格
     * @param userType
     * @return
     */
    public List<PriceDto> findByUserType(Integer userType){
        List<Price> priceList =repository.findByUserType(userType);
        List<PriceDto> priceDtoList = new ArrayList<>();
        for(Price price:priceList){
            priceDtoList.add(price.to(PriceDto.class));
        }
        return priceDtoList;
    }

    /**
     * @Author: Away
     * @Description: 按照费用编码查找费用项
     * @Param: feeCode
     * @Return com.pingxun.biz.price.domain.entity.Price
     * @Date 2017/12/14 15:33
     * @Copyright 重庆平讯数据
     */
    public Price findByFeeCode(String feeCode){
        if(ObjectHelper.isNotEmpty(feeCode)){
            return repository.findByFeeCode(feeCode);
        }else{
            throw new BusinessException("PX001","按照费用项编码查找费用项出错，费用项编码为空");
        }
    }
}

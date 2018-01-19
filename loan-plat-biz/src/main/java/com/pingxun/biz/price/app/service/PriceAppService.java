package com.pingxun.biz.price.app.service;


import com.pingxun.biz.CwException;
import com.pingxun.biz.price.app.dto.PriceDto;
import com.pingxun.biz.price.domain.entity.Price;
import com.pingxun.biz.price.domain.service.PriceDomainService;
import com.pingxun.core.common.util.ObjectHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * 会员认证价格服务
 * Created by dujy on 2017-12-11.
 */
@Transactional
@Service
public class PriceAppService {

    @Autowired
    private PriceDomainService priceDomainService;

    /**
     * 查询价格列表
     * @return
     */
    public List<PriceDto> getPriceList() {

        List<PriceDto> priceDtoList = new ArrayList<>();
        List<Price> priceList = priceDomainService.getPriceList();
        for(Price price:priceList){
            priceDtoList.add(price.to(PriceDto.class));
        }
        return priceDtoList;
    }

    /**
     * 根据会员类型查询认证费用
     * @param userType
     * @return
     */
    public List<PriceDto> findByUserType(Integer userType) {
        return priceDomainService.findByUserType(userType);
    }

    /**
     * @Author: Away
     * @Description: 按照费用项编码查找费用项出错
     * @Param: feeCode
     * @Return com.pingxun.biz.price.app.dto.PriceDto
     * @Date 2017/12/14 15:36
     * @Copyright 重庆平讯数据
     */
    public PriceDto findByFeeCode(String feeCode){
        Price sourceData=this.priceDomainService.findByFeeCode(feeCode);
        if(ObjectHelper.isNotEmpty(sourceData)){
            return sourceData.to(PriceDto.class);
        }else{
            CwException.throwIt("按照费用项编码查找费用项出错，无此结果");
            return null;
        }
    }

}

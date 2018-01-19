package com.pingxun.biz.product.domain.service;

import com.pingxun.biz.product.domain.entity.ProductVersion;
import com.pingxun.biz.product.domain.repository.ProductVersionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 产品服务
 * Created by dujy on 2017-05-20.
 */
@Service
public class ProductVersionDomainService {
    public static Logger logger = LoggerFactory.getLogger(ProductVersionDomainService.class);

    @Autowired
    private ProductVersionRepository productVersionRepository;

    /**
     * 查询数据更新版本
     * @return
     */
    public ProductVersion findProductVersion(String channelNo){
        ProductVersion productVersion = productVersionRepository.findByChannelNo(channelNo);
        if(productVersion==null)
        {
            return null;
        }
        return productVersion;
    }

}

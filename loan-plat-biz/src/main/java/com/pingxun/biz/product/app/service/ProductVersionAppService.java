package com.pingxun.biz.product.app.service;

import com.pingxun.biz.product.app.dto.ProductVersionDto;
import com.pingxun.biz.product.domain.entity.ProductVersion;
import com.pingxun.biz.product.domain.service.ProductVersionDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;

/**
 * Created by dujy on 2017-05-20.
 */
@Transactional
@Service
public class ProductVersionAppService {

    @Autowired
    private ProductVersionDomainService productVersionDomainService;


    /**
     * 查询产品数据是否有更新
     * @return
     */
    public ProductVersionDto findProductVersion(String channelNo)
    {
        ProductVersionDto productVersionDto = new ProductVersionDto();
        ProductVersion productVersion = productVersionDomainService.findProductVersion(channelNo);
        if(!Objects.isNull(productVersion))
        {
            productVersionDto = productVersion.to(ProductVersionDto.class);
        }

        return productVersionDto;
    }

}

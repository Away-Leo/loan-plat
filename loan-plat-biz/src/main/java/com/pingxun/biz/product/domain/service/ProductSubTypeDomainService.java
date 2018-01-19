package com.pingxun.biz.product.domain.service;

import com.pingxun.biz.CwException;
import com.pingxun.biz.product.app.dto.ProductSubTypeDto;
import com.pingxun.biz.product.domain.entity.ProductSubType;
import com.pingxun.biz.product.domain.repository.ProductSubTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 产品分类服务
 * Created by dujy on 2017-05-20.
 */
@Service
public class ProductSubTypeDomainService {

    @Autowired
    private ProductSubTypeRepository repository;
    /**
     * 新增产品
     * @param productDto
     * @return
     */
    public ProductSubType create(ProductSubTypeDto productDto)
    {
        ProductSubType product = new ProductSubType();
        product.from(productDto);
        return repository.save(product);
    }

    /**
     * 修改产品
     * @param productSubTypeDto
     * @return
     */
    public ProductSubType update(ProductSubTypeDto productSubTypeDto)
    {
        ProductSubType product = repository.findOne(productSubTypeDto.getId());
        if(product == null)
        {
            CwException.throwIt("产品不存在");
        }
        product.from(productSubTypeDto);
        repository.save(product);
        return product;
    }

    /**
     * 查询产品详情
     * @param id
     * @return
     */
    public ProductSubType findById(Long id)
    {
        ProductSubType product = repository.findOne(id);
        return product;
    }


    /**
     * 查询所有产品分类
     * @return
     */
    public List<ProductSubType> findAll(String appName) {

        return repository.findByAppName(appName);
    }

}

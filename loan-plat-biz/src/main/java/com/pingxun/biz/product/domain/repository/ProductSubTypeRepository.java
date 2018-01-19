package com.pingxun.biz.product.domain.repository;

import com.pingxun.biz.product.domain.entity.ProductSubType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 产品分类
 * Created by dujy on 2017-05-20.
 */
public interface ProductSubTypeRepository extends JpaRepository<ProductSubType,Long>,JpaSpecificationExecutor<ProductSubType> {
    @Query(value = "select * from cw_product_sub_type where app_name=?1",nativeQuery = true)
    List<ProductSubType> findByAppName(String appName);
}

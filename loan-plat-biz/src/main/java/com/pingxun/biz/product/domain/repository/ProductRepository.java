package com.pingxun.biz.product.domain.repository;

import com.pingxun.biz.product.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 产品数据
 * Created by dujy on 2017-05-20.
 */
public interface ProductRepository extends JpaRepository<Product,Long>,JpaSpecificationExecutor<Product> {

}

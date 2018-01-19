package com.pingxun.biz.product.domain.repository;

import com.pingxun.biz.product.domain.entity.ProductVersion;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 产品数据版本
 * Created by dujy on 2017-05-20.
 */
public interface ProductVersionRepository extends JpaRepository<ProductVersion,Long>{

    ProductVersion findByChannelNo(String channelNo);
}

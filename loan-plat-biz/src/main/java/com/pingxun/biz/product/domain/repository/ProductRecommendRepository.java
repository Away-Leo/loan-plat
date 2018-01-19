package com.pingxun.biz.product.domain.repository;

import com.pingxun.biz.product.domain.entity.ProductRecommend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 产品推荐数据
 * Created by dujy on 2017-10-19.
 */
public interface ProductRecommendRepository extends JpaRepository<ProductRecommend,Long>,JpaSpecificationExecutor<ProductRecommend> {
}

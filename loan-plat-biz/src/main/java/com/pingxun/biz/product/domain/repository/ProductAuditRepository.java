package com.pingxun.biz.product.domain.repository;

import com.pingxun.biz.product.domain.entity.ProductAuditVersion;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 苹果本地数据版本
 * Created by dujy on 2017-05-20.
 */
public interface ProductAuditRepository extends JpaRepository<ProductAuditVersion,Long>{
    /**
     * 查询版本号
     * @param version
     * @return
     */
    ProductAuditVersion findByDataVersion(String version);
}

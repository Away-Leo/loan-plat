package com.pingxun.biz.banner.domain.repository;

import com.pingxun.biz.banner.domain.entity.Banner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 系统banner图片
 * Created by dujy on 2017-05-20.
 */
public interface BannerRepository extends JpaRepository<Banner,Long>,JpaSpecificationExecutor<Banner> {

    @Query(value = " select * from loan_banner where is_valid=1 and banner_position = 'common' " +
            " UNION all select * from loan_banner where is_valid=1 and banner_position = ?1  ORDER BY show_order asc ",nativeQuery = true)
    List<Banner> findByBannerPosition(String position);

    @Query(value = " select * from loan_banner where is_valid=0 and banner_position = 'common'  ORDER BY show_order asc ",nativeQuery = true)
    List<Banner> findByAuditBannerPosition();

    @Query(value = " select * from loan_banner where is_valid=1 and banner_position = ?1  ORDER BY show_order asc ",nativeQuery = true)
    List<Banner> findWxBanner(String position);

}

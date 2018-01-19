package com.pingxun.biz.product.domain.repository;

import com.pingxun.biz.product.domain.entity.AuditChannel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 审核渠道配置
 * Created by dujy on 2017-05-20.
 */
public interface AuditChannelRepository extends JpaRepository<AuditChannel,Long>{
    /**
     * 查询版本号
     * @param channelNo
     * @param appName
     * @return
     */
    @Query(value = "select * from cw_audit_channel where channel_no=?1 and app_name=?2 ",nativeQuery=true)
    List<AuditChannel> findAuditChannel(String channelNo,String appName);
}

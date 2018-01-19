package com.pingxun.biz.product.domain.entity;

import com.pingxun.biz.common.entity.AggEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * 金融产品审核版本号
 * Created by Administrator on 2017/11/14.
 */
@Entity
@Table(name="cw_audit_channel",indexes = {@Index(unique = true, name = "cw_audit_version_unique_idx", columnList = "channel_no,app_name")})
@Getter
@Setter
public class AuditChannel extends AggEntity{

    @Column(name="channel_no",columnDefinition="varchar(20) not null comment '审核渠道'")
    private String channelNo;

    @Column(name="app_name",columnDefinition="varchar(20) comment '所属APP'")
    private String appName;
}

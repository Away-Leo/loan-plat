package com.pingxun.biz.product.domain.entity;

import com.pingxun.biz.common.entity.AggEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**   
* @Title: ProductVersion.java
* @Description: 产品版本信息
* @author Away
* @date 2018/1/5 9:50 
* @copyright 重庆平讯数据
* @version V1.0   
*/
@Entity
@Table(name="loan_product_version")
@org.hibernate.annotations.Table(appliesTo = "loan_product_version",comment = "APP自动更新版本控制表")
@Getter
@Setter
public class ProductVersion extends AggEntity {

    @Column(name="data_version",columnDefinition="int(11) not null comment '产品数据更新版本'")
    private Integer dataVersion;

    @Column(name="channel_no",columnDefinition="varchar(50) not null comment '渠道编号'")
    private String channelNo;

    @Column(name="apk_url",columnDefinition="varchar(100) not null comment '更新apk下载url'")
    private String apkUrl;

    @Column(name="memo",columnDefinition="varchar(200) not null comment '更新日志'")
    private String memo;

    @Column(name="is_audit",columnDefinition="tinyint(1) comment '是否审核'")
    private Boolean isAudit= Boolean.FALSE;

}

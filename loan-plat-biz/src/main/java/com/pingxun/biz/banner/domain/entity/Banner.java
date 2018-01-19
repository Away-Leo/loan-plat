package com.pingxun.biz.banner.domain.entity;

import com.pingxun.biz.common.entity.AggEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 系统banner
 * Created by dujiangyu on 2017/6/21.
 */
@Entity
@Table(name="loan_banner")
@Getter
@Setter
public class Banner extends AggEntity {

    @Column(name="name",columnDefinition="varchar(100) not null comment 'banner描述'")
    private String name;

    @Column(name="banner_img",columnDefinition="varchar(200) not null comment '图片'")
    private String bannerImg;

    @Column(name="banner_detail_img",columnDefinition="varchar(200) comment '图片'")
    private String bannerDetailImg;

    @Column(name="banner_position",columnDefinition="varchar(200) not null comment 'banner展示位置'")
    private String bannerPosition;

    @Column(name="jump_url",columnDefinition="varchar(200) comment '跳转URL'")
    private String jumpUrl;

    @Column(name="show_order",columnDefinition="int(11) comment '排序'")
    private Integer showOrder=1;

    @Column(name="is_valid",columnDefinition="tinyint(1) not null comment '是否有效'")
    private Boolean isValid=Boolean.TRUE;

    @Column(name="is_android",columnDefinition="tinyint(1) not null comment '是否安卓投放'")
    private Boolean isAndroid=Boolean.FALSE;

    @Column(name="is_apple",columnDefinition="tinyint(1) not null comment '是否苹果投放'")
    private Boolean isApple=Boolean.FALSE;
    /**
     * 保存数据验证
     */
    public void prepareSave()
    {

    }
}

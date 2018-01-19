package com.pingxun.biz.product.domain.entity;

import com.pingxun.biz.common.entity.AggEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 产品子类
 * Created by Administrator on 2017/6/1.
 */
@Entity
@Table(name="cw_product_sub_type")
@Getter
@Setter
public class ProductSubType extends AggEntity{

    @Column(name="name",columnDefinition="varchar(100) not null comment '产品类型名称'")
    private String name;

    @Column(name="description",columnDefinition="varchar(500) not null comment '产品类型描述'")
    private String description;

    @Column(name="img",columnDefinition="varchar(100) not null comment '产品子类图片'")
    private String img;

    @Column(name="code",columnDefinition="varchar(100) not null comment '子类编码'")
    private String code;

    @Column(name="app_name",columnDefinition="varchar(100) comment 'app名称'")
    private String appName;

    @Column(name="text_color",columnDefinition="varchar(100) comment '文字颜色'")
    private String textColor;

    @Column(name="background_color",columnDefinition="varchar(100) comment '背景颜色'")
    private String backgroundColor;

}

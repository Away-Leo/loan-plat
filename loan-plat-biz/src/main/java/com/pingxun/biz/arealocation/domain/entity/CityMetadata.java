package com.pingxun.biz.arealocation.domain.entity;

import com.pingxun.biz.common.entity.AggEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
* @Title: CityMetadata.java
* @Description: 城市编码数据字典表
* @author Away
* @date 2017/12/19 14:41
* @copyright 重庆平讯数据
* @version V1.0
*/
@Entity
@Table(name="loan_metadata_city")
@org.hibernate.annotations.Table(appliesTo = "loan_metadata_city",comment="城市编码数据字典表")
@Getter
@Setter
public class CityMetadata extends AggEntity {

    @Column(name="city_name",columnDefinition="varchar(100) comment '城市名称'")
    private String name;

    @Column(name="city_adcode",columnDefinition="varchar(8) not null comment '地区编码'")
    private String adCode;

    @Column(name="city_citycode",columnDefinition="varchar(5) comment '城市编码'")
    private String citycode;
}

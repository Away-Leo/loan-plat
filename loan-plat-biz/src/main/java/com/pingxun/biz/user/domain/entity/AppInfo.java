package com.pingxun.biz.user.domain.entity;

import com.pingxun.biz.common.entity.AggEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
/**
 * APP管理
 * Created by Administrator on 2017/7/28.
 */
@Entity
@Table(name="loan_app")
@Getter
@Setter
public class AppInfo extends AggEntity {

    @Column(name="code",columnDefinition="varchar(100) not null comment '模块编码'")
    private String code;

    @Column(name="name",columnDefinition="varchar(100)  comment 'app名称'")
    private String name;

    @Column(name="sms_content",columnDefinition="varchar(100)  comment '短信内容'")
    private String smsContent;

    @Column(name="register_message",columnDefinition="varchar(500)  comment '注册消息'")
    private String registerMessage;

    @Column(name="is_valid",columnDefinition="tinyint(1) not null comment '是否有效'")
    private Boolean isValid=Boolean.TRUE;

    @Column(name="org_name",columnDefinition="varchar(100)  comment '所属主体'")
    private String orgName;

    @Column(name="master_secret",columnDefinition="varchar(100)  comment '极光推送masterSecret'")
    private String masterSecret;

    @Column(name="app_key",columnDefinition="varchar(100)  comment '极光推送app主键'")
    private String appKey;

    /**
     * 保存数据验证
     */
    public void prepareSave(){

    }
}
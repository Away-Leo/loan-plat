package com.pingxun.biz.log.domain.entity;

import com.pingxun.biz.common.entity.AggEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * 系统操作日志
 * Created by Administrator on 2017/6/1.
 */
@Entity
@Table(name="loan_log_sys")
@Setter
@Getter
public class SystemLog extends AggEntity {

    @Column(name="user_id",columnDefinition="int(11)  comment '用户ID'")
    private Long userId;

    @Column(name="log_date",columnDefinition="datetime  comment '操作时间'")
    private Date logDate;

    @Column(name="url",columnDefinition="varchar(200)  comment '操作URL'")
    private String url;

    @Column(name="operate_arg",columnDefinition="varchar(500)  comment '请求参数'")
    private String operateArg;

    @Column(name="action",columnDefinition="varchar(50)  comment '执行动作'")
    private String action;

    @Column(name="name",columnDefinition="varchar(200)  comment '操作名称'")
    private String name;

    @Column(name="remote_address",columnDefinition="varchar(50)  comment '操作IP'")
    private String remoteAddress;

    /**
     * 保存数据验证
     */
    public void prepareSave()
    {

    }
}

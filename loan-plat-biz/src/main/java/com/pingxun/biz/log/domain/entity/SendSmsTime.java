package com.pingxun.biz.log.domain.entity;

import com.pingxun.biz.common.entity.AggEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * 短信发送日志
 * Created by Administrator on 2017/6/1.
 */
@Entity
@Table(name="loan_log_send_sms_time")
@Getter
@Setter
public class SendSmsTime extends AggEntity {

    @Column(name="phone",columnDefinition="varchar(11) not null comment '手机号码'")
    private String phone;

    @Column(name="send_date",columnDefinition="date not null comment '发送时间'")
    private Date sendDate;

    @Column(name="sendTime",columnDefinition="int(11) default 0 comment '发送次数'")
    private Integer sendTime;

    @Column(name="app_name",columnDefinition="varchar(50)  comment 'app名称'")
    private String appName;

    /**
     * 保存数据验证
     */
    public void prepareSave()
    {

    }
}

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
@Table(name="loan_log_send_sms")
@Setter
@Getter
public class SendSmsLog extends AggEntity {

    @Column(name="phone",columnDefinition="varchar(11)  comment '手机号码'")
    private String phone;

    @Column(name="send_date",columnDefinition="datetime  comment '发送时间'")
    private Date sendDate;

    @Column(name="device_number",columnDefinition="varchar(50)  comment '发送终端唯一识别码'")
    private String deviceNumber;

    @Column(name="apply_area",columnDefinition="varchar(50)  comment '申请时区域'")
    private String applyArea;

    @Column(name="app_name",columnDefinition="varchar(50)  comment 'app名称'")
    private String appName;

    @Column(name="is_success",columnDefinition="tinyint(1)  comment '是否发送成功'")
    private Boolean isSuccess;
    /**
     * 保存数据验证
     */
    public void prepareSave()
    {

    }
}

package com.pingxun.biz.log.domain.entity;

import com.pingxun.biz.common.entity.AggEntity;
import com.pingxun.biz.log.app.LogEnum;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * 数据埋点日志
 * Created by Administrator on 2017/6/1.
 */
@Entity
@Table(name="loan_log")
@Getter
@Setter
public class Log extends AggEntity {

    @Column(name="product_id",columnDefinition="int(11)  comment '产品ID'")
    private Long productId;

    @Column(name="source_product_id",columnDefinition="int(11)  comment '推荐来源产品ID'")
    private Long sourceProductId;

    @Column(name="user_id",columnDefinition="int(11) comment '用户id'")
    private Long userId;

    @Column(name="channel_no",columnDefinition="varchar(20)  comment '来源渠道'")
    private String channelNo;

    @Column(name="type",columnDefinition="varchar(20)  comment '日志类型'")
    private LogEnum type;

    @Column(name="device_number",columnDefinition="varchar(500)  comment '设备号码'")
    private String deviceNumber;

    @Column(name="apply_area",columnDefinition="varchar(50)  comment '申请时区域'")
    private String applyArea;

    @Column(name="app_name",columnDefinition="varchar(50)  comment 'app名称'")
    private String appName;

    @Column(name="apply_date",columnDefinition="datetime  comment '申请时间'")
    private Date applyDate;

    @Column(name="invite_code",columnDefinition="int(11)  comment '邀请码'")
    private Long inviteCode;

    /**
     * 保存数据验证
     */
    public void prepareSave()
    {

    }
}

package com.pingxun.biz.call.domain.entity;

import com.pingxun.biz.common.entity.AggEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;


/**
* @Title: CallRecord.java
* @Description:  拨打电话记录
* @author Away
* @date 2018/1/9 18:14
* @copyright 重庆平讯数据
* @version V1.0
*/
@Entity
@Table(name="loan_call_record")
@org.hibernate.annotations.Table(appliesTo = "loan_call_record",comment = "拨打电话记录表" )
@Getter
@Setter
public class CallRecord extends AggEntity{

    @Column(name="user_id",columnDefinition="int(11) not null comment '用户ID'")
    private Long userId;

    @Column(name="side_user_id",columnDefinition="int(11) not null comment '对方用户ID'")
    private Long sideUserId;

    @Column(name="called_phone",columnDefinition="varchar(20) comment '拨打的电话号码'")
    private String calledPhone;

    @Column(name="called_position",columnDefinition="varchar(50) comment '拨打页面位置'")
    private String calledPosition;

    @Column(name="called_time",columnDefinition="datetime  comment '拨打时间'")
    private Date calledTime;
}

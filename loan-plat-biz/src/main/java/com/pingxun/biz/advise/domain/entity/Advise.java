package com.pingxun.biz.advise.domain.entity;

import com.pingxun.biz.common.entity.AggEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * APP管理
 * Created by Administrator on 2017/7/28.
 */
@Entity
@Table(name="loan_advise")
@Getter
@Setter
public class Advise extends AggEntity {

    @Column(name="user_id",columnDefinition="int(11) comment '用户id'")
    private Long userId;

    @Column(name="content",columnDefinition="varchar(2000)  comment '建议内容'")
    private String content;

    @Column(name="apply_date",columnDefinition="datetime  comment '提交时间'")
    private Date applyDate;

    /**
     * 保存数据验证
     */
    public void prepareSave()
    {

    }
}
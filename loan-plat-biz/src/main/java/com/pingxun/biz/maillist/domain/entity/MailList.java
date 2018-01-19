package com.pingxun.biz.maillist.domain.entity;

import com.pingxun.biz.common.entity.AggEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * 通讯录
 * Created by dujy on 2017/7/31.
 */
@Entity
@Table(name="loan_mail_list")
@Getter
@Setter
public class MailList extends AggEntity{

    @Column(name="loan_user_id",columnDefinition="int(11) comment '放款用户id'")
    private Long userId;

    @Column(name="borrow_user_id",columnDefinition="int(11) comment '借款用户ID'")
    private Long borrowUserId;

    @Column(name="borrow_name",columnDefinition="varchar(50) comment '借款人姓名'")
    private String borrowName;

    @Column(name="loan_name",columnDefinition="varchar(50) comment '放款人姓名'")
    private String loanName;

    @Column(name="apply_date",columnDefinition="datetime comment '申请时间'")
    private Date applyDate;

    @Column(name="is_agree",columnDefinition="int(11) default 0 comment '是否同意'")
    private Integer isAgree=0;

    @Column(name="apply_type",columnDefinition="int(11) default 0 comment '1：借款人申请，2：出借人申请'")
    private Integer applyType=0;
}

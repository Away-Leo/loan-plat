package com.pingxun.biz.maillist.app.dto;

import com.pingxun.biz.common.dto.PageDto;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

/**
 * 通讯录
 * Created by dujy on 2017/12/11.
 */
@Getter
@Setter
public class MailListDto extends PageDto {

    private Long id;

    private Long userId;

    private Integer userType;//用户类型

    private Long borrowUserId;

    private String loanName;

    private String borrowName;

    private Date applyDate;

    private Integer isAgree;

    private Long loanId;

    private Long borrowId;

    private Integer applyType;//申请方式：1：借款人申请，2：出借人申请

    private String channelNo;

}

package com.pingxun.biz.message.app.dto;

import com.pingxun.biz.common.dto.PageDto;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;


/**
 * 消息内容
 * Created by Administrator on 2017/6/1.
 */
@Getter
@Setter
public class MessageDto extends PageDto{

    private Long id;

    private Long userId;

    private String title;

    private String content;

    private Date sendDate;

    private String appName="RRJT";

    private Boolean isRead=Boolean.FALSE;

    private Integer msgType;//消息类型，默认是0，1：跳转通讯录

}

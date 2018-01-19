package com.pingxun.biz.call.app.dto;

import com.pingxun.biz.common.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CallRecordDto extends BaseDto {

    private Long id;

    private Long userId;

    private Long sideUserId;

    private String calledPhone;

    private String calledPosition;

    private Date calledTime;

}

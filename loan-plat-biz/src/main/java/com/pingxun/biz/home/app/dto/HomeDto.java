package com.pingxun.biz.home.app.dto;

import com.pingxun.biz.common.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class HomeDto extends BaseDto{

    private Long id;

    private String startDate;

    private String endDate;

    private Integer devUser;

}

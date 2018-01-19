package com.pingxun.biz.advise.app.dto;

import com.pingxun.biz.common.dto.PageDto;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 意见收集管理
 * Created by Administrator on 2017/7/28.
 */
@Getter
@Setter
public class AdviseDto extends PageDto {

    private Long id;

    private Long userId;

    private String content;

    private Date applyDate;

}
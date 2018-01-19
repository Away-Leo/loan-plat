package com.pingxun.biz.log.app.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 申请用户数统计
 * Created by Administrator on 2017/8/11.
 */
@Getter
@Setter
public class ApplyLogDto {

    private Long productId;

    private Integer applyTime=0;

    private Integer applyNum=0;
}

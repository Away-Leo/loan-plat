package com.pingxun.biz.order.app.dto;

import com.pingxun.biz.common.entity.AggEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * 支付查询记录表
 * Created by dujy on 2017/12/07.
 */
@Getter
@Setter
public class PayResultQueryDto extends AggEntity {

    private String queryParam;

}

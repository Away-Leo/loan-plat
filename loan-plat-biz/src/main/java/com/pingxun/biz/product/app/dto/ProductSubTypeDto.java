package com.pingxun.biz.product.app.dto;

import com.pingxun.biz.common.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;

/**
 * 产品分类
 * Created by Administrator on 2017/6/1.
 */
@Getter
@Setter
public class ProductSubTypeDto extends BaseDto{

    private Long id;

    private String name;

    private String description;

    private String img;

    private String code;

    private String textColor;

    private String backgroundColor;
}

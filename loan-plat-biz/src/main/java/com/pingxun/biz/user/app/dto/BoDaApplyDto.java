package com.pingxun.biz.user.app.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.List;
import java.io.Serializable;

/**
 * 博达数据接口
 * Created by Administrator on 2017/9/8.
 */
@Setter
@Getter
public class BoDaApplyDto implements Serializable {

    private String batchNo;

    private List<com.pingxun.biz.user.app.dto.BoDaLoanDto> data;

}

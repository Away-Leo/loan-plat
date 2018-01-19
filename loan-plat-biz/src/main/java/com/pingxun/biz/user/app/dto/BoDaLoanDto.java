package com.pingxun.biz.user.app.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 博达数据接口
 * Created by Administrator on 2017/9/8.
 */
@Setter
@Getter
public class BoDaLoanDto {

    private String loanType;// "生意人士贷款"
    private String clientName;// "张三",
    private String clientTel;//"12345678910",
    private String clientAge;// "50",
    private String companyName;// "xxx公司",
    private String businessTime;// "5年6月",
    private String serviceTime;// "6年",
    private String annualSalesIncome;//"120万",
    private String monthIncome;// "10万",
    private String annualProfit;// "100万",
    private String companyShareRatio;// "60%",
    private String houseAddress;//"重庆市xxxxx",
    private String sspfIsPay;//是"

}

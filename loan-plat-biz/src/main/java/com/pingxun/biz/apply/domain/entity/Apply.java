package com.pingxun.biz.apply.domain.entity;

import com.pingxun.biz.CwException;
import com.pingxun.biz.common.entity.AggEntity;
import com.pingxun.biz.product.app.dto.ProductDto;
import com.pingxun.biz.product.app.service.ProductAppService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

/**
 * 借款申请
 * Created by Administrator on 2017/6/13.
 */
@Entity
@Table(name="cw_product_apply")
@Setter
@Getter
public class Apply extends AggEntity{

    @Autowired
    private transient ProductAppService productAppService;

    private transient ProductDto productDto;

    @Column(name="product_id",columnDefinition="int(11)  comment '产品ID'")
    private Long productId;

    @Column(name="product_name",columnDefinition="varchar(100) not null comment '产品名称'")
    private String productName;

    @Column(name="img",columnDefinition="varchar(100) not null comment '产品图片'")
    private String img;

    @Column(name="service_rate",columnDefinition="decimal(10,2) comment '手续费率'")
    private BigDecimal serviceRate;

    @Column(name="period_type",columnDefinition="varchar(10) not null comment '期限类型'")
    private String periodType;

    @Column(name="start_period",columnDefinition="int(11) not null comment '开始期限'")
    private Integer startPeriod;

    @Column(name="end_period",columnDefinition="int(11) not null comment '结束期限'")
    private Integer endPeriod;

    @Column(name="start_amount",columnDefinition="decimal(20,2) not null comment '起始金额'")
    private BigDecimal startAmount;

    @Column(name="end_amount",columnDefinition="decimal(20,2) not null comment '最高限额'")
    private BigDecimal endAmount;

    @Column(name="loan_day",columnDefinition="varchar(100) not null comment '放款天数'")
    private String loanDay;

    @Column(name="user_id",columnDefinition="int(11) comment '用户id'")
    private Long userId;

    @Column(name="loan_amount",columnDefinition="decimal(20,6)  comment '借款金额'")
    private BigDecimal loanAmount;

    @Column(name="apply_area",columnDefinition="varchar(50)  comment '申请时区域'")
    private String applyArea;

    @Column(name="apply_date",columnDefinition="datetime  comment '申请时间'")
    private Date applyDate;

    @Column(name="channel_no",columnDefinition="varchar(20)  comment '申请渠道'")
    private String channelNo;

    @Column(name="app_name",columnDefinition="varchar(20)  comment 'app名称'")
    private String appName;

    @Column(name="product_flag",columnDefinition="varchar(20) comment '产品标识'")
    private String productFlag = "0";

    @Column(name="product_label",columnDefinition="varchar(200) comment '产品标签'")
    private String productLabel;

    @Column(name="sub_title",columnDefinition="varchar(200) comment '副标题'")
    private String subTitle;

    @Column(name="click_num",columnDefinition="int(11)  default 0 comment '跳转率'")
    private Integer clickNum=0;
    /**
     * 保存数据验证
     */
    public void prepareSave()
    {
        if(!Objects.isNull(productId))
        {
           productDto = productAppService.findById(productId);
           if(!Objects.isNull(productDto))
           {
               this.setProductName(productDto.getName());
               this.setStartPeriod(productDto.getStartPeriod());
               this.setEndPeriod(productDto.getEndPeriod());
               this.setStartAmount(productDto.getStartAmount());
               this.setEndAmount(productDto.getEndAmount());
               this.setPeriodType(productDto.getPeriodType());
               this.setLoanDay(productDto.getLoanDay());
               this.setImg(productDto.getImg());
               this.setServiceRate(productDto.getServiceRate());
               this.setProductFlag(productDto.getProductFlag());
               this.setProductLabel(productDto.getProductLabel());
               this.setSubTitle(productDto.getSubTitle());
               this.setClickNum(productDto.getClickNum());
           }else{
               CwException.throwIt("产品不存在");
           }

        }else{
            CwException.throwIt("产品id不能为空");
        }

    }

}

package com.pingxun.biz.product.domain.entity;

import com.pingxun.biz.common.entity.AggEntity;
import com.pingxun.core.common.util.AppUtils;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 金融产品
 * Created by Administrator on 2017/6/1.
 */
@Entity
@Table(name="cw_product")
@Getter
@Setter
public class Product extends AggEntity{

    @Column(name="name",columnDefinition="varchar(100) not null comment '产品名称'")
    private String name;

    @Column(name="img",columnDefinition="varchar(100) not null comment '产品图片'")
    private String img;

    @Column(name="detail_img",columnDefinition="varchar(100) not null comment '产品详情图片'")
    private String detailImg;

    @Column(name="recommend_img",columnDefinition="varchar(100) comment '产品宣传图'")
    private String recommendImg;

    @Column(name="product_type",columnDefinition="varchar(20)  comment '产品类型'")
    private String productType;

    @Column(name="product_sub_type",columnDefinition="varchar(20)  comment '产品子类型'")
    private String productSubType;

    @Column(name="service_type",columnDefinition="varchar(20)  comment '手续费类型'")
    private String serviceType;

    @Column(name="service_rate",columnDefinition="decimal(10,2) comment '手续费率'")
    private BigDecimal serviceRate;

    @Column(name="rate_memo",columnDefinition="varchar(100) comment '利率说明'")
    private String rateMemo;

    @Column(name="period_type",columnDefinition="varchar(20) not null comment '期限类型(日day/月month)'")
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

    @Column(name="product_desc",columnDefinition="varchar(500) not null comment '申请条件'")
    private String productDesc;

    @Column(name="apply_flow",columnDefinition="varchar(500) not null comment '申请流程'")
    private String applyFlow;

    @Column(name="apply_condition",columnDefinition="varchar(500) not null comment '申请条件'")
    private String applyCondition;

    @Column(name="apply_material",columnDefinition="varchar(500) not null comment '申请材料'")
    private String applyMaterial;

    @Column(name="source_type",columnDefinition="varchar(100) comment '来源类型'")
    private String sourceType;

    @Column(name="channel",columnDefinition="varchar(100) comment '来源渠道'")
    private String channel;

    @Column(name="url",columnDefinition="varchar(200) comment '客户app跳转地址'")
    private String url;

    @Column(name="view_num",columnDefinition="int(11)  default 0 comment '点击率'")
    private Integer viewNum=0;

    @Column(name="click_num",columnDefinition="int(11)  default 0 comment '跳转率'")
    private Integer clickNum=0;

    @Column(name="start_date",columnDefinition="datetime comment '上架时间'")
    private Date startDate;

    @Column(name="end_date",columnDefinition="datetime comment '下架时间'")
    private Date endDate;

    @Column(name="online_date",columnDefinition="datetime comment '上线时间'")
    private Date onlineDate;

    @Column(name="save_date",columnDefinition="datetime comment '入库时间'")
    private Date saveDate;

    @Column(name="product_flag",columnDefinition="varchar(20) comment '产品标示：1美借（启用摄像头）'")
    private String productFlag="0";

    @Column(name="is_valid",columnDefinition="tinyint(1) not null comment '是否有效'")
    private Boolean isValid=Boolean.FALSE;

    @Column(name="is_android",columnDefinition="tinyint(1) not null comment '安卓是否上架'")
    private Boolean androidFlag=Boolean.TRUE;

    @Column(name="is_apple",columnDefinition="tinyint(1) not null comment '苹果是否上架'")
    private Boolean appleFlag;

    @Column(name="is_recommend",columnDefinition="int(11) comment '是否推荐产品(1:推荐，2：热门，3:新口子)'")
    private Integer isRecommend;

    @Column(name="is_wechat",columnDefinition="tinyint(1) not null comment '微信是否上架'")
    private Boolean wechatFlag=Boolean.TRUE;

    @Column(name="belong_app",columnDefinition="varchar(200) comment '所属app'")
    private String belongApp= AppUtils.appNameMBD;
    //秒必贷排序
    @Column(name="show_order",columnDefinition="int(11) comment '排序'")
    private Integer showOrder;
    //借多少排序
    @Column(name="show_order_jds",columnDefinition="int(11) comment '借多少排序'")
    private Integer showOrderJds;
    //讯闪贷排序
    @Column(name="show_order_xsd",columnDefinition="int(11) comment '讯闪贷排序'")
    private Integer showOrderXsd;
    //借钱帝排序
    @Column(name="show_order_jqw",columnDefinition="int(11) comment '借钱帝排序'")
    private Integer showOrderJqw;
    //借钱贷排序
    @Column(name="show_order_dkqb",columnDefinition="int(11) comment '借钱贷排序'")
    private Integer showOrderDkqb;
    //借钱乐乐排序
    @Column(name="show_order_dsqb",columnDefinition="int(11) comment '借钱乐乐排序'")
    private Integer showOrderDsqb;
    //叮叮招财排序
    @Column(name="show_order_lsqd",columnDefinition="int(11) comment '叮叮招财排序'")
    private Integer showOrderLsqd;
    //贷款部落排序
    @Column(name="show_order_lyb",columnDefinition="int(11) comment '贷款部落排序'")
    private Integer showOrderLyb;
    //贷款帝排序
    @Column(name="show_order_pxqb",columnDefinition="int(11) comment '贷款帝排序'")
    private Integer showOrderPxqb;
    //速亿贷排序
    @Column(name="show_order_syd",columnDefinition="int(11) comment '速亿贷排序'")
    private Integer showOrderSyd;
    //速融贷排序
    @Column(name="show_order_srd",columnDefinition="int(11) comment '速融贷排序'")
    private Integer showOrderSrd;
    //借贷帮排序
    @Column(name="show_order_jdb",columnDefinition="int(11) comment '借贷帮排序'")
    private Integer showOrderJdb;
    //贷钱来排序
    @Column(name="show_order_dql",columnDefinition="int(11) comment '贷钱来排序'")
    private Integer showOrderDql;
    //贷款掌柜排序
    @Column(name="show_order_dkzg",columnDefinition="int(11) comment '贷款掌柜排序'")
    private Integer showOrderDkzg;
    //借借款排序
    @Column(name="show_order_jjk",columnDefinition="int(11) comment '借借款排序'")
    private Integer showOrderJjk;
    //信而贷排序
    @Column(name="show_order_xed",columnDefinition="int(11) comment '信而贷排序'")
    private Integer showOrderXed;
    //借钱树排序
    @Column(name="show_order_jqs",columnDefinition="int(11) comment '借钱树排序'")
    private Integer showOrderJqs;
    //贷款汇排序
    @Column(name="show_order_dkh",columnDefinition="int(11) comment '贷款汇排序'")
    private Integer showOrderDkh;
    //借钱花花排序
    @Column(name="show_order_jqhh",columnDefinition="int(11) comment '借借款排序'")
    private Integer showOrderJqhh;
    //借贷金钱
    @Column(name="show_order_jdjq",columnDefinition="int(11) comment '借贷金钱排序'")
    private Integer showOrderJdjq;
    //贷款堂
    @Column(name="show_order_dkt",columnDefinition="int(11) comment '贷款堂排序'")
    private Integer showOrderDkt;
    //快借王
    @Column(name="show_order_kjw",columnDefinition="int(11) comment '快借王排序'")
    private Integer showOrderKjw;
    //秒贷宝
    @Column(name="show_order_mdb",columnDefinition="int(11) comment '秒贷宝排序'")
    private Integer showOrderMdb;
    //好贷宝
    @Column(name="show_order_hdb",columnDefinition="int(11) comment '好贷宝排序'")
    private Integer showOrderHdb;
    //贷款神速
    @Column(name="show_order_dkss",columnDefinition="int(11) comment '贷款神速排序'")
    private Integer showOrderDkss;
    //借钱富
    @Column(name="show_order_jqf",columnDefinition="int(11) comment '借钱富排序'")
    private Integer showOrderJqf;
    //易借侠
    @Column(name="show_order_yjx",columnDefinition="int(11) comment '易借侠排序'")
    private Integer showOrderYjx;
    //借贷款
    @Column(name="show_order_jdk",columnDefinition="int(11) comment '借贷款排序'")
    private Integer showOrderJdk;
    //借贷款
    @Column(name="show_order_dkd",columnDefinition="int(11) comment '贷款哒排序'")
    private Integer showOrderDkd;

    @Column(name="product_label",columnDefinition="varchar(500) comment '产品标签'")
    private String productLabel;

    @Column(name="backend_login_url",columnDefinition="varchar(500)  comment '商户后台地址'")
    private String backendLoginUrl;

    @Column(name="login_user",columnDefinition="varchar(500) comment '登录用户名'")
    private String loginUser;

    @Column(name="login_pwd",columnDefinition="varchar(500) comment '登录密码'")
    private String loginPwd;

    @Column(name="cooperation_model",columnDefinition="varchar(20) comment '合作模式'")
    private String cooperationModel;

    @Column(name="unit_price",columnDefinition="varchar(200) comment '合作价格'")
    private String unitPrice;

    @Column(name="is_download_app",columnDefinition="tinyint(1) comment '是否需要下载app'")
    private Boolean isDownloadApp;

    @Column(name="limit_user_top",columnDefinition="int(11) comment '用户流量控制阈值'")
    private Integer limitUserTop=0;

    @Column(name="is_hidden",columnDefinition="int(1) comment '是否隐藏产品'")
    private Integer isHidden=0;

    @Column(name="jump_url",columnDefinition="varchar(200) comment '产品引流app产品链接'")
    private String jumpUrl;

    @Column(name="today_apply_user",columnDefinition="int(11) comment '当天申请人数'")
    private Integer todayApplyUser=0;

    @Column(name="operate_no",columnDefinition="int(11) comment '操作人员'")
    private Long operateNo;

    @Column(name="account_name",columnDefinition="varchar(200) comment '开户名称'")
    private String accountName;

    @Column(name="open_bank_name",columnDefinition="varchar(200) comment '开户银行'")
    private String openBankName;

    @Column(name="bank_account",columnDefinition="varchar(50) comment '银行账号'")
    private String bankAccount;

    @Column(name="link_address",columnDefinition="varchar(500) comment '联系地址'")
    private String linkAddress;

    @Column(name="link_phone",columnDefinition="varchar(50) comment '联系电话'")
    private String linkPhone;

    @Column(name="invoice_type",columnDefinition="int(11) comment '发票类型'")
    private Integer invoiceType;

    @Column(name="invoice_item",columnDefinition="varchar(50) comment '发票名目'")
    private String invoiceItem;

    @Column(name="taxes_object",columnDefinition="varchar(50) comment '税金支付方'")
    private String taxesObject;

    @Column(name="taxpayer_no",columnDefinition="varchar(50) comment '纳税人识别号'")
    private String taxpayerNo;

    @Column(name="invoice_link_address",columnDefinition="varchar(200) comment '发票邮寄地址'")
    private String invoiceLinkAddress;

    @Column(name="invoice_link_phone",columnDefinition="varchar(20) comment '发票收件人联系电话'")
    private String invoiceLinkPhone;

    @Column(name="invoice_memo",columnDefinition="varchar(200) comment '开票备注信息'")
    private String invoiceMemo;

    @Column(name="recommend_flag",columnDefinition="int(11) default 0 comment '产品推荐列表开关'")
    private Integer recommendFlag;

    @Column(name="loan_amount",columnDefinition="decimal(20,2) comment '放款金额'")
    private BigDecimal loanAmount;

    @Column(name="sub_title",columnDefinition="varchar(200) comment '副标题'")
    private String subTitle;

    @Column(name="start_level",columnDefinition="int(11) comment '星级'")
    private Integer startLevel;

    @Column(name="settle_cycle",columnDefinition="int(11) comment '结算周期：1：日结，3：周结，2：月结'")
    private Integer settleCycle;
    /**
     * 保存数据验证
     */
    public void prepareSave()
    {

    }
}

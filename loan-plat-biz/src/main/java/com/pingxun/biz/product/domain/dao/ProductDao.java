package com.pingxun.biz.product.domain.dao;

import com.pingxun.biz.CwException;
import com.pingxun.biz.product.app.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2017/9/5.
 */
@Repository
public class ProductDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 查询产品当天申请人数
     * @param productId
     * @return
     */
    public ProductListDto findProductUserById(Long productId){

        String sql = " select count(distinct user_id) as applyNum \n" +
                "   from cwapp.cw_log \n" +
                "  where product_id = "+productId+"  and type=2 \n" +
                "    and DATE_FORMAT(apply_date,'%y-%m-%d')=DATE_FORMAT(NOW(),'%y-%m-%d')\n" +
                "    and user_id in (select id from cwapp.pf_se_user where type='user' and register_date is not null)   \n" +
                "    and channel_no in (select code from cwapp.cw_channel union all select code from cwapp.cw_app_market)";

        List<ProductListDto> homeDto = jdbcTemplate.query(sql, new BeanPropertyRowMapper(ProductListDto.class));

        return homeDto.get(0);
    }

    /**
     * 查询审核渠道
     * @param channelNo
     * @param appName
     * @return
     */
    public Boolean findAuditChannel(String channelNo,String appName){
        String vivoAuditSql = "select * from cw_audit_channel where channel_no='"+channelNo+"' and app_name='"+appName+"'";
        List<AuditChannelDto> auditChannelDtoList = jdbcTemplate.query(vivoAuditSql, new BeanPropertyRowMapper(AuditChannelDto.class));
        return auditChannelDtoList.size()>0?Boolean.TRUE:Boolean.FALSE;

    }

    /**
     * 申请产品查询推荐产品信息
     * @param productId
     * @return
     */
    public List<ProductRecommendListDto> findProductRecommendByProductId(Long productId,String versionNo,String channelNo){

        String sqls="select a.*,a.is_android as androidFlag,a.is_apple as appleFlag from cw_product a where id="+productId;
        List<ProductDto> productDtoList = jdbcTemplate.query(sqls, new BeanPropertyRowMapper(ProductDto.class));
        if(productDtoList.size()==0){
            CwException.throwIt("产品不存在");
        }

        //判断苹果审核条件展示推荐数据
        String auditSql = "select data_version as dataVersion,is_audit as isAudit,app_name as appName" +
                "    from cw_audit_version where data_version='"+versionNo+"'";
        List<ProductAuditVersionDto> productVersionDtoList = jdbcTemplate.query(auditSql, new BeanPropertyRowMapper(ProductAuditVersionDto.class));
        String sql = "";
        if(productVersionDtoList.size()>0) {
            //查询需要审核的渠道
            Boolean isAudit = findAuditChannel(channelNo,versionNo);
            if (!channelNo.toUpperCase().contains("IOS".toUpperCase()) && isAudit ) {
                String vivoAuditSql = "select data_version as dataVersion,is_audit as isAudit,app_name as appName" +
                        "    from cw_audit_version where data_version='"+versionNo+"'";
                productVersionDtoList = jdbcTemplate.query(vivoAuditSql, new BeanPropertyRowMapper(ProductAuditVersionDto.class));
                sql+=createSql(productVersionDtoList,productDtoList,productId);
            }else if(channelNo.toUpperCase().contains("IOS".toUpperCase())){//IOS审核
                sql+=createSql(productVersionDtoList,productDtoList,productId);
            }else{//安卓非VIVO渠道审核sql
                sql+=createAuditSql(productDtoList,productId);
            }
        }else{
            sql += " select a.recommend_img as recommendImg,a.name,a.img,a.id,6850000 as loanAmount " +
                    "  from cw_product a " +
                    " where is_valid=1 and channel='重庆平讯数据服务有限公司'";
            sql += "    and is_android = 0 ";
            sql += "    and is_apple = 0 and belong_app like '%MBD%'";
            sql += "  limit 6 ";
        }

        List<ProductRecommendListDto> productListDtos = jdbcTemplate.query(sql, new BeanPropertyRowMapper(ProductRecommendListDto.class));
        return productListDtos;
    }

    /***
     * 构造审核数据sql
     * @param productDtoList
     * @param productId
     * @return
     */
    private String createAuditSql(List<ProductDto> productDtoList,Long productId){
        String sql="";
        sql += " select a.recommend_img as recommendImg,a.name,a.img,a.id,b.loan_amount as loanAmount,b.raw_add_time \n" +
                "   from cw_product a \n" +
                " INNER JOIN ( " +
                "  select recommend_product_id,loan_amount,raw_add_time from cw_product_recommend where product_id=" + productId + ") b " +
                "      on a.id = b.recommend_product_id " +
                " where is_valid=1 and channel!='重庆平讯数据服务有限公司' " +
                "   and ((limit_user_top <= today_apply_user  and (is_hidden=2 or is_hidden=0)) or today_apply_user<=limit_user_top) " +
                " union all ";
        sql += " (select a.recommend_img as recommendImg,a.name,a.img,a.id,a.loan_amount as loanAmount,raw_add_time " +
                "  from cw_product a where is_valid=1 and channel!='重庆平讯数据服务有限公司' and id !="+productId;
        boolean flag=false;
        if (productDtoList.get(0).getAndroidFlag()&&productDtoList.get(0).getAppleFlag()) {
            sql += "  and is_android = 1 and is_apple=1 " +
                   "  and ((limit_user_top <= today_apply_user  and (is_hidden=2 or is_hidden=0)) or today_apply_user<=limit_user_top)" +
                    " order by show_order asc ";
            flag=true;
        }
        if (productDtoList.get(0).getAppleFlag()&&!productDtoList.get(0).getAndroidFlag()&&!flag) {
            sql += " and ((limit_user_top <= today_apply_user  and (is_hidden=2 or is_hidden=0)) or today_apply_user<=limit_user_top)" +
                   " and is_apple = 1 and is_android=0 order by show_order_jqw asc ";
            flag=true;
        }
        if (!productDtoList.get(0).getAppleFlag()&&productDtoList.get(0).getAndroidFlag()&&!flag) {
            sql += " and ((limit_user_top <= today_apply_user  and (is_hidden=2 or is_hidden=0)) or today_apply_user<=limit_user_top)" +
                    " and is_apple = 0 and is_android=1 order by show_order asc ";
        }

        sql += "  limit 6 ) " ;
        return sql;
    }
    /**
     * 构造审核数据
     * @param productVersionDtoList
     * @param productDtoList
     * @param productId
     * @return
     */
    private String createSql(List<ProductAuditVersionDto> productVersionDtoList,List<ProductDto> productDtoList,Long productId){
        String sql="";
        if (productVersionDtoList.get(0).getIsAudit()) {
            sql += createAuditSql(productDtoList,productId);
        } else {
            sql += " select a.recommend_img as recommendImg,a.name,a.img,a.id,a.loan_amount as loanAmount " +
                    " from cw_product a where is_valid=1 and channel='重庆平讯数据服务有限公司'";
            sql += "  and is_android = 0 ";
            sql += "  and is_apple = 0 and belong_app like '%" + productVersionDtoList.get(0).getAppName() + "%'";
            sql += "  order by show_order asc  limit 6 ";
        }
        return sql;
    }

}

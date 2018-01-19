package com.pingxun.biz.log.domain.dao;

import com.pingxun.biz.log.app.dto.ApplyLogDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2017/8/11.
 */
@Repository
public class LogDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public  List<ApplyLogDto> findApplyNumById(){
        String sql = "select a.id as productId,b.applyTime,b.applyNum\n" +
                "  from cw_product a " +
                "   inner JOIN ( \n" +
                "  select product_id as productId,count(1) as applyTime,  \n" +
                "         count(distinct user_id) as applyNum  \n" +
                "\t  from cw_log a \n" +
                "\t where type=2  \t\t \n" +
                "\t\t and user_id in (select id from pf_se_user where type='user' and register_date is not null)    \n" +
                "\t\t and channel_no in (select code from cw_channel union all select code from cw_app_market)\n" +
                "  group by product_id) b on a.id = b.productId " ;

        List<ApplyLogDto> applyLogDtoList = jdbcTemplate.query(sql, new BeanPropertyRowMapper(ApplyLogDto.class));

        return applyLogDtoList;
    }

}

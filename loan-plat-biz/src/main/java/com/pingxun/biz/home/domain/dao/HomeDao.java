package com.pingxun.biz.home.domain.dao;

import com.pingxun.biz.home.app.dto.HomeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 首页数据表
 */
@Repository
public class HomeDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 查询当天新增情况
     * @param homeDto
     * @return
     */
    public HomeDto dayDevelopmentData(HomeDto homeDto){

        String sql = " select count(1) as devUser  " +
                "        from loan_log " +
                "       where type=0 " +
                "         and date_format(apply_date,'%y-%m-%d')=date_format(now(),'%y-%m-%d') ";


        List<HomeDto> homeDtoList = jdbcTemplate.query(sql, new BeanPropertyRowMapper(HomeDto.class));
        return homeDtoList.get(0);
    }

}

package com.pingxun.biz.user.domain.dao;

import com.pingxun.biz.user.app.dto.AppInfoDto;
import com.pingxun.biz.user.app.dto.AppModuleDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2017/7/28.
 */
@Repository
public class ModuleDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 根据app查询系统展示的模块数据
     * @param appName
     * @return
     */
    public AppModuleDto getAppModule(String appName) {
        String sql = "select module_ids from loan_module_app where app_name = '"+appName+"'";
        List<AppModuleDto> homeDto = jdbcTemplate.query(sql, new BeanPropertyRowMapper(AppModuleDto.class));
        if (homeDto.size() == 0) {
            return null;
        }
        return homeDto.get(0);
    }

    /**
     * 根据app查询系统展示的模块数据
     * @param appName
     * @return
     */
    public AppInfoDto getAppSendMessage(String appName) {
        String sql = "select * from loan_app where upper(code) = '"+appName.toUpperCase()+"'";
        List<AppInfoDto> homeDto = jdbcTemplate.query(sql, new BeanPropertyRowMapper(AppInfoDto.class));
        if (homeDto.size() == 0) {
            return null;
        }
        return homeDto.get(0);
    }


}

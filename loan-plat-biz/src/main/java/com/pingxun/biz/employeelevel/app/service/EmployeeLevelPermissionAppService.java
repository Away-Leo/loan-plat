package com.pingxun.biz.employeelevel.app.service;

import com.pingxun.biz.common.dto.Pages;
import com.pingxun.biz.employeelevel.app.dto.EmployeeLevelPermissionDto;
import com.pingxun.biz.employeelevel.domain.entity.EmployeeLevelPermission;
import com.pingxun.biz.employeelevel.domain.service.EmployeeLevelPermissonDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * 会员等级
 * Created by dujy on 2017-05-20.
 */
@Transactional
@Service
public class EmployeeLevelPermissionAppService {

    @Autowired
    private EmployeeLevelPermissonDomainService employeeLevelDomainService;

    /**
     * 修改会员等级
     * @param employeeLevelDto
     * @return
     */
    public Long update(EmployeeLevelPermissionDto employeeLevelDto)
    {
        return employeeLevelDomainService.update(employeeLevelDto).getId();
    }

    /**
     * 查询产品详情
     * @param id
     * @return
     */
    public EmployeeLevelPermissionDto findById(Long id)
    {
        EmployeeLevelPermissionDto employeeLevelPermissionDto = new EmployeeLevelPermissionDto();
        EmployeeLevelPermission employeeLevel = employeeLevelDomainService.findById(id);
        if(employeeLevel!=null){
            employeeLevelPermissionDto = employeeLevel.to(EmployeeLevelPermissionDto.class);
        }
        return employeeLevelPermissionDto;
    }

    /**
     * 按条件查询banner图标
     * @param dto
     * @return
     */
    public Page<EmployeeLevelPermissionDto> findByCondition(EmployeeLevelPermissionDto dto)
    {
        return Pages.map(employeeLevelDomainService.findByCondition(dto),EmployeeLevelPermissionDto.class);
    }
}

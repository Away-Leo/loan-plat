package com.pingxun.biz.employeelevel.app.service;

import com.pingxun.biz.common.dto.Pages;
import com.pingxun.biz.employeelevel.app.dto.EmployeeLevelDto;
import com.pingxun.biz.employeelevel.domain.entity.EmployeeLevel;
import com.pingxun.biz.employeelevel.domain.entity.EmployeeLevelPermission;
import com.pingxun.biz.employeelevel.domain.service.EmployeeLevelDomainService;
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
public class EmployeeLevelAppService {

    @Autowired
    private EmployeeLevelDomainService employeeLevelDomainService;

    @Autowired
    private EmployeeLevelPermissonDomainService employeeLevelPermissonDomainService;
    /**
     * 新增会员等级
     * @param employeeLevelDto
     * @return
     */
    public Long create(EmployeeLevelDto employeeLevelDto)
    {
        return employeeLevelDomainService.create(employeeLevelDto).getId();
    }

    /**
     * 修改会员等级
     * @param employeeLevelDto
     * @return
     */
    public Long update(EmployeeLevelDto employeeLevelDto)
    {
        return employeeLevelDomainService.update(employeeLevelDto).getId();
    }

    /**
     * 查询产品详情
     * @param id
     * @return
     */
    public EmployeeLevelDto findById(Long id)
    {
        EmployeeLevelDto employeeLevelDto = new EmployeeLevelDto();
        EmployeeLevel employeeLevel = employeeLevelDomainService.findById(id);
        if(employeeLevel!=null){
            employeeLevelDto = employeeLevel.to(EmployeeLevelDto.class);
            EmployeeLevelPermission permissionDto = employeeLevelPermissonDomainService.findById(employeeLevel.getId());
            employeeLevelDto.setAmountId(permissionDto.getAmountId());
            employeeLevelDto.setIsWechatAmount(permissionDto.getIsWechatAmount());
            employeeLevelDto.setIsSesameScore(permissionDto.getIsSesameScore());
            employeeLevelDto.setIsSocialSecurity(permissionDto.getIsSocialSecurity());
            employeeLevelDto.setIsGjj(permissionDto.getIsGjj());
            employeeLevelDto.setIsCreditCard(permissionDto.getIsCreditCard());
            employeeLevelDto.setIsHouse(permissionDto.getIsHouse());
            employeeLevelDto.setIsCar(permissionDto.getIsCar());

        }
        return employeeLevelDto;
    }

    /**
     * 按条件查询banner图标
     * @param dto
     * @return
     */
    public Page<EmployeeLevelDto> findByCondition(EmployeeLevelDto dto)
    {
        return Pages.map(employeeLevelDomainService.findByCondition(dto),EmployeeLevelDto.class);
    }
}

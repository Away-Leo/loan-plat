package com.pingxun.biz.employeelevel.domain.service;

import com.google.common.collect.Lists;
import com.pingxun.biz.CwException;
import com.pingxun.biz.employeelevel.app.dto.EmployeeLevelPermissionDto;
import com.pingxun.biz.employeelevel.domain.entity.EmployeeLevelPermission;
import com.pingxun.biz.employeelevel.domain.repository.EmployeeLevelPermissonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * 会员等级
 * Created by dujy on 2017-05-20.
 */
@Service
public class EmployeeLevelPermissonDomainService {

    @Autowired
    private EmployeeLevelPermissonRepository repository;

    /**
     * 新增会员等级
     * @param employeeLevelDto
     * @return
     */
    private EmployeeLevelPermission create(EmployeeLevelPermissionDto employeeLevelDto){
        EmployeeLevelPermission employeeLevel = new EmployeeLevelPermission();
        employeeLevel.from(employeeLevelDto);
        return repository.save(employeeLevel);
    }

    /**
     * 修改会员等级
     * @param employeeLevelDto
     * @return
     */
    public EmployeeLevelPermission update(EmployeeLevelPermissionDto employeeLevelDto)
    {
        EmployeeLevelPermission employeeLevel = repository.findByLevelId(employeeLevelDto.getLevelId());
        if(employeeLevel == null){
            employeeLevel = create(employeeLevelDto);
        }else {
            employeeLevel.from(employeeLevelDto);
            repository.save(employeeLevel);
        }
        return employeeLevel;
    }


    /**
     * 查询会员等级
     * @param id
     * @return
     */
    public EmployeeLevelPermission findById(Long id){
        return repository.findByLevelId(id);
    }

    /**
     * 按条件查询会员等级
     * @param employeeLevelPermissionDto
     * @return
     */
    public Page<EmployeeLevelPermission> findByCondition(EmployeeLevelPermissionDto employeeLevelPermissionDto)
    {
        String[] fields = {"showOrder"};
        employeeLevelPermissionDto.setSortFields(fields);
        Specification<EmployeeLevelPermission> supplierSpecification = (Root<EmployeeLevelPermission> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> predicates = Lists.newArrayListWithCapacity(20);

            query.where(cb.and(predicates.toArray(new Predicate[0])));
            return query.getRestriction();
        };
        return repository.findAll(supplierSpecification, employeeLevelPermissionDto.toPage());
    }

}

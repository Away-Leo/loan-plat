package com.pingxun.biz.employeelevel.domain.service;

import com.google.common.collect.Lists;
import com.pingxun.biz.CwException;
import com.pingxun.biz.employeelevel.app.dto.EmployeeLevelDto;
import com.pingxun.biz.employeelevel.domain.entity.EmployeeLevel;
import com.pingxun.biz.employeelevel.domain.repository.EmployeeLevelRepository;
import org.apache.commons.lang.StringUtils;
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
public class EmployeeLevelDomainService {

    @Autowired
    private EmployeeLevelRepository repository;

    /**
     * 新增会员等级
     * @param employeeLevelDto
     * @return
     */
    public EmployeeLevel create(EmployeeLevelDto employeeLevelDto)
    {
        EmployeeLevel employeeLevel = new EmployeeLevel();
        employeeLevel.from(employeeLevelDto);
        return repository.save(employeeLevel);
    }

    /**
     * 修改会员等级
     * @param employeeLevelDto
     * @return
     */
    public EmployeeLevel update(EmployeeLevelDto employeeLevelDto)
    {
        EmployeeLevel employeeLevel = repository.findOne(employeeLevelDto.getId());
        if(employeeLevel == null){
            CwException.throwIt("banner不存在");
        }
        employeeLevel.from(employeeLevelDto);
        repository.save(employeeLevel);

        return employeeLevel;
    }


    /**
     * 查询会员等级
     * @param id
     * @return
     */
    public EmployeeLevel findById(Long id)
    {
        return repository.findOne(id);
    }

    /**
     * 按条件查询会员等级
     * @param employeeLevelDto
     * @return
     */
    public Page<EmployeeLevel> findByCondition(EmployeeLevelDto employeeLevelDto)
    {
        String[] fields = {"rawAddTime"};
        employeeLevelDto.setSortFields(fields);
        Specification<EmployeeLevel> supplierSpecification = (Root<EmployeeLevel> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> predicates = Lists.newArrayListWithCapacity(20);

            if(StringUtils.isNotEmpty(employeeLevelDto.getName())){
                predicates.add(cb.like(root.get("name"), "%"+employeeLevelDto.getName()+"%"));
            }

            query.where(cb.and(predicates.toArray(new Predicate[0])));
            return query.getRestriction();
        };
        return repository.findAll(supplierSpecification, employeeLevelDto.toPage());
    }

}

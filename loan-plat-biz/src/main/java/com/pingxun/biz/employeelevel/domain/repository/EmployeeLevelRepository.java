package com.pingxun.biz.employeelevel.domain.repository;

import com.pingxun.biz.employeelevel.domain.entity.EmployeeLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 会员等级
 * Created by dujy on 2017-05-20.
 */
public interface EmployeeLevelRepository extends JpaRepository<EmployeeLevel,Long>,JpaSpecificationExecutor<EmployeeLevel> {


}

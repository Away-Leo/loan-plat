package com.pingxun.biz.employeelevel.domain.repository;

import com.pingxun.biz.employeelevel.domain.entity.EmployeeLevelPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 会员等级
 * Created by dujy on 2017-05-20.
 */
public interface EmployeeLevelPermissonRepository extends JpaRepository<EmployeeLevelPermission,Long>,JpaSpecificationExecutor<EmployeeLevelPermission> {

    EmployeeLevelPermission findByLevelId(Long levelId);
}

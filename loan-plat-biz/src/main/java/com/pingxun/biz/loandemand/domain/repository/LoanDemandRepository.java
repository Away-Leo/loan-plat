package com.pingxun.biz.loandemand.domain.repository;

import com.pingxun.biz.loandemand.domain.entity.LoanDemand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * 借款需求
 * Created by dujy on 2017-05-20.
 */
public interface LoanDemandRepository extends JpaRepository<LoanDemand,Long>,JpaSpecificationExecutor<LoanDemand> {

}

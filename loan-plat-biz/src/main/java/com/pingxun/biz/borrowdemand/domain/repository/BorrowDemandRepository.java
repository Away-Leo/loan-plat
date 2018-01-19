package com.pingxun.biz.borrowdemand.domain.repository;

import com.pingxun.biz.borrowdemand.domain.entity.BorrowDemand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * 借款需求
 * Created by dujy on 2017-05-20.
 */
public interface BorrowDemandRepository extends JpaRepository<BorrowDemand,Long>,JpaSpecificationExecutor<BorrowDemand> {

    List<BorrowDemand> findByUserIdOrderByLoanDateDesc(Long id);
}

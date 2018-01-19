package com.pingxun.biz.loandemand.domain.repository;


import com.pingxun.biz.loandemand.domain.entity.LoanBorrowRelation;
import com.pingxun.biz.loandemand.domain.entity.LoanDemand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 放款交易记录
 * Created by dujy on 2017-05-20.
 */
public interface LoanBorrowRelationRepository extends JpaRepository<LoanBorrowRelation,Long>,JpaSpecificationExecutor<LoanBorrowRelation> {

    @Query(value = "select * from loan_loan_borrow_relation where loan_user_id=?1 and borrow_user_id=?2 and status in (0,1)",nativeQuery = true)
    List<LoanBorrowRelation> findByIsApply(Long loanUserId, Long borrowUserId);

    @Query(value = "select * from loan_loan_borrow_relation where loan_user_id=?1 and borrow_user_id=?2 and status=0",nativeQuery = true)
    List<LoanBorrowRelation> findByLoanUserIdAndBorrowUserId(Long loanUserId, Long borrowUserId);

}

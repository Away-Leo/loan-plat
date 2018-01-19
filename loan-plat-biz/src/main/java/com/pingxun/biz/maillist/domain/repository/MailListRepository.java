package com.pingxun.biz.maillist.domain.repository;

import com.pingxun.biz.maillist.domain.entity.MailList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 借款需求
 * Created by dujy on 2017-05-20.
 */
public interface MailListRepository extends JpaRepository<MailList,Long>,JpaSpecificationExecutor<MailList> {

    @Query(value = "select * from loan_mail_list where borrow_user_id=?1 and loan_user_id=?2 and is_agree in (0,1)",nativeQuery = true)
    List<MailList> findByIsApplyFriend(Long borrowUserId,Long loanUserId);

    @Query(value = "select * from loan_mail_list where borrow_user_id=?1 and loan_user_id=?2 and is_agree =1 ",nativeQuery = true)
    List<MailList> findByIsFriend(Long borrowUserId,Long loanUserId);

    @Query(value = "select * from loan_mail_list where date_format(apply_date,'%y-%m-%d')=date_format(now(),'%y-%m-%d')  and borrow_user_id=?1",nativeQuery = true)
    List<MailList> findBorrowTodayApplyNum(Long borrowUserId);

    @Query(value = "select * from loan_mail_list where date_format(apply_date,'%y-%m-%d')=date_format(now(),'%y-%m-%d')  and loan_user_id=?1",nativeQuery = true)
    List<MailList> findLoanTodayApplyNum(Long borrowUserId);
}

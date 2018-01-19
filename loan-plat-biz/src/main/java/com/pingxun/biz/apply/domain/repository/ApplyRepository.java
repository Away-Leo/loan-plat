package com.pingxun.biz.apply.domain.repository;

import com.pingxun.biz.apply.domain.entity.Apply;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * 借款申请记录
 * Created by Administrator on 2017/6/13.
 */
public interface ApplyRepository extends PagingAndSortingRepository<Apply,Long>,JpaSpecificationExecutor<Apply> {
}

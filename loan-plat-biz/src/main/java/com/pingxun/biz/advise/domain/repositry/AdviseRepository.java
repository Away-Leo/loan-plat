package com.pingxun.biz.advise.domain.repositry;


import com.pingxun.biz.advise.domain.entity.Advise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 意见收集
 * Created by dujy on 2017-05-20.
 */
public interface AdviseRepository extends JpaRepository<Advise,Long>,JpaSpecificationExecutor<Advise> {

}

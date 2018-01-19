package com.pingxun.biz.apply.domain.service;

import com.pingxun.biz.apply.app.dto.ApplyDto;
import com.pingxun.biz.apply.domain.entity.Apply;
import com.pingxun.biz.apply.domain.repository.ApplyRepository;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Created by Administrator on 2017/6/13.
 */
@Service
public class ApplyDomainService {

    @Autowired
    private ApplyRepository repository;

    /**
     * 新增借款申请
     * @param applyDto
     * @return
     */
    public Apply create(ApplyDto applyDto){
        Apply apply = new Apply();
        apply.from(applyDto);
        apply.prepareSave();
        return repository.save(apply);
    }

    /**
     * 查询我的借款申请列表
     * @param applyDto
     * @return
     */
    public Page<Apply> findByCondition(ApplyDto applyDto)
    {
        String[] fields = {"applyDate"};
        applyDto.setSortFields(fields);
        applyDto.setSortDirection(Sort.Direction.DESC);
        Specification<Apply> supplierSpecification = (Root<Apply> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> predicates = Lists.newArrayListWithCapacity(4);
            predicates.add(cb.equal(root.get("userId"),applyDto.getUserId()));
            predicates.add(cb.equal(root.get("appName"),applyDto.getAppName()));
            query.where(cb.and(predicates.toArray(new Predicate[0])));
            return query.getRestriction();
        };
        return repository.findAll(supplierSpecification, applyDto.toPage());
    }
}

package com.pingxun.biz.advise.domain.service;


import com.google.common.collect.Lists;
import com.pingxun.biz.advise.app.dto.AdviseDto;
import com.pingxun.biz.advise.domain.entity.Advise;
import com.pingxun.biz.advise.domain.repositry.AdviseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;


import java.util.Date;
import java.util.List;


/**
 * 意见收集服务
 * Created by dujy on 2017-05-20.
 */
@Service
public class AdviseDomainService {

    @Autowired
    private AdviseRepository repository;

    /**
     * 新增意见
     * @param adviseDto
     * @return
     */
    public Advise create(AdviseDto adviseDto){
        Advise advise = new Advise();
        advise.from(adviseDto);
        advise.setApplyDate(new Date());

        return repository.save(advise);
    }


    /**
     * 查看意见列表
     * @param adviseDto
     * @return
     */
    public Page<Advise> findByCondition(AdviseDto adviseDto)
    {
        String[] fields = {"applyDate"};
        adviseDto.setSortFields(fields);
        adviseDto.setSortDirection(Sort.Direction.DESC);
        Specification<Advise> supplierSpecification = (Root<Advise> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> predicates = Lists.newArrayListWithCapacity(20);
            query.where(cb.and(predicates.toArray(new Predicate[0])));
            return query.getRestriction();
        };
        return repository.findAll(supplierSpecification, adviseDto.toPage());
    }



}

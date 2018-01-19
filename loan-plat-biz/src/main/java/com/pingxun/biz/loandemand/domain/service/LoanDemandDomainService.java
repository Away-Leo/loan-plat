package com.pingxun.biz.loandemand.domain.service;

import com.google.common.collect.Lists;
import com.pingxun.biz.CwException;
import com.pingxun.biz.loandemand.app.dto.LoanDemandDto;
import com.pingxun.biz.loandemand.domain.entity.LoanDemand;
import com.pingxun.biz.loandemand.domain.repository.LoanDemandRepository;
import com.pingxun.biz.user.app.dto.CwUserInfoDto;
import com.pingxun.biz.user.app.service.CwUserInfoAppService;
import com.zds.common.lang.beans.Copier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 订单服务
 * Created by dujy on 2017-05-20.
 */
@Service
public class LoanDemandDomainService {

    @Autowired
    private LoanDemandRepository repository;

    @Autowired
    private CwUserInfoAppService userInfoAppService;
    /**
     * 新增订单
     * @param orderDto
     * @return
     */
    public LoanDemand create(LoanDemandDto orderDto){
        LoanDemand loanDemand = new LoanDemand();
        loanDemand.from(orderDto);
        loanDemand.prepareSave();
        loanDemand.setPublishDate(new Date());
        updateUserInfo(orderDto);
        return repository.save(loanDemand);
    }

    /**
     * 同步修改用户信息
     * @param borrowDemandDto
     */
    private void updateUserInfo(LoanDemandDto borrowDemandDto){
        CwUserInfoDto cwUserInfoDto = new CwUserInfoDto();
        Copier.copy(borrowDemandDto,cwUserInfoDto);
        userInfoAppService.update(cwUserInfoDto);
    }
    /**
     * 修改订单状态
     * @param loanDemandDto
     * @return
     */
    public LoanDemand update(LoanDemandDto loanDemandDto){
        LoanDemand loanDemand = repository.findOne(loanDemandDto.getId());
        if(loanDemand == null){
            CwException.throwIt("需求不存在");
        }
        loanDemand.from(loanDemandDto);
        return loanDemand;
    }

    /**
     *
     * @param loanDemandDto
     * @return
     */
    public LoanDemand disable(LoanDemandDto loanDemandDto){
        LoanDemand loanDemand = repository.findOne(loanDemandDto.getId());
        if(loanDemand == null){
            CwException.throwIt("需求不存在");
        }
        loanDemand.setIsValid(Boolean.FALSE);
        return loanDemand;
    }

    /**
     * 查询订单详情
     * @param id
     * @return
     */
    public LoanDemand findById(Long id){
        return repository.findOne(id);
    }

    /**
     * 查看出借人出借列表
     * @param loanDemandDto
     * @return
     */
    public Page<LoanDemand> findByUserId(LoanDemandDto loanDemandDto){
        String[] fields = {"publishDate"};
        loanDemandDto.setSortFields(fields);
        loanDemandDto.setSortDirection(Sort.Direction.DESC);
        Specification<LoanDemand> supplierSpecification = (Root<LoanDemand> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> predicates = Lists.newArrayListWithCapacity(20);

            predicates.add(cb.equal(root.get("userId"), loanDemandDto.getUserId()));
            predicates.add(cb.equal(root.get("isValid"), Boolean.TRUE));
            query.where(cb.and(predicates.toArray(new Predicate[0])));
            return query.getRestriction();
        };
        return repository.findAll(supplierSpecification, loanDemandDto.toPage());
    }

    /**
     * 查询借款人的借款列表
     * @param loanDemandDto
     * @return
     */
    public Page<LoanDemand> findByCondition(LoanDemandDto loanDemandDto){
        String[] fields = {"publishDate"};
        loanDemandDto.setSortFields(fields);
        loanDemandDto.setSortDirection(Sort.Direction.DESC);
        Specification<LoanDemand> supplierSpecification = (Root<LoanDemand> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> predicates = Lists.newArrayListWithCapacity(20);

            //借款列表展示区域
            if(loanDemandDto.getUserId()==null) {
                if(loanDemandDto.getArea()==null){
                    loanDemandDto.setArea("010");
                }
                predicates.add(cb.like(root.get("area"),loanDemandDto.getArea()));
            }else{
                predicates.add(cb.equal(root.get("userId"), loanDemandDto.getUserId()));
            }
            //借款金额
            if(loanDemandDto.getLoanFee()!=null&&!"".equals(loanDemandDto.getLoanFee())){
                predicates.add(cb.lessThanOrEqualTo(root.get("loanStartFee"), loanDemandDto.getLoanFee()));
                predicates.add(cb.greaterThanOrEqualTo(root.get("loanEndFee"), loanDemandDto.getLoanFee()));
            }
            //查询期限
            if(loanDemandDto.getLoanPeriod()!=null&&loanDemandDto.getLoanPeriod()!=0){
                predicates.add(cb.lessThanOrEqualTo(root.get("loanStartPeriod"), loanDemandDto.getLoanPeriod()));
                predicates.add(cb.greaterThanOrEqualTo(root.get("loanEndPeriod"), loanDemandDto.getLoanPeriod()));
            }
            //贷款利率
            if(loanDemandDto.getMonthRate()!=null&&loanDemandDto.getMonthRate().compareTo(new BigDecimal(9))!=0){
                predicates.add(cb.lessThanOrEqualTo(root.get("monthRate"), loanDemandDto.getMonthRate()));
            }
            //借款要求
            if(loanDemandDto.getOther()!=null&&!"".equals(loanDemandDto.getOther())){
                predicates.add(cb.lessThanOrEqualTo(root.get("other"), "%"+loanDemandDto.getOther()+"%"));
            }
            predicates.add(cb.equal(root.get("isValid"), Boolean.TRUE));

            query.where(cb.and(predicates.toArray(new Predicate[0])));
            return query.getRestriction();
        };
        return repository.findAll(supplierSpecification, loanDemandDto.toPage());
    }
}

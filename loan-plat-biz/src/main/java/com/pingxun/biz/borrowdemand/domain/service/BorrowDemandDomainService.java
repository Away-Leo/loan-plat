package com.pingxun.biz.borrowdemand.domain.service;

import com.google.common.collect.Lists;
import com.pingxun.biz.CPContext;
import com.pingxun.biz.CwException;
import com.pingxun.biz.borrowdemand.app.dto.BorrowDemandDto;
import com.pingxun.biz.borrowdemand.domain.entity.BorrowDemand;
import com.pingxun.biz.borrowdemand.domain.repository.BorrowDemandRepository;
import com.pingxun.biz.loandemand.app.dto.LoanBorrowRelationDto;
import com.pingxun.biz.loandemand.app.service.LoanBorrowRelationAppService;
import com.pingxun.biz.user.app.dto.CwUserInfoDto;
import com.pingxun.biz.user.app.service.CwUserInfoAppService;
import com.zds.common.lang.beans.Copier;
import org.apache.commons.lang.StringUtils;
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
 * 借款需求
 * Created by dujy on 2017-05-20.
 */
@Service
public class BorrowDemandDomainService {

    @Autowired
    private BorrowDemandRepository repository;

    @Autowired
    private CwUserInfoAppService userInfoAppService;
    /**
     * 新增借款需求
     * @param borrowDemandDto
     * @return
     */
    public BorrowDemand create(BorrowDemandDto borrowDemandDto){
        BorrowDemand borrowDemand = new BorrowDemand();
        borrowDemand.from(borrowDemandDto);
        borrowDemand.prepareSave();
        borrowDemand.setPublishDate(new Date());
        updateUserInfo(borrowDemandDto);
        return repository.save(borrowDemand);
    }

    /**
     * 同步修改用户信息
     * @param borrowDemandDto
     */
    private void updateUserInfo(BorrowDemandDto borrowDemandDto){
        CwUserInfoDto cwUserInfoDto = new CwUserInfoDto();
        Copier.copy(borrowDemandDto,cwUserInfoDto);
        cwUserInfoDto.setUserId(CPContext.getContext().getSeUserInfo().getId());
        cwUserInfoDto.setId(null);
        userInfoAppService.update(cwUserInfoDto);
    }
    /**
     * 修改借款需求状态
     * @param borrowDemandDto
     * @return
     */
    public BorrowDemand update(BorrowDemandDto borrowDemandDto){
        BorrowDemand loanDemand = repository.findOne(borrowDemandDto.getId());
        if(loanDemand == null){
            CwException.throwIt("需求不存在");
        }
        loanDemand.from(borrowDemandDto);
        updateUserInfo(borrowDemandDto);

        return loanDemand;
    }

    /**
     * 删除借款需求
     * @param borrowDemandDto
     * @return
     */
    public BorrowDemand disable(BorrowDemandDto borrowDemandDto){
        BorrowDemand loanDemand = repository.findOne(borrowDemandDto.getId());
        if(loanDemand == null){
            CwException.throwIt("需求不存在");
        }
        loanDemand.setIsValid(Boolean.FALSE);

        return loanDemand;
    }

    /**
     * 查询借款需求详情
     * @param id
     * @return
     */
    public BorrowDemand findById(Long id){
        return repository.findOne(id);
    }

    /**
     * 通讯录查看借款需求
     * @param borrowDemandDto
     * @return
     */
    public Page<BorrowDemand> findByUserId(BorrowDemandDto borrowDemandDto){
        String[] fields = {"publishDate"};
        borrowDemandDto.setSortFields(fields);
        borrowDemandDto.setSortDirection(Sort.Direction.DESC);
        Specification<BorrowDemand> supplierSpecification = (Root<BorrowDemand> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> predicates = Lists.newArrayListWithCapacity(20);

            //查询借款人有效的借款需求
            predicates.add(cb.equal(root.get("isValid"), Boolean.TRUE));
            predicates.add(cb.equal(root.get("userId"), borrowDemandDto.getUserId()));
            query.where(cb.and(predicates.toArray(new Predicate[0])));
            return query.getRestriction();
        };
        return repository.findAll(supplierSpecification, borrowDemandDto.toPage());
    }

    /**
     * 查询借款人的借款列表
     * @param borrowDemandDto
     * @return
     */
    public Page<BorrowDemand> findByCondition(BorrowDemandDto borrowDemandDto){
        String[] fields = {"publishDate"};
        borrowDemandDto.setSortFields(fields);
        borrowDemandDto.setSortDirection(Sort.Direction.DESC);
        Specification<BorrowDemand> supplierSpecification = (Root<BorrowDemand> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
        List<Predicate> predicates = Lists.newArrayListWithCapacity(20);

        //借款列表展示区域
        if(borrowDemandDto.getUserId()==null) {
            if(borrowDemandDto.getArea()==null){
                borrowDemandDto.setArea("010");
            }
            predicates.add(cb.equal(root.get("area"), borrowDemandDto.getArea()));

            //借款金额
            if(borrowDemandDto.getLoanStartFee()!=null&&!"".equals(borrowDemandDto.getLoanStartFee())){
                predicates.add(cb.between(root.get("loanFee"), borrowDemandDto.getLoanStartFee(),borrowDemandDto.getLoanEndFee()));
            }
            //查询期限
            if(borrowDemandDto.getLoanStartPeriod()!=null&&!"".equals(borrowDemandDto.getLoanStartPeriod())){
                predicates.add(cb.between(root.get("loanPeriod"), borrowDemandDto.getLoanStartPeriod(),borrowDemandDto.getLoanEndPeriod()));
            }
            //社会身份
            if(borrowDemandDto.getSocialIdentity()!=null&&!"".equals(borrowDemandDto.getSocialIdentity())){
                predicates.add(cb.equal(root.get("socialIdentity"), borrowDemandDto.getSocialIdentity()));
            }

            //借款用途
            if(borrowDemandDto.getLoanPurpose()!=null&&!"".equals(borrowDemandDto.getLoanPurpose())){
                predicates.add(cb.equal(root.get("loanPurpose"), borrowDemandDto.getLoanPurpose()));
            }

            //芝麻分
            if(borrowDemandDto.getIsSesameScore()!=null){
                predicates.add(cb.equal(root.get("isSesameScore"), borrowDemandDto.getIsSesameScore()));
            }
            //是否微粒贷
            if(borrowDemandDto.getIsWechatAmount()!=null){
                predicates.add(cb.equal(root.get("isWechatAmount"), borrowDemandDto.getIsWechatAmount()));
            }
            //有无社保
            if(borrowDemandDto.getIsSocialSecurity()!=null){
                predicates.add(cb.equal(root.get("isSocialSecurity"), borrowDemandDto.getIsSocialSecurity()));
            }
            //有无公积金
            if(borrowDemandDto.getIsGjj()!=null){
                predicates.add(cb.equal(root.get("isGjj"), borrowDemandDto.getIsGjj()));
            }
            //有无信用卡
            if(borrowDemandDto.getIsCreditCard()!=null){
                predicates.add(cb.equal(root.get("isCreditCard"), borrowDemandDto.getIsCreditCard()));
            }
            //有无房贷
            if(borrowDemandDto.getIsHouse()!=null){
                predicates.add(cb.equal(root.get("isHouse"), borrowDemandDto.getIsHouse()));
            }
            //有无车贷
            if(borrowDemandDto.getIsCar()!=null){
                predicates.add(cb.equal(root.get("isCar"), borrowDemandDto.getIsCar()));
            }
        }else{
            predicates.add(cb.equal(root.get("userId"), borrowDemandDto.getUserId()));
        }
        predicates.add(cb.equal(root.get("isValid"), Boolean.TRUE));

        query.where(cb.and(predicates.toArray(new Predicate[0])));
        return query.getRestriction();
        };
        return repository.findAll(supplierSpecification, borrowDemandDto.toPage());
    }
}

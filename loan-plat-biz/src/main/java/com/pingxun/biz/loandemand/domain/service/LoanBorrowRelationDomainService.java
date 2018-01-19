package com.pingxun.biz.loandemand.domain.service;

import com.google.common.collect.Lists;
import com.pingxun.biz.CPContext;
import com.pingxun.biz.CwException;
import com.pingxun.biz.loandemand.app.dto.LoanBorrowRelationDto;
import com.pingxun.biz.loandemand.domain.entity.LoanBorrowRelation;
import com.pingxun.biz.loandemand.domain.repository.LoanBorrowRelationRepository;
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
 * 借款放款关系对应表
 * Created by dujy on 2017-05-20.
 */
@Service
public class LoanBorrowRelationDomainService {

    @Autowired
    private LoanBorrowRelationRepository repository;

    /**
     * 新增借放对应关系
     *
     * @param loanBorrowRelationDto
     * @return
     */
    public LoanBorrowRelation create(LoanBorrowRelationDto loanBorrowRelationDto) {
        List<LoanBorrowRelation> loanBorrowRelationList = repository.findByIsApply(loanBorrowRelationDto.getLoanUserId(),loanBorrowRelationDto.getBorrowUserId());
        if(loanBorrowRelationList.size()>0){
            CwException.throwIt("该借款已申请");
        }
        LoanBorrowRelation loanBorrowRelation = new LoanBorrowRelation();
        loanBorrowRelation.from(loanBorrowRelationDto);
        loanBorrowRelation.setTransferDate(new Date());
        loanBorrowRelation.prepareSave();
        return repository.save(loanBorrowRelation);
    }

    /**
     * 修改借款关系
     *
     * @param loanBorrowRelationDto
     * @return
     */
    public LoanBorrowRelation update(LoanBorrowRelationDto loanBorrowRelationDto) {
        LoanBorrowRelation loanBorrowRelation = findLoanId(loanBorrowRelationDto);
        if (loanBorrowRelation == null) {
            CwException.throwIt("借款关系不存在");
        }
        if(loanBorrowRelationDto.getStatus()==9){
            loanBorrowRelation.setStatus(2);//拒绝
        }else {
            loanBorrowRelation.setStatus(loanBorrowRelationDto.getStatus());
        }
        return loanBorrowRelation;
    }

    /**
     * 查找借款出借申请记录id
     * @param loanBorrowRelationDto
     * @return
     */
    public LoanBorrowRelation findLoanId(LoanBorrowRelationDto loanBorrowRelationDto) {
        return repository.findByLoanUserIdAndBorrowUserId(loanBorrowRelationDto.getLoanUserId(),loanBorrowRelationDto.getBorrowUserId()).get(0);
    }

    /**
     * 查询借款人
     * @param id
     * @return
     */
    public LoanBorrowRelation findById(Long id){
        return repository.findOne(id);
    }

    /**
     * 查询借款人列表
     * @param loanBorrowRelationDto
     * @return
     */
    public Page<LoanBorrowRelation> findByCondition(LoanBorrowRelationDto loanBorrowRelationDto){
        String[] fields = {"transferDate"};
        loanBorrowRelationDto.setSortFields(fields);
        loanBorrowRelationDto.setSortDirection(Sort.Direction.DESC);
        Specification<LoanBorrowRelation> supplierSpecification = (Root<LoanBorrowRelation> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> predicates = Lists.newArrayListWithCapacity(20);
            if(loanBorrowRelationDto.getLoanType()==1) {
                predicates.add(cb.equal(root.get("borrowUserId"), CPContext.getContext().getSeUserInfo().getId()));
                predicates.add(cb.isNull(root.get("borrowId")));
            }else{
                predicates.add(cb.equal(root.get("loanUserId"), CPContext.getContext().getSeUserInfo().getId()));
                predicates.add(cb.isNull(root.get("loanId")));
            }
            query.where(cb.and(predicates.toArray(new Predicate[0])));
            return query.getRestriction();
        };
        return repository.findAll(supplierSpecification, loanBorrowRelationDto.toPage());
    }
}

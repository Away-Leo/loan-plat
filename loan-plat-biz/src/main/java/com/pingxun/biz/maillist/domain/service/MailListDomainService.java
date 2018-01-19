package com.pingxun.biz.maillist.domain.service;

import com.google.common.collect.Lists;
import com.pingxun.biz.CPContext;
import com.pingxun.biz.CwException;
import com.pingxun.biz.loandemand.app.dto.LoanBorrowRelationDto;
import com.pingxun.biz.loandemand.app.service.LoanBorrowRelationAppService;
import com.pingxun.biz.maillist.app.dto.MailListDto;
import com.pingxun.biz.maillist.domain.entity.MailList;
import com.pingxun.biz.maillist.domain.repository.MailListRepository;
import com.pingxun.biz.message.app.dto.MessageDto;
import com.pingxun.biz.message.app.service.MessageAppService;
import com.pingxun.biz.user.app.dto.CwUserInfoDto;
import com.pingxun.biz.user.app.service.CwUserInfoAppService;
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
import java.util.List;

/**
 * 通讯录服务
 * Created by dujy on 2017-05-20.
 */
@Service
public class MailListDomainService {

    @Autowired
    private MailListRepository repository;

    @Autowired
    private CwUserInfoAppService cwUserInfoAppService;

    @Autowired
    private LoanBorrowRelationAppService loanBorrowRelationAppService;
    @Autowired
    private MessageAppService messageAppService;
    /**
     * 申请查看通讯录
     * @param mailListDto
     * @return
     */
    public MailList create(MailListDto mailListDto){
        MailList mailList = new MailList();
        mailList.from(mailListDto);
        apply(mailListDto);
        return repository.save(mailList);
    }

    /**
     * 记录申请需求关系
     * @param mailListDto
     */
    private void apply(MailListDto mailListDto){
        LoanBorrowRelationDto loanBorrowRelationDto = new LoanBorrowRelationDto();
        loanBorrowRelationDto.setLoanUserId(mailListDto.getUserId());
        loanBorrowRelationDto.setBorrowUserId(mailListDto.getBorrowUserId());
        loanBorrowRelationDto.setBorrowId(mailListDto.getBorrowId());
        loanBorrowRelationDto.setLoanId(mailListDto.getLoanId());
        loanBorrowRelationDto.setChannelNo(mailListDto.getChannelNo());
        if(mailListDto.getApplyType()==2){
            loanBorrowRelationDto.setStatus(1);
        }
        loanBorrowRelationAppService.create(loanBorrowRelationDto);
    }

    /**
     * 查询是否申请好友
     * @param mailListDto
     * @return
     */
    public List<MailList> findByBorrowUserIdAndUserId(MailListDto mailListDto){
        return repository.findByIsApplyFriend(mailListDto.getBorrowUserId(),mailListDto.getUserId());
    }

    /**
     * 借款人当日申请次数
     * @param userId
     * @return
     */
    public List<MailList> findBorrowTodayApplyNum(Long userId){
        return repository.findBorrowTodayApplyNum(userId);
    }

    /**
     * 出借人当日申请人数
     * @param userId
     * @return
     */
    public List<MailList> findLoanTodayApplyNum(Long userId){
        return repository.findLoanTodayApplyNum(userId);
    }

    /**
     * 同意或者拒绝查看通讯录
     * @param mailListDto
     * @return
     */
    public MailList update(MailListDto mailListDto){
        MailList mailList = repository.findOne(mailListDto.getId());
        if(mailList == null){
            CwException.throwIt("好友关系不存在");
        }
        mailList.setIsAgree(mailListDto.getIsAgree());
        //修改申请状态
        updateApplyStatus(mailListDto);
        //发送验证消息
        addFriendMessage(mailListDto);
        return mailList;
    }

    /**
     * 好友处理同意
     * @param mailListDto
     */
    private void addFriendMessage(MailListDto mailListDto){
        MessageDto messageDto = new MessageDto();
        messageDto.setAppName("RRJT");
        String name="";
        if(mailListDto.getUserId()!=null){//放款人
            name = cwUserInfoAppService.findById(mailListDto.getUserId()).getName();
            messageDto.setUserId(mailListDto.getBorrowUserId());
        }else{//借款人
            name = cwUserInfoAppService.findById(mailListDto.getBorrowUserId()).getName();
            messageDto.setUserId(mailListDto.getUserId());
        }
        if(mailListDto.getIsAgree()==1) {
            messageDto.setTitle(name+"同意添加您为好友!");
            messageDto.setContent(name+"同意添加您为好友!");
        }else{
            messageDto.setTitle(name+"拒绝添加您为好友!");
            messageDto.setContent(name+"拒绝添加您为好友!");
        }
        messageDto.setMsgType(1);
        messageAppService.create(messageDto);

//        if(mailListDto.getUserId()!=null){
//            if(mailListDto.getIsAgree()==1) {
//                name = cwUserInfoAppService.findById(mailListDto.getBorrowUserId()).getName();
//                messageDto.setUserId(mailListDto.getUserId());
//                messageDto.setTitle(name+"已经成为您好友!");
//                messageDto.setContent(name+"已经成为您好友!");
//            }
//        }
    }
    /**
     * 修改申请记录状态
     * @param mailListDto
     */
    private void updateApplyStatus(MailListDto mailListDto){
        CwUserInfoDto cwUserInfoDto = cwUserInfoAppService.findById(CPContext.getContext().getSeUserInfo().getId());
        if(cwUserInfoDto.getUserType()==2) {
            mailListDto.setUserId(CPContext.getContext().getSeUserInfo().getId());
        }else {
            mailListDto.setBorrowUserId(CPContext.getContext().getSeUserInfo().getId());
        }
        LoanBorrowRelationDto loanBorrowRelationDto = new LoanBorrowRelationDto();
        loanBorrowRelationDto.setLoanUserId(mailListDto.getUserId());
        loanBorrowRelationDto.setBorrowUserId(mailListDto.getBorrowUserId());
        loanBorrowRelationDto.setStatus(mailListDto.getIsAgree());
        loanBorrowRelationAppService.update(loanBorrowRelationDto);
    }
    /**
     * 查询通讯录
     * @param id
     * @return
     */
    public MailList findById(Long id){
        return repository.findOne(id);
    }

    /**
     * 查询是否是好朋友
     * @param dto
     * @return
     */
    public MailList findIsFriend(MailListDto dto){
        List<MailList>  mailListList =repository.findByIsFriend(dto.getBorrowUserId(),dto.getUserId());
        if(mailListList.size()>0){
            return mailListList.get(0);
        }else{
            return null;
        }
    }

    /**
     * 查询借款人或者出借人的通讯录
     * @param mailListDto
     * @return
     */
    public Page<MailList> findByCondition(MailListDto mailListDto){
        String[] fields = {"applyDate"};
        mailListDto.setSizePerPage(1000);
        mailListDto.setSortFields(fields);
        mailListDto.setSortDirection(Sort.Direction.DESC);
        Specification<MailList> supplierSpecification = (Root<MailList> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> predicates = Lists.newArrayListWithCapacity(20);
            CwUserInfoDto cwUserInfoDto = cwUserInfoAppService.findById(CPContext.getContext().getSeUserInfo().getId());
            if(cwUserInfoDto.getUserType()==1) {
                predicates.add(cb.equal(root.get("borrowUserId"), CPContext.getContext().getSeUserInfo().getId()));
                if(mailListDto.getIsAgree()==0) {
                    predicates.add(cb.equal(root.get("applyType"), 2));
                }
            }else{
                predicates.add(cb.equal(root.get("userId"), CPContext.getContext().getSeUserInfo().getId()));
                if(mailListDto.getIsAgree()==0) {
                    predicates.add(cb.equal(root.get("applyType"), 1));
                }
            }

            if(StringUtils.isNotEmpty(mailListDto.getLoanName())){
                predicates.add(cb.like(root.get("loanName"), "%"+mailListDto.getLoanName()+"%"));
            }
            if(StringUtils.isNotEmpty(mailListDto.getBorrowName())){
                predicates.add(cb.like(root.get("borrowName"), "%"+mailListDto.getBorrowName()+"%"));
            }

            predicates.add(cb.equal(root.get("isAgree"), mailListDto.getIsAgree()));//同意
            query.where(cb.and(predicates.toArray(new Predicate[0])));
            return query.getRestriction();
        };
        return repository.findAll(supplierSpecification, mailListDto.toPage());
    }

}

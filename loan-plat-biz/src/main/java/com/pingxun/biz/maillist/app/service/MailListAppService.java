package com.pingxun.biz.maillist.app.service;

import com.pingxun.biz.CPContext;
import com.pingxun.biz.CwException;
import com.pingxun.biz.common.dto.Pages;
import com.pingxun.biz.loandemand.app.service.LoanBorrowRelationAppService;
import com.pingxun.biz.maillist.app.dto.MailListDto;
import com.pingxun.biz.maillist.domain.entity.MailList;
import com.pingxun.biz.maillist.domain.service.MailListDomainService;
import com.pingxun.biz.parameter.app.dto.ParameterDto;
import com.pingxun.biz.parameter.app.service.ParameterAppService;
import com.pingxun.biz.user.app.dto.CwUserInfoDto;
import com.pingxun.biz.user.app.service.CwUserInfoAppService;
import com.pingxun.core.common.util.ObjectHelper;
import com.pingxun.core.common.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * 通讯录
 * Created by dujy on 2017-05-20.
 */
@Transactional
@Service
public class MailListAppService {

    @Autowired
    private MailListDomainService mailListDomainService;

    @Autowired
    private ParameterAppService parameterAppService;

    @Autowired
    private CwUserInfoAppService cwUserInfoAppService;
    /**
     * 新增通讯录
     * @param mailListDto
     * @return
     */
    public Long create(MailListDto mailListDto){

        ParameterDto parameterDto = parameterAppService.findByCode("applyLimit");
        //查询当日新增联系人次数
        if(ObjectHelper.isNotEmpty(mailListDto))mailListDto.setApplyDate(Utils.getCurrentDayDate());
        //借款人添加出借人
        Boolean flag = Boolean.FALSE;
        if(mailListDto.getUserId()!=null){
            CwUserInfoDto cwUserInfoDto = cwUserInfoAppService.findById(mailListDto.getBorrowUserId());
            mailListDto.setUserId(mailListDto.getUserId());
            mailListDto.setBorrowUserId(CPContext.getContext().getSeUserInfo().getId());
            mailListDto.setLoanName(cwUserInfoDto.getName());
            mailListDto.setBorrowName(cwUserInfoAppService.findById(mailListDto.getBorrowUserId()).getName());
            mailListDto.setApplyType(1);
            if(!cwUserInfoDto.getMessageTipFlag()) {
                mailListDto.setIsAgree(1);//默认同意查看需求
            }
//            List<MailList> loanApplyTime = mailListDomainService.findLoanTodayApplyNum(CPContext.getContext().getSeUserInfo().getId());
//            if(Integer.parseInt(parameterDto.getParameterValue()) < loanApplyTime.size()){
//                CwException.throwIt("当日申请超过限制");
//            }
            flag=Boolean.TRUE;
        }
        //出借人添加借款人
        if(mailListDto.getBorrowUserId()!=null&&!flag){
            CwUserInfoDto cwUserInfoDto = cwUserInfoAppService.findById(mailListDto.getBorrowUserId());
            mailListDto.setBorrowUserId(mailListDto.getBorrowUserId());
            mailListDto.setUserId(CPContext.getContext().getSeUserInfo().getId());
            mailListDto.setBorrowName(cwUserInfoDto.getName());
            mailListDto.setLoanName(cwUserInfoAppService.findById(mailListDto.getUserId()).getName());
            mailListDto.setApplyType(2);
            //加好友消息提示开关控制
            if(!cwUserInfoDto.getMessageTipFlag()) {
                mailListDto.setIsAgree(1);//默认同意查看需求
            }
            List<MailList> borrowApplyTime = mailListDomainService.findLoanTodayApplyNum(CPContext.getContext().getSeUserInfo().getId());
            if(Integer.parseInt(parameterDto.getParameterValue()) <=borrowApplyTime.size()){
                CwException.throwIt("当日申请超过限制");
            }
        }

        //查询是否是好友
        List<MailList> mailLists = mailListDomainService.findByBorrowUserIdAndUserId(mailListDto);
        if(mailLists.size()>0){
            if(mailLists.get(0).getIsAgree()==0) {
                CwException.throwIt("已申请等待审核！");
            }
            if(mailLists.get(0).getIsAgree()==1) {
                CwException.throwIt("已经是好友！");
            }
        }

        return mailListDomainService.create(mailListDto).getId();
    }

    /**
     * 同意查看通讯录
     * @param mailListDto
     * @return
     */
    public MailListDto update(MailListDto mailListDto){
        return mailListDomainService.update(mailListDto).to(MailListDto.class);
    }

    /**
     * 查看通讯录详情
     * @param id
     * @return
     */
    public MailListDto findById(Long id){
        return mailListDomainService.findById(id).to(MailListDto.class);
    }

    /**
     * 查询是否是好朋友
     * @param dto
     * @return
     */
    public MailListDto findIsFriend(MailListDto dto){
        MailList mailList = mailListDomainService.findIsFriend(dto);
        if(mailList!=null){
           MailListDto mailListDto = mailList.to(MailListDto.class);
           return mailListDto;
        }
        return null;
    }

    /**
     * 查询用户通讯录
     * @return
     */
    public Page<MailListDto> findByCondition(MailListDto dto){
        return Pages.map(mailListDomainService.findByCondition(dto),MailListDto.class);
    }
}

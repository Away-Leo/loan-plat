package com.pingxun.biz.loandemand.app.service;

import com.pingxun.biz.CPContext;
import com.pingxun.biz.CwException;
import com.pingxun.biz.borrowdemand.app.dto.BorrowDemandDto;
import com.pingxun.biz.common.dto.Pages;
import com.pingxun.biz.loandemand.app.dto.LoanDemandDto;
import com.pingxun.biz.loandemand.domain.service.LoanDemandDomainService;
import com.pingxun.biz.maillist.app.dto.MailListDto;
import com.pingxun.biz.maillist.app.service.MailListAppService;
import com.pingxun.biz.user.app.dto.CwUserInfoDto;
import com.pingxun.biz.user.app.service.CwUserInfoAppService;
import com.pingxun.core.common.util.Utils;
import com.zds.common.lang.beans.Copier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * 借款放款需求服务
 * Created by dujy on 2017-05-20.
 */
@Transactional
@Service
public class LoanDemandAppService {

    @Autowired
    private LoanDemandDomainService loanDemandDomainService;

    @Autowired
    private MailListAppService mailListAppService;

    @Autowired
    private CwUserInfoAppService userInfoAppService;
    /**
     * 新增借款需求
     * @param loanDemandDto
     * @return
     */
    public Long create(LoanDemandDto loanDemandDto){
        return loanDemandDomainService.create(loanDemandDto).getId();
    }

    /**
     * 修改借款需求
     * @param loanDemandDto
     * @return
     */
    public LoanDemandDto update(LoanDemandDto loanDemandDto){
        return loanDemandDomainService.update(loanDemandDto).to(LoanDemandDto.class);
    }

    /**
     * 删除借款需求
     * @param loanDemandDto
     * @return
     */
    public LoanDemandDto disable(LoanDemandDto loanDemandDto){
        return loanDemandDomainService.disable(loanDemandDto).to(LoanDemandDto.class);
    }

    /**
     * 查询借款详情
     * @param id
     * @return
     */
    public LoanDemandDto findById(Long id,Boolean isMask){
        LoanDemandDto loanDemandDto = loanDemandDomainService.findById(id).to(LoanDemandDto.class);
        CwUserInfoDto cwUserInfoDto = userInfoAppService.findById(loanDemandDto.getUserId());
        //查询是否是好友
        MailListDto mailListDto = new MailListDto();
        mailListDto.setBorrowUserId(CPContext.getContext().getSeUserInfo().getId());
        mailListDto.setUserId(loanDemandDto.getUserId());
        MailListDto mailListDtoes = mailListAppService.findIsFriend(mailListDto);
        if(mailListDtoes==null){
            loanDemandDto.setIsFriend(Boolean.FALSE);
            loanDemandDto.setPhone(Utils.maskPhone(cwUserInfoDto.getPhone()));
        }else {
            loanDemandDto.setIsFriend(Boolean.TRUE);
            loanDemandDto.setPhone(cwUserInfoDto.getPhone());
        }

        loanDemandDto.setIdCard(Utils.maskCertId(cwUserInfoDto.getIdCard()));
        loanDemandDto.setAccountNo(Utils.maskAccountNo(cwUserInfoDto.getAccountNo()));
        loanDemandDto.setUserName(cwUserInfoDto.getName());
        loanDemandDto.setLoginPhone(cwUserInfoDto.getLoginPhone());

        return loanDemandDto;
    }


    /**
     * 查询借款人借款详情
     * @param borrowDemandDto
     * @return
     */
    public Page<LoanDemandDto> findByUserId(BorrowDemandDto borrowDemandDto){
        LoanDemandDto loanDemandDto = new LoanDemandDto();
        loanDemandDto.setUserId(borrowDemandDto.getUserId());
        loanDemandDto.setPageNo(borrowDemandDto.getPageNo()+1);
        return Pages.map(loanDemandDomainService.findByUserId(loanDemandDto),LoanDemandDto.class);
    }

    /**
     * 查询借款放款列表
     * @return
     */
    public Page<LoanDemandDto> findByCondition(LoanDemandDto dto){
        //会员等级
        Integer level = userInfoAppService.findById(CPContext.getContext().getSeUserInfo().getId()).getEmployeeLevel();
        dto.setEmployeeLevel(level);
        Page<LoanDemandDto>  loanDemandDtoPage = Pages.map(loanDemandDomainService.findByCondition(dto),LoanDemandDto.class);
        for(LoanDemandDto loanDemandDto: loanDemandDtoPage.getContent()){
            MailListDto mailListDto = new MailListDto();
            if(CPContext.getContext()==null){
                CwException.throwIt("未登录");
            }
            mailListDto.setBorrowUserId(CPContext.getContext().getSeUserInfo().getId());
            mailListDto.setUserId(loanDemandDto.getUserId());
            mailListDto = mailListAppService.findIsFriend(mailListDto);
            if(mailListDto==null){
                loanDemandDto.setIsFriend(Boolean.FALSE);
            }else {
                loanDemandDto.setIsFriend(Boolean.TRUE);
            }
        }

        return loanDemandDtoPage;
    }
}

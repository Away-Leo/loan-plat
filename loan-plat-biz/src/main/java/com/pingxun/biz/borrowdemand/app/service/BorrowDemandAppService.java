package com.pingxun.biz.borrowdemand.app.service;

import com.pingxun.biz.CPContext;
import com.pingxun.biz.borrowdemand.app.dto.BorrowDemandDto;
import com.pingxun.biz.borrowdemand.domain.service.BorrowDemandDomainService;
import com.pingxun.biz.common.dto.Pages;
import com.pingxun.biz.loandemand.app.dto.LoanBorrowRelationDto;
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

import javax.print.attribute.standard.Copies;
import javax.rmi.CORBA.Util;
import javax.transaction.Transactional;

/**
 * 借款放款需求服务
 * Created by dujy on 2017-05-20.
 */
@Transactional
@Service
public class BorrowDemandAppService {

    @Autowired
    private BorrowDemandDomainService borrowDemandDomainService;

    @Autowired
    private MailListAppService mailListAppService;

    @Autowired
    private CwUserInfoAppService userInfoAppService;
    /**
     * 新增借款需求
     * @param borrowDemandDto
     * @return
     */
    public Long create(BorrowDemandDto borrowDemandDto){

        return borrowDemandDomainService.create(borrowDemandDto).getId();
    }

    /**
     * 修改借款需求
     * @param borrowDemandDto
     * @return
     */
    public BorrowDemandDto update(BorrowDemandDto borrowDemandDto){
        return borrowDemandDomainService.update(borrowDemandDto).to(BorrowDemandDto.class);
    }

    /**
     * 修改借款需求
     * @param borrowDemandDto
     * @return
     */
    public BorrowDemandDto disable(BorrowDemandDto borrowDemandDto){
        return borrowDemandDomainService.disable(borrowDemandDto).to(BorrowDemandDto.class);
    }
    /**
     * 查询借款详情
     * @param id
     * @return
     */
    public BorrowDemandDto findById(Long id,Boolean isMark){
        BorrowDemandDto borrowDemandDto = borrowDemandDomainService.findById(id).to(BorrowDemandDto.class);
        CwUserInfoDto cwUserInfoDto = userInfoAppService.findById(borrowDemandDto.getUserId());
        borrowDemandDto.setIdCard(Utils.maskCertId(cwUserInfoDto.getIdCard()));
        borrowDemandDto.setAccountNo(Utils.maskAccountNo(cwUserInfoDto.getAccountNo()));
        borrowDemandDto.setUserName(borrowDemandDto.getUserName());
        MailListDto mailListDto = new MailListDto();
        mailListDto.setUserId(CPContext.getContext().getSeUserInfo().getId());
        mailListDto.setBorrowUserId(borrowDemandDto.getUserId());
        mailListDto = mailListAppService.findIsFriend(mailListDto);
        if(mailListDto==null){
            borrowDemandDto.setIsFriend(Boolean.FALSE);
            borrowDemandDto.setPhone(Utils.maskPhone(cwUserInfoDto.getPhone()));
        }else {
            borrowDemandDto.setIsFriend(Boolean.TRUE);
            borrowDemandDto.setPhone(cwUserInfoDto.getPhone());
        }
        borrowDemandDto.setLoginPhone(cwUserInfoDto.getLoginPhone());
        return borrowDemandDto;
    }
    /**
     * 查询借款详情
     * @param loanDemandDto
     * @return
     */
    public Page<BorrowDemandDto> findByUserId(LoanDemandDto loanDemandDto){
        BorrowDemandDto borrowDemandDto = new BorrowDemandDto();
        borrowDemandDto.setUserId(loanDemandDto.getUserId());
        borrowDemandDto.setPageNo(loanDemandDto.getPageNo()+1);
        return Pages.map(borrowDemandDomainService.findByUserId(borrowDemandDto),BorrowDemandDto.class);
    }

    /**
     * 查询借款放款列表
     * @return
     */
    public Page<BorrowDemandDto> findByCondition(BorrowDemandDto dto){
        dto = setIsLoanQuire(dto);
        Page<BorrowDemandDto>  borrowDemandDtoPage = Pages.map(borrowDemandDomainService.findByCondition(dto),BorrowDemandDto.class);
        for(BorrowDemandDto borrowDemandDto: borrowDemandDtoPage.getContent()){
            MailListDto mailListDto = new MailListDto();
            mailListDto.setUserId(CPContext.getContext().getSeUserInfo().getId());
            mailListDto.setBorrowUserId(borrowDemandDto.getUserId());
            mailListDto = mailListAppService.findIsFriend(mailListDto);
            if(mailListDto==null){
                borrowDemandDto.setIsFriend(Boolean.FALSE);
            }else {
                borrowDemandDto.setIsFriend(Boolean.TRUE);
            }
        }

        return borrowDemandDtoPage;
    }

    /**
     * 设置借款要求
     * @param borrowDemandDto
     * @return
     */
    private BorrowDemandDto setIsLoanQuire(BorrowDemandDto borrowDemandDto){

        if(!"9".equals(borrowDemandDto.getLoanRequire())&&borrowDemandDto.getLoanRequire()!=null){
            switch (borrowDemandDto.getLoanRequire()){
                case "1"://有微粒贷
                    borrowDemandDto.setIsWechatAmount(Boolean.TRUE);break;
                case "2"://有芝麻信用
                    borrowDemandDto.setIsSesameScore(Boolean.TRUE);break;
                case "3"://有社保
                    borrowDemandDto.setIsSocialSecurity(Boolean.TRUE);break;
                case "4"://有公积金
                    borrowDemandDto.setIsGjj(Boolean.TRUE);break;
                case "5"://有信用卡
                    borrowDemandDto.setIsCreditCard(Boolean.TRUE);break;
                case "6"://有房贷
                    borrowDemandDto.setIsHouse(Boolean.TRUE);break;
                case "7"://有车贷
                    borrowDemandDto.setIsCar(Boolean.TRUE);break;
            }
        }
        return borrowDemandDto;
    }
}

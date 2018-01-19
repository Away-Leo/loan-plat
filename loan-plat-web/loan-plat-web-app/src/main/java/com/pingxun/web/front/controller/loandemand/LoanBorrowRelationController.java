package com.pingxun.web.front.controller.loandemand;

import com.pingxun.biz.borrowdemand.app.dto.BorrowDemandDto;
import com.pingxun.biz.borrowdemand.app.service.BorrowDemandAppService;
import com.pingxun.biz.loandemand.app.dto.LoanBorrowRelationDto;
import com.pingxun.biz.loandemand.app.dto.LoanDemandDto;
import com.pingxun.biz.loandemand.app.service.LoanBorrowRelationAppService;
import com.pingxun.biz.loandemand.app.service.LoanDemandAppService;
import com.pingxun.biz.user.app.dto.CwUserInfoDto;
import com.pingxun.biz.user.app.service.CwUserInfoAppService;
import com.pingxun.core.common.util.Utils;
import com.pingxun.web.common.dto.CPViewResultInfo;
import com.pingxun.web.front.controller.AbstractFrontController;
import com.sun.org.apache.xpath.internal.operations.Bool;
import com.zds.common.lang.beans.Copier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * 借款关系需求接口
 * Created by dujy on 2017-05-20.
 */
@RestController
public class LoanBorrowRelationController extends AbstractFrontController {

    @Autowired
    private LoanBorrowRelationAppService loanBorrowRelationAppService;
    @Autowired
    private BorrowDemandAppService borrowDemandAppService;
    @Autowired
    private LoanDemandAppService loanDemandAppService;

    @Autowired
    private CwUserInfoAppService userInfoAppService;
    /**
     * 新增借款需求
     * @return
     */
    @PostMapping("/relation/create.json")
    public CPViewResultInfo create(@RequestBody LoanBorrowRelationDto loanDemandDto){
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        Long id = loanBorrowRelationAppService.create(loanDemandDto);
        cpViewResultInfo.setData(id);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("申请借款成功");
        return cpViewResultInfo;
    }

    /**
     * 修改交易状态
     * @param loanDemandDto
     * @return
     */
    @PostMapping("/relation/update.json")
    public CPViewResultInfo update(@RequestBody LoanBorrowRelationDto loanDemandDto){
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        LoanDemandDto loanDemandDto1 = loanBorrowRelationAppService.update(loanDemandDto);
        cpViewResultInfo.setData(loanDemandDto1.getId());
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("借款需求成功");

        return cpViewResultInfo;
    }

    /**
     * 出借人出借记录详情
     * @return
     */
    @GetMapping("/relation/findById.json")
    public CPViewResultInfo findById(Long id){
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        LoanBorrowRelationDto loanBorrowRelationDto = loanBorrowRelationAppService.findById(id);
        if(loanBorrowRelationDto.getBorrowId()!=null) {
            BorrowDemandDto borrowDemandDto = borrowDemandAppService.findById(loanBorrowRelationDto.getBorrowId(), Boolean.TRUE);
            loanBorrowRelationDto.setSocialIdentity(borrowDemandDto.getSocialIdentity());
            loanBorrowRelationDto.setIsSesameScore(borrowDemandDto.getIsSesameScore());
            loanBorrowRelationDto.setIsWechatAmount(borrowDemandDto.getIsWechatAmount());
            loanBorrowRelationDto.setIsSocialSecurity(borrowDemandDto.getIsSocialSecurity());
            loanBorrowRelationDto.setIsCreditCard(borrowDemandDto.getIsCreditCard());
            loanBorrowRelationDto.setIsCar(borrowDemandDto.getIsCar());
            loanBorrowRelationDto.setIsHouse(borrowDemandDto.getIsHouse());
            loanBorrowRelationDto.setIsGjj(borrowDemandDto.getIsGjj());
            loanBorrowRelationDto.setHeadImg(borrowDemandDto.getHeadImg());
            loanBorrowRelationDto.setUserName(borrowDemandDto.getUserName());
            loanBorrowRelationDto.setLoanPurpose(borrowDemandDto.getLoanPurpose());
            loanBorrowRelationDto.setSalary(borrowDemandDto.getSalary());
            loanBorrowRelationDto.setLoanFee(borrowDemandDto.getLoanFee());
            loanBorrowRelationDto.setLoanPeriod(borrowDemandDto.getLoanPeriod());
            loanBorrowRelationDto.setLinkAddress(borrowDemandDto.getLinkAddress());

            CwUserInfoDto cwUserInfoDto = userInfoAppService.findById(loanBorrowRelationDto.getBorrowUserId());
            loanBorrowRelationDto.setLoginPhone(cwUserInfoDto.getLoginPhone());
        }else{
            CwUserInfoDto cwUserInfoDto = userInfoAppService.findById(loanBorrowRelationDto.getBorrowUserId());
            loanBorrowRelationDto.setSocialIdentity(cwUserInfoDto.getSocialIdentity());
            loanBorrowRelationDto.setIsSesameScore(cwUserInfoDto.getIsSesameScore());
            loanBorrowRelationDto.setIsWechatAmount(cwUserInfoDto.getIsWechatAmount());
            loanBorrowRelationDto.setIsSocialSecurity(cwUserInfoDto.getIsSocialSecurity());
            loanBorrowRelationDto.setIsCreditCard(cwUserInfoDto.getIsCreditCard());
            loanBorrowRelationDto.setIsCar(cwUserInfoDto.getIsCar());
            loanBorrowRelationDto.setIsHouse(cwUserInfoDto.getIsHouse());
            loanBorrowRelationDto.setIsGjj(cwUserInfoDto.getIsGjj());
            loanBorrowRelationDto.setHeadImg(cwUserInfoDto.getHeadImg());
            loanBorrowRelationDto.setUserName(cwUserInfoDto.getName());
            loanBorrowRelationDto.setSalary(cwUserInfoDto.getSalary());
            loanBorrowRelationDto.setLinkAddress(cwUserInfoDto.getLinkAddress());
        }

        loanBorrowRelationDto.setIdCard(Utils.maskCertId(loanBorrowRelationDto.getIdCard()));
        loanBorrowRelationDto.setAccountNo(Utils.maskAccountNo(loanBorrowRelationDto.getAccountNo()));
        loanBorrowRelationDto.setUserName(loanBorrowRelationDto.getUserName());
        cpViewResultInfo.setData(loanBorrowRelationDto);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("成功");
        return cpViewResultInfo;
    }

    /**
     * 借款人借款记录详情
     * @return
     */
    @GetMapping("/relation/findLoanById.json")
    public CPViewResultInfo findLoanById(Long id){
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        LoanBorrowRelationDto loanBorrowRelationDto = loanBorrowRelationAppService.findById(id);
        LoanDemandDto loanDemandDto = loanDemandAppService.findById(loanBorrowRelationDto.getLoanId(),Boolean.TRUE);
        CwUserInfoDto cwUserInfoDto = userInfoAppService.findById(loanBorrowRelationDto.getLoanUserId());
        loanBorrowRelationDto.setMonthRate(loanDemandDto.getMonthRate());
        loanBorrowRelationDto.setLoanStartFee(loanDemandDto.getLoanStartFee());
        loanBorrowRelationDto.setLoanEndFee(loanDemandDto.getLoanEndFee());
        loanBorrowRelationDto.setLoanStartPeriod(loanDemandDto.getLoanStartPeriod());
        loanBorrowRelationDto.setLoanEndPeriod(loanDemandDto.getLoanEndPeriod());
        loanBorrowRelationDto.setAreaName(loanDemandDto.getAreaName());
        loanBorrowRelationDto.setIdCard(Utils.maskCertId(loanDemandDto.getIdCard()));
        loanBorrowRelationDto.setAccountNo(Utils.maskAccountNo(loanDemandDto.getAccountNo()));
        loanBorrowRelationDto.setUserName(loanDemandDto.getUserName());
        loanBorrowRelationDto.setLoginPhone(cwUserInfoDto.getLoginPhone());
        if(loanBorrowRelationDto.getStatus()!=1){
            loanBorrowRelationDto.setPhone(Utils.maskPhone(loanDemandDto.getPhone()));
        }else {
            loanBorrowRelationDto.setPhone(loanDemandDto.getPhone());
        }
        loanBorrowRelationDto.setPublishDate(loanDemandDto.getPublishDate());
        cpViewResultInfo.setData(loanBorrowRelationDto);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("成功");
        return cpViewResultInfo;
    }

    /**
     * 我的借款记录
     * @param loanBorrowRelationDto
     * @return
     */
    @GetMapping("/relation/borrowList.json")
    @ResponseBody
    public CPViewResultInfo borrowList(LoanBorrowRelationDto loanBorrowRelationDto){
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        loanBorrowRelationDto.setLoanType(1);
        Page<LoanBorrowRelationDto> loanDemandDtoList = loanBorrowRelationAppService.findByCondition(loanBorrowRelationDto);
        for(LoanBorrowRelationDto dto:loanDemandDtoList.getContent()){
            if(dto.getLoanId()!=null) {
                LoanDemandDto loanDemandDto = loanDemandAppService.findById(dto.getLoanId(), Boolean.TRUE);
                dto.setMonthRate(loanDemandDto.getMonthRate());
                dto.setLoanStartFee(loanDemandDto.getLoanStartFee());
                dto.setLoanEndFee(loanDemandDto.getLoanEndFee());
                dto.setLoanStartPeriod(loanDemandDto.getLoanStartPeriod());
                dto.setLoanEndPeriod(loanDemandDto.getLoanEndPeriod());
                dto.setAreaName(loanDemandDto.getAreaName());
                dto.setIdCard(Utils.maskCertId(loanDemandDto.getIdCard()));
                dto.setAccountNo(Utils.maskAccountNo(loanDemandDto.getAccountNo()));
                dto.setUserName(loanDemandDto.getUserName());
            }else {
                CwUserInfoDto cwUserInfoDto = userInfoAppService.findById(dto.getLoanUserId());
                dto.setUserName(cwUserInfoDto.getName());
                dto.setHeadImg(cwUserInfoDto.getHeadImg());
                dto.setUserName(cwUserInfoDto.getName());
                dto.setSalary(cwUserInfoDto.getSalary());
            }
        }
        cpViewResultInfo.setData(loanDemandDtoList);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("成功");
        return cpViewResultInfo;
    }

    /**
     * 我的出借记录
     * @param loanBorrowRelationDto
     * @return
     */
    @GetMapping("/relation/loanList.json")
    @ResponseBody
    public CPViewResultInfo loanList(LoanBorrowRelationDto loanBorrowRelationDto){
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        loanBorrowRelationDto.setLoanType(2);
        Page<LoanBorrowRelationDto> viewBorrowApplyList = loanBorrowRelationAppService.findByCondition(loanBorrowRelationDto);
        for(LoanBorrowRelationDto dto:viewBorrowApplyList.getContent()){
            if(dto.getBorrowId()!=null){
                BorrowDemandDto borrowDto = borrowDemandAppService.findById(dto.getBorrowId(), Boolean.TRUE);
                dto.setSocialIdentity(borrowDto.getSocialIdentity());
                dto.setIsSesameScore(borrowDto.getIsSesameScore());
                dto.setIsWechatAmount(borrowDto.getIsWechatAmount());
                dto.setIsSocialSecurity(borrowDto.getIsSocialSecurity());
                dto.setIsCreditCard(borrowDto.getIsCreditCard());
                dto.setIsCar(borrowDto.getIsCar());
                dto.setIsHouse(borrowDto.getIsHouse());
                dto.setIsGjj(borrowDto.getIsGjj());
                dto.setHeadImg(borrowDto.getHeadImg());
                dto.setUserName(borrowDto.getUserName());
                dto.setLoanPurpose(borrowDto.getLoanPurpose());
                dto.setLoanFee(borrowDto.getLoanFee());
                dto.setLoanPeriod(borrowDto.getLoanPeriod());
                dto.setSalary(borrowDto.getSalary());
                dto.setLinkAddress(borrowDto.getLinkAddress());
            }else{
                CwUserInfoDto cwUserInfoDto = userInfoAppService.findById(dto.getBorrowUserId());
                dto.setUserName(cwUserInfoDto.getName());
                dto.setSocialIdentity(cwUserInfoDto.getSocialIdentity());
                dto.setAreaName(cwUserInfoDto.getAreaName());
                dto.setSalary(cwUserInfoDto.getSalary());
                dto.setIsWechatAmount(cwUserInfoDto.getIsWechatAmount());
                dto.setIsSocialSecurity(cwUserInfoDto.getIsSocialSecurity());
                dto.setIsCreditCard(cwUserInfoDto.getIsCreditCard());
                dto.setLinkAddress(cwUserInfoDto.getLinkAddress());
            }
        }
        cpViewResultInfo.setData(viewBorrowApplyList);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("成功");
        return cpViewResultInfo;
    }

}

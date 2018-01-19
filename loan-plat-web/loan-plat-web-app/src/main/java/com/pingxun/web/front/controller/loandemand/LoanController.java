package com.pingxun.web.front.controller.loandemand;

import com.pingxun.biz.CPContext;
import com.pingxun.biz.borrowdemand.app.dto.BorrowDemandDto;
import com.pingxun.biz.borrowdemand.app.service.BorrowDemandAppService;
import com.pingxun.biz.loandemand.app.dto.LoanBorrowRelationDto;
import com.pingxun.biz.loandemand.app.dto.LoanDemandDto;
import com.pingxun.biz.loandemand.app.service.LoanDemandAppService;
import com.pingxun.biz.user.app.service.CwUserInfoAppService;
import com.pingxun.web.common.dto.CPViewResultInfo;
import com.pingxun.web.front.controller.AbstractFrontController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * 借款需求接口
 * Created by dujy on 2017-05-20.
 */
@RestController
public class LoanController extends AbstractFrontController {

    @Autowired
    private LoanDemandAppService loanDemandAppService;

    @Autowired
    private BorrowDemandAppService borrowDemandAppService;

    @Autowired
    private CwUserInfoAppService cwUserInfoAppService;
    /**
     * 新增借款需求
     * @return
     */
    @PostMapping("/loan/create.json")
    public CPViewResultInfo create(@RequestBody LoanDemandDto loanDemandDto){
        loanDemandDto.setUserId(CPContext.getContext().getSeUserInfo().getId());
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        try{
            cwUserInfoAppService.checkVip(CPContext.getContext().getSeUserInfo().getId());
            Long id = loanDemandAppService.create(loanDemandDto);
            cpViewResultInfo.setData(id);
            cpViewResultInfo.setSuccess(true);
            cpViewResultInfo.setMessage("出借需求发布成功");
        }catch (Exception e){
            cpViewResultInfo.setSuccess(false);
            cpViewResultInfo.setMessage(e.getMessage());
        }
        return cpViewResultInfo;
    }

    /**
     * 修改借款需求
     * @return
     */
    @PostMapping("/loan/update.json")
    public CPViewResultInfo update(@RequestBody LoanDemandDto loanDemandDto){
        loanDemandDto.setUserId(CPContext.getContext().getSeUserInfo().getId());
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        LoanDemandDto loanDemandDtoResult = loanDemandAppService.update(loanDemandDto);
        cpViewResultInfo.setData(loanDemandDtoResult.getId());
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("成功");

        return cpViewResultInfo;
    }

    /**
     * 删除出借需求
     * @return
     */
    @PostMapping("/loan/delete.json")
    public CPViewResultInfo delete(@RequestBody LoanDemandDto loanDemandDto){
        loanDemandDto.setUserId(CPContext.getContext().getSeUserInfo().getId());
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        LoanDemandDto loanDemandDtoResult = loanDemandAppService.disable(loanDemandDto);
        cpViewResultInfo.setData(loanDemandDtoResult.getId());
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("删除成功");

        return cpViewResultInfo;
    }

    /**
     * 查看借款放款需求
     * @return
     */
    @GetMapping("/loan/findById.json")
    public CPViewResultInfo findById(Long id){
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        LoanDemandDto loanDemandDto = loanDemandAppService.findById(id,Boolean.TRUE);
        cpViewResultInfo.setData(loanDemandDto);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("查看成功");
        return cpViewResultInfo;
    }

    /**
     * 放款人通讯录查看借款需求
     * @return
     */
    @GetMapping("/loan/findByUserId.json")
    public CPViewResultInfo findByUserId(LoanDemandDto loanDemandDto){
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        Page<BorrowDemandDto> borrowDemandDto = borrowDemandAppService.findByUserId(loanDemandDto);
        cpViewResultInfo.setData(borrowDemandDto);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("通讯录查看借款成功");
        return cpViewResultInfo;
    }

    /**
     * 查询放款需求
     * @param loanDemandDto
     * @return
     */
    @PostMapping("/loan/findByCondition.json")
    @ResponseBody
    public CPViewResultInfo findByCondition(@RequestBody LoanDemandDto loanDemandDto){
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        Page<LoanDemandDto> loanDemandDtoList = loanDemandAppService.findByCondition(loanDemandDto);
        cpViewResultInfo.setData(loanDemandDtoList);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("成功");
        return cpViewResultInfo;
    }

    /**
     * 我的出借需求
     * @param loanDemandDto
     * @return
     */
    @GetMapping("/loan/loanList.json")
    @ResponseBody
    public CPViewResultInfo loanList(LoanDemandDto loanDemandDto){
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        loanDemandDto.setUserId(CPContext.getContext().getSeUserInfo().getId());
        Page<LoanDemandDto> loanDemandDtoList = loanDemandAppService.findByCondition(loanDemandDto);
        cpViewResultInfo.setData(loanDemandDtoList);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("成功");
        return cpViewResultInfo;
    }

}

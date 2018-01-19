package com.pingxun.web.front.controller.borrowdemand;

import com.pingxun.biz.CPContext;
import com.pingxun.biz.borrowdemand.app.dto.BorrowDemandDto;
import com.pingxun.biz.borrowdemand.app.service.BorrowDemandAppService;
import com.pingxun.biz.loandemand.app.dto.LoanDemandDto;
import com.pingxun.biz.loandemand.app.service.LoanDemandAppService;
import com.pingxun.web.common.dto.CPViewResultInfo;
import com.pingxun.web.front.controller.AbstractFrontController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * 借款人需求接口
 * Created by dujy on 2017-05-20.
 */
@RestController
public class BorrowController extends AbstractFrontController {

    @Autowired
    private LoanDemandAppService loanDemandAppService;

    @Autowired
    private BorrowDemandAppService borrowDemandAppService;
    /**
     * 新增借款需求
     * @return
     */
    @PostMapping("/borrow/create.json")
    public CPViewResultInfo create(@RequestBody BorrowDemandDto borrowDemandDto){
        borrowDemandDto.setUserId(CPContext.getContext().getSeUserInfo().getId());
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        Long id = borrowDemandAppService.create(borrowDemandDto);
        cpViewResultInfo.setData(id);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("发布成功");

        return cpViewResultInfo;
    }

    /**
     * 修改借款需求
     * @return
     */
    @PostMapping("/borrow/update.json")
    public CPViewResultInfo update(@RequestBody BorrowDemandDto borrowDemandDto){
        borrowDemandDto.setUserId(CPContext.getContext().getSeUserInfo().getId());
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        BorrowDemandDto borrowDemandDto1 = borrowDemandAppService.update(borrowDemandDto);
        cpViewResultInfo.setData(borrowDemandDto1.getId());
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("成功");

        return cpViewResultInfo;
    }

    /**
     * 删除出借需求
     * @return
     */
    @PostMapping("/borrow/delete.json")
    public CPViewResultInfo delete(@RequestBody BorrowDemandDto borrowDemandDto){
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        BorrowDemandDto borrow = borrowDemandAppService.disable(borrowDemandDto);
        cpViewResultInfo.setData(borrow.getId());
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("删除成功");

        return cpViewResultInfo;
    }

    /**
     * 查看借款放款需求
     * @return
     */
    @GetMapping("/borrow/findById.json")
    public CPViewResultInfo findById(Long id){
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        BorrowDemandDto borrowDemandDto= borrowDemandAppService.findById(id,Boolean.TRUE);
        cpViewResultInfo.setData(borrowDemandDto);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("查看成功");
        return cpViewResultInfo;
    }

    /**
     * 放款人通讯录查看借款人借款需求
     * @return
     */
    @GetMapping("/borrow/findByUserId.json")
    public CPViewResultInfo findByUserId(BorrowDemandDto borrowDemandDto){
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        Page<LoanDemandDto> loanDemandDto = loanDemandAppService.findByUserId(borrowDemandDto);
        cpViewResultInfo.setData(loanDemandDto);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("通讯录查看借款成功");
        return cpViewResultInfo;
    }

    /**
     * 查询放款需求
     * @param borrowDemandDto
     * @return
     */
    @PostMapping("/borrow/list.json")
    @ResponseBody
    public CPViewResultInfo findByCondition(@RequestBody BorrowDemandDto borrowDemandDto){
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        Page<BorrowDemandDto> loanDemandDtoList = borrowDemandAppService.findByCondition(borrowDemandDto);
        cpViewResultInfo.setData(loanDemandDtoList);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("成功");
        return cpViewResultInfo;
    }

    /**
     * 我的借款需求
     * @param borrowDemandDto
     * @return
     */
    @GetMapping("/borrow/borrowList.json")
    @ResponseBody
    public CPViewResultInfo borrowList(BorrowDemandDto borrowDemandDto){
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        borrowDemandDto.setUserId(CPContext.getContext().getSeUserInfo().getId());
        Page<BorrowDemandDto> loanDemandDtoList = borrowDemandAppService.findByCondition(borrowDemandDto);
        cpViewResultInfo.setData(loanDemandDtoList);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("成功");
        return cpViewResultInfo;
    }
}

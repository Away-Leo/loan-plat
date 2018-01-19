package com.pingxun.web.front.controller.maillist;

import com.pingxun.biz.CPContext;
import com.pingxun.biz.maillist.app.dto.MailListDto;
import com.pingxun.biz.maillist.app.service.MailListAppService;
import com.pingxun.biz.user.app.dto.CwUserInfoDto;
import com.pingxun.biz.user.app.service.CwUserInfoAppService;
import com.pingxun.web.common.dto.CPViewResultInfo;
import com.pingxun.web.front.controller.AbstractFrontController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * 消息服务
 * Created by dujy on 2017-05-20.
 */
@RestController
public class MailListController extends AbstractFrontController {

    @Autowired
    private MailListAppService mailListAppService;

    @Autowired
    private CwUserInfoAppService cwUserInfoAppService;

    private static Logger logger= LoggerFactory.getLogger(MailListController.class);

    /**
     * 申请查看通讯录
     * @param mailListDto
     * @return
     */
    @PostMapping("/maillist/create.json")
    @ResponseBody
    public CPViewResultInfo findById(@RequestBody MailListDto mailListDto){
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        try{
            cwUserInfoAppService.checkVip(CPContext.getContext().getSeUserInfo().getId());
            Long id = mailListAppService.create(mailListDto);
            cpViewResultInfo.setData(id);
            cpViewResultInfo.setSuccess(true);
            cpViewResultInfo.setMessage("申请成功");
        }catch (Exception e){
            cpViewResultInfo.setSuccess(false);
            cpViewResultInfo.setMessage(e.getMessage());
            logger.error("创建申请记录关系出错",e);
            e.printStackTrace();
        }
        return cpViewResultInfo;
    }

    /**
     * 放款人同意或者拒绝查看通讯录
     * @param mailListDto
     * @return
     */
    @PostMapping("/maillist/loanAgree.json")
    @ResponseBody
    public CPViewResultInfo loanAgree(@RequestBody MailListDto mailListDto){
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        mailListDto.setUserId(CPContext.getContext().getSeUserInfo().getId());
        MailListDto mailListDto1 = mailListAppService.update(mailListDto);
        cpViewResultInfo.setData(mailListDto1);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("操作成功");
        return cpViewResultInfo;
    }

    /**
     * 借款人同意或者拒绝查看通讯录
     * @param mailListDto
     * @return
     */
    @PostMapping("/maillist/borrowAgree.json")
    @ResponseBody
    public CPViewResultInfo borrowAgree(@RequestBody MailListDto mailListDto){
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        mailListDto.setBorrowUserId(CPContext.getContext().getSeUserInfo().getId());
        MailListDto mailListDto1 = mailListAppService.update(mailListDto);
        cpViewResultInfo.setData(mailListDto1);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("操作成功");
        return cpViewResultInfo;
    }

    /**
     * 查询我的通讯录
     * @param mailListDto
     * @return
     */
    @PostMapping("/maillist/findByCondition.json")
    @ResponseBody
    public CPViewResultInfo findByCondition(@RequestBody MailListDto mailListDto){
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        Page<MailListDto> mailListDtoPage = mailListAppService.findByCondition(mailListDto);
        cpViewResultInfo.setData(mailListDtoPage);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("成功");
        return cpViewResultInfo;
    }

}

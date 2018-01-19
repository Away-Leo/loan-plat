package com.pingxun.web.front.controller.user;

import com.pingxun.biz.CPContext;
import com.pingxun.biz.maillist.app.dto.MailListDto;
import com.pingxun.biz.maillist.app.service.MailListAppService;
import com.pingxun.biz.user.app.dto.CwUserInfoDto;
import com.pingxun.biz.user.app.service.CwUserInfoAppService;
import com.pingxun.core.common.util.Utils;
import com.pingxun.web.common.component.UploadFileComponent;
import com.pingxun.web.common.dto.CPViewResultInfo;
import com.pingxun.web.front.controller.AbstractFrontController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 用户个人信息接口
 * Created by dujy on 2017-05-20.
 */
@RestController
public class CwUserController extends AbstractFrontController {

    @Autowired
    private CwUserInfoAppService cwUserInfoAppService;


    @Autowired
    private UploadFileComponent uploadFileComponent;

    @Autowired
    private MailListAppService mailListAppService;
    /**
     * 新增或者修改客户信息
     * @return
     */
    @PostMapping("/userInfo/update.json")
    public CPViewResultInfo update(@RequestBody CwUserInfoDto cwUserInfoDto){
        cwUserInfoDto.setUserId(CPContext.getContext().getSeUserInfo().getId());
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        Long id = cwUserInfoAppService.update(cwUserInfoDto);
        cpViewResultInfo.setData(cwUserInfoAppService.findById(cwUserInfoDto.getUserId()));
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("保存成功");

        return cpViewResultInfo;
    }

    /**
     * 认证成功接口
     * @return
     */
    @PostMapping("/userInfo/authenticationSuccess.json")
    public CPViewResultInfo authenticationSuccess(@RequestBody CwUserInfoDto cwUserInfoDto){
        cwUserInfoDto.setUserId(CPContext.getContext().getSeUserInfo().getId());
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        Long id = cwUserInfoAppService.authenticationSuccess(cwUserInfoDto);
        cpViewResultInfo.setData(cwUserInfoAppService.findById(id).getIsPay());
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("认证成功");

        return cpViewResultInfo;
    }

    /**
     * 支付成功接口
     * @return
     */
    @PostMapping("/userInfo/paySuccess.json")
    public CPViewResultInfo paySuccess(@RequestBody CwUserInfoDto cwUserInfoDto){
        cwUserInfoDto.setUserId(CPContext.getContext().getSeUserInfo().getId());
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        Long id = cwUserInfoAppService.paySuccess(cwUserInfoDto);
        cpViewResultInfo.setData(cwUserInfoAppService.findById(id).getIsPay());
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("支付成功");

        return cpViewResultInfo;
    }

    /**
     * 消息开关提示
     * @return
     */
    @GetMapping("/userInfo/updateMessageTip.json")
    public CPViewResultInfo updateMessageTip(Boolean messageTip){
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();

        CwUserInfoDto cwUserInfoDto = new CwUserInfoDto();
        cwUserInfoDto.setUserId(CPContext.getContext().getSeUserInfo().getId());
        cwUserInfoDto.setMessageTipFlag(messageTip);
        Long id = cwUserInfoAppService.updateMessageTip(cwUserInfoDto).getId();
        cpViewResultInfo.setData(id);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("设置成功");

        return cpViewResultInfo;
    }

    /**
     * 查询认证信息接口
     * @return
     */
    @GetMapping("/userInfo/findAuthInfoById.json")
    public CPViewResultInfo findAuthInfoById(){
        Long id = CPContext.getContext().getSeUserInfo().getId();
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        CwUserInfoDto cwUserInfoDto = cwUserInfoAppService.findAuthInfoById(id);
        cpViewResultInfo.setData(cwUserInfoDto);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("成功");
        return cpViewResultInfo;
    }

    /**
     * 查询用户信息
     * @return
     */
    @GetMapping("/userInfo/findById.json")
    public CPViewResultInfo findById(){
        Long id = CPContext.getContext().getSeUserInfo().getId();
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        CwUserInfoDto cwUserInfoDto = cwUserInfoAppService.findById(id);
        cpViewResultInfo.setData(cwUserInfoDto);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("成功");
        return cpViewResultInfo;
    }

    /**
     * 查询指定用户的信息
     * @return
     */
    @GetMapping("/userInfo/findByUserId.json")
    public CPViewResultInfo findById(Long id){
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        CwUserInfoDto cwUserInfoDto = cwUserInfoAppService.findById(id);
        MailListDto mailListDto = new MailListDto();
        if(cwUserInfoDto.getUserType()==1) {
            mailListDto.setBorrowUserId(CPContext.getContext().getSeUserInfo().getId());
            mailListDto.setUserId(id);
        }else{
            mailListDto.setBorrowUserId(id);
            mailListDto.setUserId(CPContext.getContext().getSeUserInfo().getId());
        }
        MailListDto mailListDto1 = mailListAppService.findIsFriend(mailListDto);
        if(mailListDto1!=null) {
            if (mailListDto1.getIsAgree() != 1) {
                cwUserInfoDto.setPhone(Utils.maskPhone(cwUserInfoDto.getPhone()));
            }
        }else{
            cwUserInfoDto.setPhone(Utils.maskPhone(cwUserInfoDto.getPhone()));
        }
        cpViewResultInfo.setData(cwUserInfoDto);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("成功");
        return cpViewResultInfo;
    }

    /**
     * 头像上传
     * @param file
     * @return
     */
    @PostMapping("/userInfo/upload.json")
    @ResponseBody
    public CPViewResultInfo upload(MultipartFile file) {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        String filePath = uploadFileComponent.upload(file);
        CwUserInfoDto cwUserInfoDto = new CwUserInfoDto();
        cwUserInfoDto.setHeadImg(filePath);
        cwUserInfoDto.setUserId(CPContext.getContext().getSeUserInfo().getId());
        Long id = cwUserInfoAppService.update(cwUserInfoDto);
        cpViewResultInfo.setData(filePath);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("上传成功");
        return cpViewResultInfo;
    }
}

package com.pingxun.web.backend.controller.user;


import com.pingxun.biz.CPContext;
import com.pingxun.biz.common.dto.ListParamDto;
import com.pingxun.biz.jdbc.JdbcPage;
import com.pingxun.biz.user.app.dto.UserPasswordDto;
import com.pingxun.biz.user.domain.entity.SeResource;
import com.pingxun.biz.user.domain.entity.SeUser;
import com.pingxun.biz.user.domain.service.PasswordHelper;
import com.pingxun.biz.user.domain.service.SeUserService;
import com.pingxun.web.backend.controller.AbstractBackendController;
import com.pingxun.web.common.dto.CPViewResultInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 *用户管理
 */
@RestController
public class UserController extends AbstractBackendController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private SeUserService seUserService;
    @Autowired
    private PasswordHelper passwordHelper;
    /**
     * 用户登录
     * @param listParamDto
     * @return
     */
    @PostMapping("/user/findByCondition.json")
    @ResponseBody
    public CPViewResultInfo userList(@RequestBody ListParamDto listParamDto) {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        listParamDto.setMerchantId(1L);
        JdbcPage jdbcPage = seUserService.findByPage(listParamDto);
        cpViewResultInfo.setData(jdbcPage);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("查询成功");
        return cpViewResultInfo;
    }

    /**
     * 新增用户
     * @param seUser
     * @return
     */
    @PostMapping("/user/create.json")
    @ResponseBody
    public CPViewResultInfo add(@RequestBody SeUser seUser) {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        seUser.setMerchantId(1L);
        seUser.setrId(1L);
        SeUser seUser1 = seUserService.createUser(seUser);
        cpViewResultInfo.setData(seUser1.getId());
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("保存成功");
        return cpViewResultInfo;
    }

    /**
     * 查询用户信息
     * @return
     */
    @GetMapping("/user/findById.json")
    @ResponseBody
    public CPViewResultInfo findById() {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        Long id = CPContext.getContext().getSeUserInfo().getId();
        SeUser seUser1 = seUserService.findOne(id);
        cpViewResultInfo.setData(seUser1);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("查询成功");
        return cpViewResultInfo;
    }


    /**
     * 修改用户
     * @param seUser
     * @return
     */
    @PostMapping("/user/update.json")
    @ResponseBody
    public CPViewResultInfo update(@RequestBody SeUser seUser) {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        seUser.setMerchantId(1L);
        seUser.setrId(1L);
        SeUser seUser1 = seUserService.updateUser(seUser,Boolean.TRUE);
        cpViewResultInfo.setData(seUser1.getId());
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("修改成功");
        return cpViewResultInfo;
    }

    /**
     * 锁定用户
     * @param seUser
     * @return
     */
    @PostMapping("/user/lock.json")
    @ResponseBody
    public CPViewResultInfo lock(@RequestBody SeUser seUser) {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        SeUser seUser1 = seUserService.findOne(seUser.getId());
        if(!seUser1.getLocked()) {
            seUserService.lock(seUser.getId());
        }else{
            seUserService.unlock(seUser.getId());
        }
        cpViewResultInfo.setData(seUser1.getId());
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("成功");
        return cpViewResultInfo;
    }

    /**
     * 查询用权限
     * @return
     */
    @GetMapping("/user/getUserPermission.json")
    @ResponseBody
    public CPViewResultInfo getUserPermission() {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        Set<SeResource> permissions = seUserService.findPermissions(CPContext.getContext().getSeUserInfo().getUsername(),1L);
        cpViewResultInfo.setData(permissions);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("成功");
        return cpViewResultInfo;
    }

    /**
     * 修改用户密码
     * @param userPasswordDto
     * @return
     */
    @PostMapping("/user/updatePassword.json")
    @ResponseBody
    public CPViewResultInfo updatePassword(@RequestBody UserPasswordDto userPasswordDto) {
        String message="修改成功";
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        if(!userPasswordDto.getPassword().equals(userPasswordDto.getConfirmPassword())){
            message = "二次密码输入不一致！";
            cpViewResultInfo.setSuccess(false);
        }else{
            SeUser seUser = seUserService.findOne(CPContext.getContext().getSeUserInfo().getId());
            String encryptPassword = passwordHelper.encryptOldPassword(seUser,userPasswordDto.getOldPassword());
            if(encryptPassword.equals(seUser.getPassword())) {
                seUser.setPassword(userPasswordDto.getPassword());
                SeUser seUser1 = seUserService.updateUser(seUser, Boolean.TRUE);
                cpViewResultInfo.setData(seUser1.getId());
                cpViewResultInfo.setSuccess(true);
            }else{
                message="旧密码不正确";
                cpViewResultInfo.setSuccess(false);
            }
        }
        cpViewResultInfo.setMessage(message);
        return cpViewResultInfo;
    }

}

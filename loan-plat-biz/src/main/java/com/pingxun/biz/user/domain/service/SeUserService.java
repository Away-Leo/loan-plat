package com.pingxun.biz.user.domain.service;


import com.pingxun.biz.CwException;
import com.pingxun.biz.common.dto.ListParamDto;
import com.pingxun.biz.jdbc.JdbcPage;
import com.pingxun.biz.log.app.LogEnum;
import com.pingxun.biz.log.app.dto.LogDto;
import com.pingxun.biz.log.app.service.LogAppService;
import com.pingxun.biz.user.app.dto.RegisterDto;
import com.pingxun.biz.user.domain.dao.SeUserDao;
import com.pingxun.biz.user.domain.entity.SeResource;
import com.pingxun.biz.user.domain.entity.SeRole;
import com.pingxun.biz.user.domain.entity.SeUser;
import com.pingxun.core.common.util.Utils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class SeUserService {

    @Autowired
    private SeUserDao seUserDao;
    @Autowired
    private PasswordHelper passwordHelper;
    @Autowired
    private SeRoleService roleService;
    @Autowired
    private LogAppService logAppService;
    /**
     * 创建用户
     *
     * @param user
     */
    public SeUser createUser(SeUser user) {
        //加密密码
        passwordHelper.encryptPassword(user);
        return seUserDao.createUser(user);
    }

    /**
     * 发送验证码
     * @param registerDto
     */
    public String sendVerify(RegisterDto registerDto)
    {
        //随机数
        String randomNum = Utils.randomStr();

        SeUser seUser1 = seUserDao.findByUsernameAndMerchantId(registerDto.getPhone(),1L);
        //贷款超市导流用户注册
        if("H5".equals(registerDto.getChannelNo()))
        {
            if(seUser1==null) {
                //注册用户数
                SeUser newUser = new SeUser();
                newUser.setDisplayName(registerDto.getPhone());
                newUser.setUsername(registerDto.getPhone());
                newUser.setPhone(registerDto.getPhone());
                newUser.setMerchantId(1L);
                newUser.setType("user");
                newUser.setrId(1L);
                newUser.setPassword(randomNum + "");
                SeUser seUser = createUser(newUser);
                //记录注册日志
                recordLog(seUser, registerDto);
            }else{
                CwException.throwIt("已注册请直接用App登录");
            }
        }else {
            //忘记密码发送短信
            if (seUser1 == null) {
                CwException.throwIt("用户不存在");
            } else {
                if (!"user".equals(seUser1.getType())) {
                    CwException.throwIt("用户类型不匹配");
                }
                if (seUser1.getVerifyExpDate() != null) {
                    if (seUser1.getVerifyExpDate().compareTo(new Date()) > 2) {
                        CwException.throwIt("验证码失效");
                    }
                }
                //修改验证码
                seUser1.setVerifyCode(randomNum);
                seUserDao.updateUserVerifyCode(seUser1);
            }
        }
        return randomNum;
    }

    /**
     * 记录注册日志
     * @param seUser
     */
    private void recordLog(SeUser seUser,RegisterDto registerDto){
        LogDto logDto = new LogDto();
        logDto.setType(LogEnum.USER_REGISTER);
        logDto.setDeviceNumber(seUser.getPhone());
        logDto.setChannelNo(registerDto.getChannelNo());
        logDto.setUserId(seUser.getId());
        logDto.setAppName(registerDto.getAppName());
        logDto.setApplyArea(registerDto.getApplyArea());
        logAppService.create(logDto);
    }

    /**
     * 修改用户
     * @param user
     * @param isReEncryptPassword
     * @return
     */
    public SeUser updateUser(SeUser user, Boolean isReEncryptPassword) {
        Assert.notNull(user.getId());
        SeUser saved = seUserDao.findOne(user.getId());
        Assert.notNull(saved);
        if (user.getPassword() != null && isReEncryptPassword) {
            passwordHelper.encryptPassword(user);
        }
        if (!saved.getUsername().equals(user.getUsername())) {
            //redisTemplate.delete(buildkey(saved.getUsername(), saved.getMerchantId()));
            //passwordRetryCache.clearRetryCount(saved.getUsername(), saved.getMerchantId());
        } else {
            //redisTemplate.delete(buildkey(user.getUsername(), user.getMerchantId()));
            //passwordRetryCache.clearRetryCount(user.getUsername(), user.getMerchantId());
        }
        return seUserDao.updateUser(user);
    }

    public void lock(String userName, Long merchantId) {
        //redisTemplate.delete(buildkey(userName, merchantId));
        seUserDao.lock(userName, merchantId);
    }

    public void lock(Long userId) {
        SeUser seUser = seUserDao.findOne(userId);
        Assert.notNull(seUser);
        lock(seUser.getUsername(), seUser.getMerchantId());
    }

    public void lock(List<Long> ids) {
        for (Long id : ids) {
            lock(id);
        }
    }

    private String buildkey(String userName, Long merchantId) {
        return "SeUser" + merchantId + userName;
    }

    public void unlock(String userName, Long merchantId) {
//        redisTemplate.delete(buildkey(userName, merchantId));
//        passwordRetryCache.clearRetryCount(userName, merchantId);
        seUserDao.unlock(userName, merchantId);
    }

    public void unlock(Long userId) {
        SeUser seUser = seUserDao.findOne(userId);
        Assert.notNull(seUser);
        unlock(seUser.getUsername(), seUser.getMerchantId());
    }

    public void unlock(List<Long> ids) {
        for (Long id : ids) {
            unlock(id);
        }
    }

    public void resetPassWord(Long id) {
        SeUser seUser = findOne(id);
        Assert.notNull(seUser, "用户不存在!");
//        if(seUser.getUsername().length()<6){
//            CPBusinessException.throwIt("用户名小于6位");
//        }
//        seUser.setPassword(seUser.getUsername().substring(seUser.getUsername().length()-6));
        seUser.setPassword("123456");//重置密码为123456
        updateUser(seUser, true);
    }

    public void deleteUser(Long userId) {
        SeUser user = seUserDao.findOne(userId);
        if (user != null) {
//            redisTemplate.delete(buildkey(user.getUsername(), user.getMerchantId()));
            seUserDao.deleteUser(userId);
        }
    }

    public SeUser findOne(Long userId) {
        return seUserDao.findOne(userId);
    }

    public List<SeUser> findAll() {
        return seUserDao.findAll();
    }

    /**
     * 根据用户名查找用户
     *
     * @param userName
     * @return
     */
    public SeUser findByUserNameAndMerchantId(String userName, Long merchantId) {
        String key = buildkey(userName, merchantId);
        SeUser seUser = null;//redisTemplate.opsForValue().get(key);
        if (seUser == null) {
            seUser = seUserDao.findByUsernameAndMerchantId(userName, merchantId);
            if (seUser != null) {
                //redisTemplate.opsForValue().set(key, seUser);
                return seUser;
            }
        }
        return seUser;
    }

    public SeUser findByWechatIdAndMerchantId(String wechatId, Long merchantId) {
        return seUserDao.findByWechatIdAndMerchantId(wechatId, merchantId);
    }


    /**
     * 根据用户名查找其角色
     *
     * @param userName
     * @return
     */
    public Set<String> findRoles(String userName, Long merchantId) {
        SeUser user = findByUserNameAndMerchantId(userName, merchantId);
        if (user == null) {
            return Collections.EMPTY_SET;
        }
        return roleService.findRoles(user.getRoleIds().toArray(new Long[0]));
    }

    /**
     * 根据用户名查找其权限
     *
     * @param userName
     * @return
     */
    public Set<SeResource> findPermissions(String userName, Long merchantId) {
        SeUser user = findByUserNameAndMerchantId(userName, merchantId);
        if (user == null) {
            return Collections.EMPTY_SET;
        }
        return roleService.findPermissions(user.getRoleIds().toArray(new Long[0]));
    }

    public List<SeUser> findAllContainRole(Long roleId) {
        return seUserDao.findAllContainRole(roleId);
    }

    public JdbcPage findByPage(ListParamDto dto) {
        JdbcPage<SeUser> seUserJdbcPage = seUserDao.findByPage(dto);
        for (SeUser seUser : seUserJdbcPage.getResult()) {
            List<Long> roleIdList = seUser.getRoleIds();
            Long idArray[] = roleIdList.toArray(new Long[0]);
            if (idArray.length > 0) {
                StringBuilder roleIdToString = new StringBuilder();
                for (int i = 0; i < idArray.length; ++i) {
                    if (i > 0) {
                        roleIdToString.append(",");
                    }
                    roleIdToString.append(idArray[i].toString());
                }
                List<SeRole> seRoleList = roleService.findRoleByRoleIdsAndMerchantIdAndType(roleIdToString.toString(), dto.getMerchantId(), seUser.getType());
                List<String> roleNameList = seRoleList.stream().map(SeRole::getDescription).collect(Collectors.toList());
                String roleNameStringArray[] = roleNameList.toArray(new String[roleNameList.size()]);
                seUser.setRoleNameStr(StringUtils.join(roleNameStringArray, ","));
            }
        }
        return seUserJdbcPage;
    }

}

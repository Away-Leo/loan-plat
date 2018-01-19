package com.pingxun.biz.user.app.service;

import com.pingxun.biz.common.dto.Pages;
import com.pingxun.biz.maillist.app.dto.MailListDto;
import com.pingxun.biz.user.app.dto.CwUserInfoDto;
import com.pingxun.biz.user.domain.entity.CwUserInfo;
import com.pingxun.biz.user.domain.entity.SeUser;
import com.pingxun.biz.user.domain.service.CwUserInfoDomainService;
import com.pingxun.biz.user.domain.service.SeUserService;
import com.pingxun.core.common.util.ObjectHelper;
import com.pingxun.core.common.util.Utils;
import com.zds.common.lang.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Created by dujy on 2017-05-20.
 */
@Transactional
@Service
public class CwUserInfoAppService {

    @Autowired
    private CwUserInfoDomainService cwUserInfoDomainService;

    @Autowired
    private SeUserService seUserService;

    /**
     * 修改客户信息
     * @param cwUserInfoDto
     * @return
     */
    public Long update(CwUserInfoDto cwUserInfoDto){
        return cwUserInfoDomainService.update(cwUserInfoDto).getId();
    }

    /**
     * 修改客户信息
     * @param cwUserInfoDto
     * @return
     */
    public CwUserInfo updateUser(CwUserInfoDto cwUserInfoDto){
        return cwUserInfoDomainService.updateUser(cwUserInfoDto);
    }

    /**
     * 修改加好友消息提示开关
     * @param cwUserInfoDto
     * @return
     */
    public CwUserInfo updateMessageTip(CwUserInfoDto cwUserInfoDto){
        return cwUserInfoDomainService.updateMessageTip(cwUserInfoDto);
    }

    /**
     * 认证成功接口
     * @param cwUserInfoDto
     * @return
     */
    public Long authenticationSuccess(CwUserInfoDto cwUserInfoDto){
        return cwUserInfoDomainService.authenticationSuccess(cwUserInfoDto).getId();
    }

    /**
     * 支付成功接口
     * @param cwUserInfoDto
     * @return
     */
    public Long paySuccess(CwUserInfoDto cwUserInfoDto){
        return cwUserInfoDomainService.paySuccess(cwUserInfoDto).getId();
    }

    /**
     * 查询用户详情
     * @param id
     * @return
     */
    public CwUserInfoDto findById(Long id)
    {
        CwUserInfoDto cwUserInfoDto = new CwUserInfoDto();
        CwUserInfo cwUserInfo = cwUserInfoDomainService.findById(id);
        if(cwUserInfo != null)
        {
            cwUserInfoDto = cwUserInfo.to(CwUserInfoDto.class);
            SeUser seUser = seUserService.findOne(id);
            cwUserInfoDto.setLoginPhone(seUser.getPhone());
            cwUserInfoDto.setIdCard(Utils.maskCertId(cwUserInfoDto.getIdCard()));
            cwUserInfoDto.setAccountNo(Utils.maskAccountNo(cwUserInfoDto.getAccountNo()));
        }
        return cwUserInfoDto;
    }

    /**
     * 查询个人信息
     * @param id
     * @return
     */
    public CwUserInfoDto findAuthInfoById(Long id)
    {
        CwUserInfoDto cwUserInfoDto = new CwUserInfoDto();
        CwUserInfo cwUserInfo = cwUserInfoDomainService.findById(id);
        if(cwUserInfo != null)
        {
            cwUserInfoDto = cwUserInfo.to(CwUserInfoDto.class);
            SeUser seUser = seUserService.findOne(id);
            cwUserInfoDto.setLoginPhone(seUser.getPhone());
        }
        return cwUserInfoDto;
    }

    /**
     * @Author: Away
     * @Description: 核对会员信息
     * @Param:
     * @Return void
     * @Date 2018/1/5 16:34
     * @Copyright 重庆平讯数据
     */
    public void checkVip(Long id) throws BusinessException{
        CwUserInfoDto cwUserInfoDto=this.findAuthInfoById(id);
        //如果为出借人
        if(cwUserInfoDto.getUserType()==2){
            if(!(ObjectHelper.isNotEmpty(cwUserInfoDto.getEffDate())&&cwUserInfoDto.getEffDate().after(Utils.getCurrentDayDate()))){
                throw new BusinessException("PX004","您还不是会员或者会员已过期");
            }
        }
    }

    /**
     * @Author: Away
     * @Description: 按照是否认证和身份证查找
     * @Param: isAuthentication 是否认证
     * @Param: idCard 身份证
     * @Return com.pingxun.biz.user.domain.entity.CwUserInfo
     * @Date 2018/1/8 11:32
     * @Copyright 重庆平讯数据
     */
    public CwUserInfoDto findByIsAuthenticationAndIdCardAndUserType(boolean isAuthentication,String idCard,int userType){
        CwUserInfo cwUserInfo=this.cwUserInfoDomainService.findByIsAuthenticationAndIdCardAndUserType(isAuthentication, idCard,userType);
        if(ObjectHelper.isNotEmpty(cwUserInfo)){
            return cwUserInfo.to(CwUserInfoDto.class);
        }else{
            return null;
        }
    }

    /**
     * 查询客户信息
     * @return
     */
    public Page<CwUserInfoDto> findByCondition(CwUserInfoDto dto){
        return Pages.map(cwUserInfoDomainService.findByCondition(dto),CwUserInfoDto.class);
    }
}

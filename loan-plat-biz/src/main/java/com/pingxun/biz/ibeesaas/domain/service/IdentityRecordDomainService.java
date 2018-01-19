package com.pingxun.biz.ibeesaas.domain.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pingxun.biz.ibeesaas.app.dto.IdentityParamDto;
import com.pingxun.biz.ibeesaas.domain.entity.IdentityRecord;
import com.pingxun.biz.ibeesaas.domain.repository.IdentityRecordRepository;
import com.pingxun.biz.ibeesaas.util.Config;
import com.pingxun.biz.ibeesaas.util.JsonUtils;
import com.pingxun.biz.ibeesaas.util.TaskUtil;
import com.pingxun.biz.order.app.service.ApplicationInfoAppService;
import com.pingxun.biz.parameter.app.dto.ParameterDto;
import com.pingxun.biz.parameter.app.service.ParameterAppService;
import com.pingxun.biz.user.app.dto.CwUserInfoDto;
import com.pingxun.biz.user.app.service.CwUserInfoAppService;
import com.pingxun.biz.user.domain.entity.CwUserInfo;
import com.pingxun.core.common.util.ObjectHelper;
import com.zds.common.lang.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Away
 * @version V1.0
 * @Title: IdentityRecordDomainService.java
 * @Description: 身份认证service
 * @date 2017/12/18 14:58
 * @copyright 重庆平讯数据
 */
@Service
public class IdentityRecordDomainService {

    @Autowired
    private IdentityRecordRepository identityRecordRepository;

    @Autowired
    private ApplicationInfoAppService applicationInfoAppService;

    @Autowired
    private CwUserInfoAppService cwUserInfoAppService;

    @Autowired
    private ParameterAppService parameterAppService;

    @Value("${cw.key.xy.pri}")
    private String prikeyLocation;
    @Value("${cw.key.xy.pub}")
    private String pubkeyLocation;
    @Value("${cw.key.xy.privpass}")
    private String privPass;
    @Value("${cw.xy.identityurl}")
    private String identityUrl;

    /**
     * @Author: Away
     * @Description: 按照用户查找
     * @Param: userId
     * @Return com.pingxun.biz.identify.domain.entity.IdentityRecord
     * @Date 2017/12/18 15:01
     * @Copyright 重庆平讯数据
     */
    public IdentityRecord findByUserId(Long userId) {
        if (ObjectHelper.isNotEmpty(userId)) {
            return identityRecordRepository.findByUserId(userId);
        } else {
            throw new BusinessException("PX001", "按照用户ID查找认证记录出错");
        }
    }

    /**
     * @Author: Away
     * @Description: 验证身份信息
     * @Param: identityRecordDto
     * @Return com.pingxun.biz.identify.app.dto.IdentityRecordDto
     * @Date 2017/12/18 15:50
     * @Copyright 重庆平讯数据
     */
    public CwUserInfoDto checkIdentity(IdentityParamDto identityParamDto) throws Exception {
        if (ObjectHelper.isEmpty(identityParamDto.getUserId())) throw new BusinessException("PX001", "用户ID为空");
        CwUserInfoDto userInfoDto=saveUserInfo(identityParamDto);
        return doCheck(identityParamDto,userInfoDto);
    }

    @Transactional
    public CwUserInfoDto doCheck(IdentityParamDto identityParamDto, CwUserInfoDto userInfoDto) throws Exception{
        CwUserInfoDto cwUserInfoDto=this.cwUserInfoAppService.findByIsAuthenticationAndIdCardAndUserType(true,identityParamDto.getId_card(),identityParamDto.getUserType());
        if(ObjectHelper.isNotEmpty(cwUserInfoDto)&& ObjectHelper.isNotEmpty(cwUserInfoDto.getUserId()))throw new BusinessException("PX004","用户已存在");
        //查询限制性配置信息 单个人能认证的最多次数
        ParameterDto parameterDto = this.parameterAppService.findByCode("authTime");
        //查询订单付款表核对是否付款
        if (!userInfoDto.getIsPay()) throw new BusinessException("PX004", "信息已保存，请缴费");
        if (userInfoDto.getIsAuthentication()) throw new BusinessException("PX004", "已经通过实名认证");
        if (ObjectHelper.isEmpty(parameterDto) || ObjectHelper.isEmpty(parameterDto.getParameterValue()))throw new BusinessException("PX004", "次数限制未设置");
        if (ObjectHelper.isNotEmpty(userInfoDto.getAuthTime())&&Integer.parseInt(parameterDto.getParameterValue()) <= userInfoDto.getAuthTime())
            throw new BusinessException("PX004", "认证次数已达上限");
        return doHttpIdentity(identityParamDto,userInfoDto);
    }

    @Transactional
    public CwUserInfoDto doHttpIdentity(IdentityParamDto identityParamDto, CwUserInfoDto cwUserInfoDto) throws Exception {
        String taskType = Config.TYPE_ELEMENT4;

        Map<String, Object> pMap = new LinkedHashMap<String, Object>();
        pMap.put("idCardNo", identityParamDto.getId_card());
        pMap.put("cardHolderName", identityParamDto.getId_holder());
        pMap.put("phoneNo", identityParamDto.getMobile());
        pMap.put("bankCardNo", identityParamDto.getAcc_no());
        pMap.put("sync", 1);
        Map<String, Object> pBody = new LinkedHashMap<String, Object>();
        pBody.put("data", pMap);
        String bodyJson = JsonUtils.objectToJson(pBody);

        String response = TaskUtil.submitTask(taskType, bodyJson);
//        String response = "{\"code\": \"general_0\",\"message\": \"\",\"taskNo\": \"f383b29113ab4800ae56023cfccc15d3\",\"taskStatus\": \"success\",\"taskResult\": {\"resultCode\":\"0000\",\"message\":\"认证成功\"}}";
        //保存记录
        return saveRecord(response, identityParamDto, identityParamDto.getUserId(),cwUserInfoDto);
    }

    @Transactional
    public CwUserInfoDto saveRecord(String resultStr, IdentityParamDto identityParamDto, Long userId, CwUserInfoDto cwUserInfoDto) {
        if (ObjectHelper.isEmpty(resultStr)) return null;
        JSONObject jsonObject = JSON.parseObject(resultStr);
        if (jsonObject.getString("taskStatus").equalsIgnoreCase("success")) {
            String taskNo = jsonObject.getString("taskNo");
            JSONObject dataJson = jsonObject.getJSONObject("taskResult");
            String code = dataJson.getString("resultCode");
            IdentityRecord identityRecord = new IdentityRecord();
            identityRecord.setIdentityParams(JSONObject.toJSONString(identityParamDto));
            identityRecord.setIdentityResult(code);
            identityRecord.setIdentityTransNo(taskNo);
            identityRecord.setUserId(userId);
            this.identityRecordRepository.save(identityRecord);
            if(code.equalsIgnoreCase("0000")){
                cwUserInfoDto.setIsAuthentication(true);
            }else{
                throw new BusinessException("PX004",dataJson.getString("message"));
            }
            cwUserInfoDto.setAuthTime((ObjectHelper.isNotEmpty(cwUserInfoDto.getAuthTime()) ? cwUserInfoDto.getAuthTime() : 0) + 1);
            CwUserInfo returnData=this.cwUserInfoAppService.updateUser(cwUserInfoDto);
            return returnData.to(CwUserInfoDto.class);
        }else{
            throw new BusinessException("PX004",jsonObject.getString("message"));
        }
    }

    @Transactional
    public CwUserInfoDto saveUserInfo(IdentityParamDto identityParamDto){
        CwUserInfoDto cwUserInfoDto = this.cwUserInfoAppService.findAuthInfoById(identityParamDto.getUserId());
        if(ObjectHelper.isEmpty(cwUserInfoDto.getUserId()))cwUserInfoDto.setUserId(identityParamDto.getUserId());
        cwUserInfoDto.setUserType(identityParamDto.getUserType());
        cwUserInfoDto.setName(identityParamDto.getId_holder());
        cwUserInfoDto.setIdCard(identityParamDto.getId_card());
        cwUserInfoDto.setAccountNo(identityParamDto.getAcc_no());
        cwUserInfoDto.setPhone(identityParamDto.getMobile());
        return this.cwUserInfoAppService.updateUser(cwUserInfoDto).to(CwUserInfoDto.class);
    }


}

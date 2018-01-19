package com.pingxun.web.common.controller;


import com.pingxun.biz.CwException;
import com.pingxun.biz.banner.app.dto.BannerDto;
import com.pingxun.biz.banner.app.service.BannerAppService;
import com.pingxun.biz.log.app.LogEnum;
import com.pingxun.biz.log.app.dto.LogDto;
import com.pingxun.biz.log.app.dto.SendSmsLogDto;
import com.pingxun.biz.log.app.dto.SendSmsTimeDto;
import com.pingxun.biz.log.app.service.LogAppService;
import com.pingxun.biz.log.app.service.SendSmsLogAppService;
import com.pingxun.biz.log.app.service.SendSmsTimeAppService;
import com.pingxun.biz.parameter.app.dto.ScreenConditionDto;
import com.pingxun.biz.parameter.app.dto.SpinnerParameterDto;
import com.pingxun.biz.parameter.app.service.SpinnerParameterAppService;
import com.pingxun.biz.price.app.dto.PriceDto;
import com.pingxun.biz.price.app.service.PriceAppService;
import com.pingxun.biz.user.app.dto.AppModuleDto;
import com.pingxun.biz.user.app.dto.CwUserInfoDto;
import com.pingxun.biz.user.app.dto.RegisterDto;
import com.pingxun.biz.user.app.service.CwUserInfoAppService;
import com.pingxun.biz.user.app.service.ModuleAppService;
import com.pingxun.biz.user.domain.entity.SeUser;
import com.pingxun.biz.user.domain.service.SeUserService;
import com.pingxun.core.common.util.Utils;
import com.pingxun.web.common.component.LoginComponent;
import com.pingxun.web.common.component.SendSmsComponent;
import com.pingxun.web.common.dto.CPViewResultInfo;
import com.pingxun.web.common.dto.UserInfoDto;
import com.pingxun.web.common.model.LoginModel;
import com.pingxun.web.common.model.SendSmsModel;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *通用请求
 */
@Controller
@RequestMapping("/common")
public class CommonController extends AbstractController {

    private static final Logger logger = LoggerFactory.getLogger(CommonController.class);

    @Autowired
    private LoginComponent loginComponent;

    @Autowired
    private SendSmsComponent sendSmsComponent;

    @Autowired
    private SendSmsLogAppService sendSmsLogAppService;

    @Autowired
    private SendSmsTimeAppService sendSmsTimeAppService;
    @Autowired
    private SpinnerParameterAppService spinnerParameterAppService;
    @Autowired
    private BannerAppService bannerAppService;

    @Autowired
    private ModuleAppService moduleAppService;

    @Autowired
    private PriceAppService priceAppService;

    @Autowired
    private SeUserService seUserService;

    @Autowired
    private CwUserInfoAppService userInfoAppService;
    @Autowired
    private LogAppService logAppService;
    /**
     * 用户登录
     * @param httpServletRequest
     * @param loginModel
     * @return
     */
    @PostMapping("passwordLogin.json")
    @ResponseBody
    public CPViewResultInfo passwordLogin(HttpServletRequest httpServletRequest, @RequestBody LoginModel loginModel) {

        SeUser seUser = seUserService.findByUserNameAndMerchantId(loginModel.getUserName(),1L);
        //用户不存在则注册用户
        if(seUser==null){
            if(!isMobilePhone(loginModel.getUserName())){
                CwException.throwIt("手机号码格式不正确");
            }
            if(loginModel.getPassword()==null||"".equals(loginModel.getPassword())){
                CwException.throwIt("密码不能为空");
            }
            SeUser seUser1 = new SeUser();
            seUser1.setPassword(loginModel.getPassword());
            seUser1.setMerchantId(1L);
            seUser1.setrId(1L);
            seUser1.setType("user");
            seUser1.setUsername(loginModel.getUserName());
            seUser1.setPhone(loginModel.getUserName());
            seUser = seUserService.createUser(seUser1);

            //插入用户信息表
            CwUserInfoDto cwUserInfoDtos = new CwUserInfoDto();
            cwUserInfoDtos.setUserId(seUser.getId());
            cwUserInfoDtos.setRegisterDate(new Date());
            userInfoAppService.update(cwUserInfoDtos);

            //记录日志
            recordLog(seUser,loginModel);
        }

        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        String openid = (String) httpServletRequest.getSession().getAttribute("openid");
        loginComponent.wechatBinding(httpServletRequest, loginModel, openid, "passwordLogin");
        CwUserInfoDto cwUserInfoDto = userInfoAppService.findById(seUser.getId());
        UserInfoDto userInfoDto = new UserInfoDto();
        userInfoDto.setUserType(cwUserInfoDto.getUserType());
        userInfoDto.setIsAuthentication(cwUserInfoDto.getIsAuthentication());
        userInfoDto.setIsPay(cwUserInfoDto.getIsPay());
        userInfoDto.setId(cwUserInfoDto.getId());
        cpViewResultInfo.setData(userInfoDto);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("登录成功");
        return cpViewResultInfo;
    }

    /**
     * 记录注册日志
     * @param seUser
     */
    private void recordLog(SeUser seUser,LoginModel loginModel){
        LogDto logDto = new LogDto();
        logDto.setType(LogEnum.USER_REGISTER);
        logDto.setDeviceNumber(seUser.getPhone());
        logDto.setChannelNo(loginModel.getChannelNo());
        logDto.setUserId(seUser.getId());
        logDto.setAppName(loginModel.getAppName());
        logDto.setApplyArea(loginModel.getApplyArea());
        logAppService.create(logDto);
    }

    /**
     * 发送验证码
     * @param sendSmsModel
     * @return
     */
    @PostMapping("sendSmsVerify.json")
    @ResponseBody
    public CPViewResultInfo sendSmsVerify(@RequestBody SendSmsModel sendSmsModel) {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        if(!isMobilePhone(sendSmsModel.getPhone()))
        {
            CwException.throwIt("手机号码格式不正确");
        }
        logger.info("发送区域："+sendSmsModel.getApplyArea());
        //检查用户已发送次数
        SendSmsTimeDto sendSmsTimeDto = sendSmsTimeAppService.findByPhone(sendSmsModel.getPhone(),sendSmsModel.getAppName());
        if(!Objects.isNull(sendSmsTimeDto))
        {
            if(sendSmsTimeDto.getSendTime() > 2)
            {
                CwException.throwIt("短信发送超过次数,最近一次验证码可直接登录");
            }
        }

        //查询上次是否发送成功
        SendSmsLogDto isSendSmsLog = sendSmsLogAppService.findById(sendSmsModel.getPhone());
        if(Objects.isNull(isSendSmsLog)) {
            //记录发送日志
            SendSmsLogDto sendSmsLogDto = new SendSmsLogDto();
            sendSmsLogDto.setDeviceNumber(sendSmsModel.getDeviceNumber());
            sendSmsLogDto.setApplyArea(sendSmsModel.getApplyArea());
            sendSmsLogDto.setSendDate(new Date());
            sendSmsLogDto.setChannelNo(sendSmsModel.getChannelNo());
            sendSmsLogDto.setPhone(sendSmsModel.getPhone());
            sendSmsLogDto.setAppName(sendSmsModel.getAppName());
            sendSmsLogDto.setIsSuccess(Boolean.FALSE);
            Long smsId = sendSmsLogAppService.create(sendSmsLogDto);

            //发送短信
            String result = sendSmsComponent.sendSms(sendSmsModel);
            //解析发送结果
            String sendResult = Utils.strConvertXml(result);
            if (sendResult.contains("Success")) {
                //修改发送标识
                sendSmsLogDto.setId(smsId);
                sendSmsLogDto.setIsSuccess(Boolean.TRUE);
                sendSmsLogAppService.update(sendSmsLogDto);
                //记录发送次数
                sendSmsTimeDto = new SendSmsTimeDto();
                sendSmsTimeDto.setPhone(sendSmsModel.getPhone());
                sendSmsTimeDto.setAppName(sendSmsModel.getAppName());
                sendSmsTimeAppService.update(sendSmsTimeDto);
            } else {
                CwException.throwIt("短信发送失败");
            }
        }
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("发送成功");
        return cpViewResultInfo;
    }

    /**
     * 忘记密码
     * @param registerDto
     * @return
     */
    @PostMapping("forgetPassword.json")
    @ResponseBody
    public CPViewResultInfo forgetPassword(HttpServletRequest httpServletRequest,@RequestBody RegisterDto registerDto) {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        if(!isMobilePhone(registerDto.getPhone())){
            CwException.throwIt("手机号码格式不正确");
        }
        if(registerDto.getPassword()==null||"".equals(registerDto.getPassword())){
            CwException.throwIt("密码不能为空");
        }
        //验证码是否正确
        SeUser seUser = seUserService.findByUserNameAndMerchantId(registerDto.getPhone(), 1L);
        if(seUser==null){
            CwException.throwIt("用户不存在");
        }
        Long diffMinute = Utils.calcDateDiffMinute(seUser.getVerifyExpDate(),new Date());
        if(diffMinute>2){
            CwException.throwIt("验证码已过期");
        }

        if(!seUser.getVerifyCode().equals(registerDto.getVerifyCode())){
            CwException.throwIt("验证码错误");
        }
        //验证密码两次输入是否一致
        if(!registerDto.getPassword().toUpperCase().equals(registerDto.getConfirmPassword().toUpperCase())) {
            CwException.throwIt("两次密码不一致");
        }
        //修改用户密码
        seUser.setPassword(registerDto.getPassword());
        seUserService.updateUser(seUser, Boolean.TRUE);

        //登录
        LoginModel loginModel = new LoginModel();
        loginModel.setPassword(registerDto.getPassword());
        loginModel.setUserName(registerDto.getPhone());
        loginModel.setType("user");
        String openid = (String) httpServletRequest.getSession().getAttribute("openid");
        loginComponent.wechatBinding(httpServletRequest, loginModel, openid, "passwordLogin");
        CwUserInfoDto cwUserInfoDto = userInfoAppService.findById(seUser.getId());
        UserInfoDto userInfoDto = new UserInfoDto();
        userInfoDto.setUserType(cwUserInfoDto.getUserType());
        userInfoDto.setIsAuthentication(cwUserInfoDto.getIsAuthentication());
        cpViewResultInfo.setData(userInfoDto);

        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("密码重置成功");
        return cpViewResultInfo;
    }

    /**
     * 退出登录
     * @return
     */
    @GetMapping("logout.json")
    @ResponseBody
    public CPViewResultInfo logout() {
        CPViewResultInfo resultInfo = new CPViewResultInfo();
        SecurityUtils.getSubject().logout();
        resultInfo.setSuccess(true);
        return resultInfo;
    }

    /**
     * 查询app对应的模块接口
     * 现金贷或则商品贷开关接口
     * @return
     */
    @GetMapping("/sys/getAppModule.json")
    @ResponseBody
    public CPViewResultInfo getAppModule(String appName)
    {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        AppModuleDto appModuleDto = moduleAppService.getAppModule(appName);
        if(!Objects.isNull(appModuleDto)) {
            cpViewResultInfo.setData(appModuleDto.getModuleIds());
        }
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("成功");
        return cpViewResultInfo;
    }
    /**
     * 查询下拉列表
     * @return
     */
    @GetMapping("findByType.json")
    @ResponseBody
    public CPViewResultInfo findByType(String type,String loanType) {
        if(type==null){
            type = loanType;
        }
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        List<SpinnerParameterDto> spinnerParameterDto = spinnerParameterAppService.findByType(type);
        cpViewResultInfo.setData(spinnerParameterDto);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("查询成功");
        return cpViewResultInfo;
    }

    /**
     * 查询筛选条件
     * @return
     */
    @GetMapping("findByCondition.json")
    @ResponseBody
    public CPViewResultInfo findByCondition(Integer userType) {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        List<ScreenConditionDto> spinnerParameterDto = spinnerParameterAppService.findByCondition(userType);
        cpViewResultInfo.setData(spinnerParameterDto);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("查询成功");
        return cpViewResultInfo;
    }

    /**
     * 查询banner图标
     * @param position
     * @return
     */
    @GetMapping("findBanner.json")
    @ResponseBody
    public CPViewResultInfo findCreditCardBanner(String position,String versionNo){
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        List<BannerDto> bannerDtoList = bannerAppService.findAll(position,versionNo);
        cpViewResultInfo.setData(bannerDtoList);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("成功");
        return cpViewResultInfo;
    }

    private Boolean isMobilePhone(String phone){
        String PHONE_PATTERN="^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17([0,1,3,6,7,]))|(18[0-9]))\\d{8}$";
        Pattern p = Pattern.compile(PHONE_PATTERN);
        Matcher m = p.matcher(phone);
        return m.find();
    }

    /**
     * 根据角色查询支付价格
     * @param userType
     * @return
     */
    @GetMapping("findPriceByUserType.json")
    @ResponseBody
    public CPViewResultInfo findPriceByUserType(Integer userType) {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        List<PriceDto> priceDtoList = priceAppService.findByUserType(userType);
        cpViewResultInfo.setData(priceDtoList);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("发送成功");
        return cpViewResultInfo;
    }

    /**
     * 查询用户是否注册
     * @return
     */
    @GetMapping("checkPhoneIsExist.json")
    @ResponseBody
    public CPViewResultInfo checkPhoneIsExist(String phone) {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        SeUser seUser = seUserService.findByUserNameAndMerchantId(phone,1L);
        if(seUser==null) {
            cpViewResultInfo.setData(0);
        }else{
            cpViewResultInfo.setData(1);
        }
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("查询成功");
        return cpViewResultInfo;
    }
}

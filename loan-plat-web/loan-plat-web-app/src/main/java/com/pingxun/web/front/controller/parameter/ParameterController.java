package com.pingxun.web.front.controller.parameter;

import com.pingxun.biz.parameter.app.dto.IndexParameterDto;
import com.pingxun.biz.parameter.app.service.ParameterAppService;
import com.pingxun.web.common.dto.CPViewResultInfo;
import com.pingxun.web.front.controller.AbstractFrontController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 参数配置
 * Created by dujy on 2017-05-20.
 */
@RestController
public class ParameterController extends AbstractFrontController {

    @Autowired
    private ParameterAppService parameterAppService;

    /**
     * 查询所有参数
     * @return
     */
    @GetMapping("/parameter/findParameter.json")
    public CPViewResultInfo findAll()
    {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        IndexParameterDto parameterDtos = parameterAppService.findAll();
        cpViewResultInfo.setData(parameterDtos);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("成功");
        return cpViewResultInfo;
    }
}

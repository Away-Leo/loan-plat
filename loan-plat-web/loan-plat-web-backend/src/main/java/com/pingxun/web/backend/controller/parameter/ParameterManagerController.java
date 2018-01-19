package com.pingxun.web.backend.controller.parameter;

import com.pingxun.biz.parameter.app.dto.IndexParameterDto;
import com.pingxun.biz.parameter.app.service.ParameterAppService;
import com.pingxun.web.backend.controller.AbstractBackendController;
import com.pingxun.web.common.dto.CPViewResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 参数配置
 * Created by dujy on 2017-05-20.
 */
@RestController
public class ParameterManagerController extends AbstractBackendController {

    @Autowired
    private ParameterAppService parameterAppService;

    /**
     * 修改参数
     * @param parameterDto
     * @return
     */
    @PostMapping("/parameter/update.json")
    @ResponseBody
    public CPViewResultInfo updateParameter(@RequestBody IndexParameterDto parameterDto)
    {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        Long parameterId = parameterAppService.update(parameterDto);
        cpViewResultInfo.setData(parameterId);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("参数保存成功");
        return cpViewResultInfo;
    }

}

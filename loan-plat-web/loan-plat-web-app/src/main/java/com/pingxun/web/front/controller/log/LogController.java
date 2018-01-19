package com.pingxun.web.front.controller.log;

import com.pingxun.biz.CPContext;
import com.pingxun.biz.log.app.dto.LogDto;
import com.pingxun.biz.log.app.service.LogAppService;
import com.pingxun.web.common.dto.CPViewResultInfo;
import com.pingxun.web.front.controller.AbstractFrontController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 日志接口
 * Created by dujy on 2017-05-20.
 */
@RestController
public class LogController extends AbstractFrontController {

    @Autowired
    private LogAppService logAppService;

    /**
     * 记录日志
     * @param logDto
     * @return
     */
    @PostMapping("/log/create.json")
    @ResponseBody
    public CPViewResultInfo create(@RequestBody LogDto logDto)
    {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        Long userId = CPContext.getContext().getSeUserInfo().getrId();
        logDto.setUserId(userId);
        Long logId = logAppService.create(logDto);
        cpViewResultInfo.setData(logId);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("成功");

        return cpViewResultInfo;
    }

}

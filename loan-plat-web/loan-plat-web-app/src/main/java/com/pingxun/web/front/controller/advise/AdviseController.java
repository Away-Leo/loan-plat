package com.pingxun.web.front.controller.advise;

import com.pingxun.biz.CPContext;
import com.pingxun.biz.advise.app.dto.AdviseDto;
import com.pingxun.biz.advise.app.service.AdviseAppService;
import com.pingxun.web.common.dto.CPViewResultInfo;
import com.pingxun.web.front.controller.AbstractFrontController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * 意见收集
 */
@RestController
public class AdviseController extends AbstractFrontController {

    @Autowired
    private AdviseAppService adviseAppService;

    /**
     * 新增意见
     * @return
     */
    @PostMapping("/advise/create.json")
    public CPViewResultInfo create(@RequestBody AdviseDto adviseDto){
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        adviseDto.setUserId(CPContext.getContext().getSeUserInfo().getId());
        Long id = adviseAppService.create(adviseDto);
        cpViewResultInfo.setData(id);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("提交成功");

        return cpViewResultInfo;
    }

    /**
     * 查询意见列表
     * @param adviseDto
     * @return
     */
    @GetMapping("/advise/list.json")
    @ResponseBody
    public CPViewResultInfo findByCondition(AdviseDto adviseDto){
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        Page<AdviseDto> orderDtos = adviseAppService.findByCondition(adviseDto);
        cpViewResultInfo.setData(orderDtos);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("成功");
        return cpViewResultInfo;
    }

}

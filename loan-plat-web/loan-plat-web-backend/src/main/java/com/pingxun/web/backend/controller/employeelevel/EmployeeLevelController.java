package com.pingxun.web.backend.controller.employeelevel;

import com.pingxun.biz.employeelevel.app.dto.EmployeeLevelDto;
import com.pingxun.biz.employeelevel.app.service.EmployeeLevelAppService;
import com.pingxun.web.backend.controller.AbstractBackendController;
import com.pingxun.web.common.dto.CPViewResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 会员等级
 * Created by Administrator on 2017/6/5.
 */
@Controller
public class EmployeeLevelController extends AbstractBackendController {

    @Autowired
    private EmployeeLevelAppService employeeLevelAppService;

    /**
     * 查询会员等级
     * @param employeeLevelDto
     * @return
     */
    @PostMapping("/level/findByCondition.json")
    @ResponseBody
    public CPViewResultInfo userList(@RequestBody EmployeeLevelDto employeeLevelDto) {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        Page<EmployeeLevelDto> employeeLevelDtos = employeeLevelAppService.findByCondition(employeeLevelDto);
        cpViewResultInfo.setData(employeeLevelDtos);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("成功");
        return cpViewResultInfo;
    }

    /**
     * 新增会员等级
     * @param employeeLevelDto
     * @return
     */
    @PostMapping("/level/create.json")
    @ResponseBody
    public CPViewResultInfo add(@RequestBody EmployeeLevelDto employeeLevelDto) {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        Long id = employeeLevelAppService.create(employeeLevelDto);
        cpViewResultInfo.setData(id);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("操作成功");
        return cpViewResultInfo;
    }

    /**
     * 查询会员等级详情
     * @return
     */
    @GetMapping("/level/findById.json")
    @ResponseBody
    public CPViewResultInfo findById(Long id) {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        EmployeeLevelDto employeeLevelDto = employeeLevelAppService.findById(id);
        cpViewResultInfo.setData(employeeLevelDto);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("成功");
        return cpViewResultInfo;
    }


    /**
     * 修改会员等级
     * @param employeeLevelDto
     * @return
     */
    @PostMapping("/level/update.json")
    @ResponseBody
    public CPViewResultInfo update(@RequestBody EmployeeLevelDto employeeLevelDto) {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        Long id = employeeLevelAppService.update(employeeLevelDto);
        cpViewResultInfo.setData(id);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("成功");
        return cpViewResultInfo;
    }

}

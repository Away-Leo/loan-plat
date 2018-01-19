package com.pingxun.web.backend.controller.employeelevel;

import com.pingxun.biz.employeelevel.app.dto.EmployeeLevelPermissionDto;
import com.pingxun.biz.employeelevel.app.service.EmployeeLevelPermissionAppService;
import com.pingxun.web.backend.controller.AbstractBackendController;
import com.pingxun.web.common.dto.CPViewResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * 会员等级权限配置
 * Created by Administrator on 2017/6/5.
 */
@RestController
public class EmployeeLevelPermissonController extends AbstractBackendController {

    @Autowired
    private EmployeeLevelPermissionAppService appService;

    /**
     * 查询权限
     * @return
     */
    @GetMapping("/permisson/findById.json")
    @ResponseBody
    public CPViewResultInfo findById(Long id) {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        EmployeeLevelPermissionDto employeeLevelPermissionDto = appService.findById(id);
        cpViewResultInfo.setData(employeeLevelPermissionDto);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("查询成功");
        return cpViewResultInfo;
    }


    /**
     * 修改权限
     * @param employeeLevelPermissionDto
     * @return
     */
    @PostMapping("/permisson/update.json")
    @ResponseBody
    public CPViewResultInfo update(@RequestBody EmployeeLevelPermissionDto employeeLevelPermissionDto) {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        Long id = appService.update(employeeLevelPermissionDto);
        cpViewResultInfo.setData(id);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("修改成功");
        return cpViewResultInfo;
    }

    /**
     * 查询权限
     * @param cwUserInfoDto
     * @return
     */
    @PostMapping("/permisson/findByCondition.json")
    public CPViewResultInfo findByCondition(@RequestBody EmployeeLevelPermissionDto cwUserInfoDto){
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        Page<EmployeeLevelPermissionDto> cwUserInfoDtos = appService.findByCondition(cwUserInfoDto);
        cpViewResultInfo.setData(cwUserInfoDtos);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("成功");

        return cpViewResultInfo;
    }

}

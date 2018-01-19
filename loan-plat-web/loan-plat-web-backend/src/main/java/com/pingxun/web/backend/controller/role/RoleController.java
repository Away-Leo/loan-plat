package com.pingxun.web.backend.controller.role;

import com.pingxun.biz.common.dto.ListParamDto;
import com.pingxun.biz.jdbc.JdbcPage;
import com.pingxun.biz.user.domain.entity.SeResource;
import com.pingxun.biz.user.domain.entity.SeRole;
import com.pingxun.biz.user.domain.service.SeResourceService;
import com.pingxun.biz.user.domain.service.SeRoleService;
import com.pingxun.web.backend.controller.AbstractBackendController;
import com.pingxun.web.common.dto.CPViewResultInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 角色管理
 */
@RestController
public class RoleController extends AbstractBackendController {
    private static final Logger logger = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private SeRoleService seRoleService;

    @Autowired
    private SeResourceService seResourceService;
    /**
     * 角色查询
     * @param roleParamDto
     * @return
     */
    @PostMapping("/role/findByCondition.json")
    @ResponseBody
    public CPViewResultInfo findByCondition(@RequestBody ListParamDto roleParamDto) {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        roleParamDto.setMerchantId(1L);
        roleParamDto.setSizePerPage(50);
        JdbcPage jdbcPage = seRoleService.findByPage(roleParamDto);
        cpViewResultInfo.setData(jdbcPage);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("角色查询成功");
        return cpViewResultInfo;
    }

    /**
     * 新增角色
     * @param seRole
     * @return
     */
    @PostMapping("/role/create.json")
    @ResponseBody
    public CPViewResultInfo add(@RequestBody SeRole seRole) {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        seRole.setMerchantId(1L);
        SeRole seRole1 = seRoleService.createRole(seRole);
        cpViewResultInfo.setData(seRole1.getId());
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("保存成功");
        return cpViewResultInfo;
    }

    /**
     * 查询角色信息
     * @param id
     * @return
     */
    @GetMapping("/role/findById.json")
    @ResponseBody
    public CPViewResultInfo findById(Long id) {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        SeRole seUser1 = seRoleService.findOne(id);
        cpViewResultInfo.setData(seUser1);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("查询成功");
        return cpViewResultInfo;
    }


    /**
     * 修改角色
     * @param seRole
     * @return
     */
    @PostMapping("/role/update.json")
    @ResponseBody
    public CPViewResultInfo update(@RequestBody SeRole seRole) {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        seRole.setMerchantId(1L);
        SeRole seUser1 = seRoleService.updateRole(seRole);
        cpViewResultInfo.setData(seUser1.getId());
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("修改成功");
        return cpViewResultInfo;
    }

    /**
     * 查看所有资源
     * @return
     */
    @GetMapping("/role/findAllResource.json")
    @ResponseBody
    public CPViewResultInfo findAllResource(Long id) {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        SeRole seRole = seRoleService.findOne(id);
        List<SeResource> seResourceList = seResourceService.findAll();
        StringBuffer stringBuffer = new StringBuffer();
        int i=0;
        stringBuffer.append("[");
        for(SeResource seResource:seResourceList)
        {
            //判断是否有权限
            if(seRole!=null && seRole.getResourceIds()!=null) {
                int idx=0;
                for (Long resourceId : seRole.getResourceIds()) {
                    if (resourceId == seResource.getId()) {
                        stringBuffer.append("{id:" + seResource.getId() + ", pId:" + seResource.getParentId() + ", name:\"" + seResource.getName() + "\",checked:true,open:true}");
                        idx++;
                        break;
                    }
                }
                if(idx==0)
                {
                    stringBuffer.append("{id:" + seResource.getId() + ", pId:" + seResource.getParentId() + ", name:\"" + seResource.getName() + "\", open:true}");
                }
            }else{
                stringBuffer.append("{id:" + seResource.getId() + ", pId:" + seResource.getParentId() + ", name:\"" + seResource.getName() + "\", open:true}");
            }
            if(i<seResourceList.size()-1)
            {
                stringBuffer.append(",");
            }
            i++;
        }
        stringBuffer.append("]");
        cpViewResultInfo.setData(stringBuffer.toString());
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("查询所有资源");
        return cpViewResultInfo;
    }
}

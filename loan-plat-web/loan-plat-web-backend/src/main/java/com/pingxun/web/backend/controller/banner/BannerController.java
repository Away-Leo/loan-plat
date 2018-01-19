package com.pingxun.web.backend.controller.banner;

import com.pingxun.biz.banner.app.dto.BannerDto;
import com.pingxun.biz.banner.app.service.BannerAppService;
import com.pingxun.web.backend.controller.AbstractBackendController;
import com.pingxun.web.common.dto.CPViewResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * 图标服务
 * Created by dujy on 2017-06-26.
 */
@RestController
public class BannerController extends AbstractBackendController {

    @Autowired
    private BannerAppService bannerAppService;

    /**
     * 新增banner详情
     * @param bannerDto
     * @return
     */
    @PostMapping("/banner/create.json")
    @ResponseBody
    public CPViewResultInfo create(@RequestBody BannerDto bannerDto)
    {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        Long cardId = bannerAppService.create(bannerDto);
        cpViewResultInfo.setData(cardId);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("成功");
        return cpViewResultInfo;
    }

    /**
     * 查询banner详情
     * @param id
     * @return
     */
    @GetMapping("/banner/findById.json")
    @ResponseBody
    public CPViewResultInfo findById(Long id)
    {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        BannerDto bannerDto = bannerAppService.findById(id);
        cpViewResultInfo.setData(bannerDto);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("查询成功");
        return cpViewResultInfo;
    }

    /**
     * 查询Banner图标
     * @param bannerDto
     * @return
     */
    @PostMapping("/banner/findByCondition.json")
    @ResponseBody
    public CPViewResultInfo findByCondition(@RequestBody BannerDto bannerDto)
    {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        Page<BannerDto> creditCardDtos = bannerAppService.findByCondition(bannerDto);
        cpViewResultInfo.setData(creditCardDtos);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("查询成功");
        return cpViewResultInfo;
    }
    /**
     * 启用停用产品
     * @param bannerDto
     * @return
     */
    @PostMapping("/banner/enable.json")
    @ResponseBody
    public CPViewResultInfo enable(@RequestBody BannerDto bannerDto)
    {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        bannerAppService.enable(bannerDto);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("图标停用成功");
        return cpViewResultInfo;
    }

    /**
     * 修改banner图片信息
     * @param bannerDto
     * @return
     */
    @PostMapping("/banner/update.json")
    @ResponseBody
    public CPViewResultInfo update(@RequestBody BannerDto bannerDto) {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        Long id = bannerAppService.update(bannerDto);
        cpViewResultInfo.setData(id);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("图标修改成功");
        return cpViewResultInfo;
    }

}

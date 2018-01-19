package com.pingxun.web.front.controller.appproduct;

import com.pingxun.biz.product.app.dto.ProductVersionDto;
import com.pingxun.biz.product.app.service.ProductVersionAppService;
import com.pingxun.web.common.dto.CPViewResultInfo;
import com.pingxun.web.front.controller.AbstractFrontController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 借款人需求接口
 * Created by dujy on 2017-05-20.
 */
@RestController
public class AppProductController extends AbstractFrontController {

    @Autowired
    private ProductVersionAppService productVersionAppService;


    /**
     * 查询产品数据是否有更新
     * @return
     */
    @GetMapping("/product/findProductVersion.json")
    public CPViewResultInfo findProductVersion(String channelNo)
    {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        ProductVersionDto productVersionDto = productVersionAppService.findProductVersion(channelNo);
        cpViewResultInfo.setData(productVersionDto);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("成功");
        return cpViewResultInfo;
    }

}

package com.pingxun.web.front.controller.product;

import com.pingxun.biz.log.app.dto.LogDto;
import com.pingxun.biz.product.app.dto.ProductDto;
import com.pingxun.biz.product.app.dto.ProductListDto;
import com.pingxun.biz.product.app.dto.ProductRecommendListDto;
import com.pingxun.biz.product.app.dto.ProductSearchDto;
import com.pingxun.biz.product.app.service.ProductAppService;
import com.pingxun.web.common.dto.CPViewResultInfo;
import com.pingxun.web.front.controller.AbstractFrontController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 产品控制器
 * Created by dujy on 2017-05-20.
 */
@RestController
public class ProductController extends AbstractFrontController {

    @Autowired
    private ProductAppService productAppService;

    /**
     * 查询产品详情
     * @param id
     * @return
     */
    @GetMapping("/product/findById.json")
    public CPViewResultInfo findById(Long id)
    {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        ProductDto productDto = productAppService.findById(id);
        cpViewResultInfo.setData(productDto);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("成功");
        return cpViewResultInfo;
    }

    /**
     * 搜索产品
     * @param productSearchDto
     * @return
     */
    @PostMapping("/product/findByCondition.json")
    public CPViewResultInfo findByCondition(@RequestBody ProductSearchDto productSearchDto)
    {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        Page<ProductListDto> productDtos = productAppService.findByCondition(productSearchDto);
        cpViewResultInfo.setData(productDtos);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("成功");
        return cpViewResultInfo;
    }

    /**
     * 立即申请
     * @param logDto
     * @return
     */
    @PostMapping("/product/applyLoan.json")
    public CPViewResultInfo applyLoan(@RequestBody LogDto logDto) {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        productAppService.applyLoan(logDto);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("成功");
        return cpViewResultInfo;
    }



    /**
     * 查找推荐产品
     * @return
     */
    @GetMapping("/product/findRecommendProduct.json")
    public CPViewResultInfo findRecommendProduct(ProductSearchDto productSearchDto)
    {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        List<ProductListDto> productStoreDtos = productAppService.findRecommendProduct(productSearchDto);
        cpViewResultInfo.setData(productStoreDtos);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("成功");
        return cpViewResultInfo;
    }

    /**
     * 产品申请后推荐产品
     * @return
     */
    @GetMapping("/product/findApplyProductRecommend.json")
    public CPViewResultInfo findApplyProductRecommend(Long productId,String versionNo,String channelNo){
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        List<ProductRecommendListDto> productListDtoList = productAppService.findRecommendProductByProductId(productId,versionNo,channelNo);
        cpViewResultInfo.setData(productListDtoList);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("成功");
        return cpViewResultInfo;
    }
}

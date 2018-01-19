package com.pingxun.biz.product.app.service;

import com.pingxun.biz.CPContext;
import com.pingxun.biz.common.dto.Pages;
import com.pingxun.biz.log.app.LogEnum;
import com.pingxun.biz.log.app.dto.LogDto;
import com.pingxun.biz.product.app.dto.ProductDto;
import com.pingxun.biz.product.app.dto.ProductListDto;
import com.pingxun.biz.product.app.dto.ProductRecommendListDto;
import com.pingxun.biz.product.app.dto.ProductSearchDto;
import com.pingxun.biz.product.domain.entity.Product;
import com.pingxun.biz.product.domain.service.ProductDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dujy on 2017-05-20.
 */
@Transactional
@Service
public class ProductAppService {

    @Autowired
    private ProductDomainService productDomainService;

    /**
     * 查询产品详情
     * @param id
     * @return
     */
    public ProductDto findById(Long id){
        ProductDto productDto = new ProductDto();
        Product product = productDomainService.findById(id);
        if(product!=null){
            productDto =  product.to(ProductDto.class);
            if(productDto.getIsHidden()==2&&productDto.getLimitUserTop()<productDto.getTodayApplyUser()){
                //设置流量控制需要跳转的Url 20171117屏蔽跳转功能
//                if(productDto.getJumpUrl()!=null&&!"0".equals(productDto.getJumpUrl())&&!"".equals(productDto.getJumpUrl())) {
//                    ProductDto jumpProductDto = productDomainService.findById(Long.parseLong(productDto.getJumpUrl())).to(ProductDto.class);
//                    if(jumpProductDto!=null) {
//                        productDto.setUrl(jumpProductDto.getUrl());
//                        productDto.setId(jumpProductDto.getId());
//                    }
//                }
            }
        }

        return productDto;
    }

    /**
     * 按条件搜索产品列表v1.0秒必贷
     * @param dto
     * @return
     */
    public Page<ProductListDto> findByCondition(ProductSearchDto dto){
        return Pages.map(productDomainService.findByCondition(dto),ProductListDto.class);
    }

    /**
     * 查询全部产品
     * @return
     */
    public List<ProductDto> findAll(ProductSearchDto searchDto){
        List<ProductDto> productDtoList = new ArrayList<ProductDto>();
        List<Product> productList = productDomainService.findAll(searchDto).getContent();
        for(Product product:productList){
            productDtoList.add(product.to(ProductDto.class));
        }
        return productDtoList;
    }


    /**
     * 借款申请
     * @param dto
     */
    public void applyLoan(LogDto dto){
        dto.setUserId(CPContext.getContext().getSeUserInfo().getrId());
        dto.setType(LogEnum.APPLY_LOAN);
        productDomainService.applyLoan(dto);
    }

    /**
     * 查找推荐产品
     * @return
     */
    public List<ProductListDto> findRecommendProduct(ProductSearchDto productSearchDto)
    {
        List<ProductListDto> productDtoList = new ArrayList<ProductListDto>();
        List<Product> productList = productDomainService.findRecommendProduct(productSearchDto).getContent();
        for(Product product:productList){
            productDtoList.add(product.to(ProductListDto.class));
        }
        return productDtoList;
    }

    /**
     * 产品申请推荐功能
     * @param productId
     * @return
     */
    public List<ProductRecommendListDto> findRecommendProductByProductId(Long productId,String versionNo,String channelNo){
        List<ProductRecommendListDto> productList = productDomainService.findRecommendProductByProductId(productId,versionNo,channelNo);
        return productList;
    }
}

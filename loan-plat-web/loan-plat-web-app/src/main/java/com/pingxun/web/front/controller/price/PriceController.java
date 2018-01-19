package com.pingxun.web.front.controller.price;

import com.pingxun.biz.price.app.dto.PriceDto;
import com.pingxun.biz.price.app.service.PriceAppService;
import com.pingxun.web.common.dto.CPViewResultInfo;
import com.pingxun.web.front.controller.AbstractFrontController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 根据用户类型查询支付价格
 * Created by dujy on 2017-05-20.
 */
@RestController
public class PriceController extends AbstractFrontController {

    @Autowired
    private PriceAppService priceAppService;

    /**
     * 根据用户类型查询认证价格
     * @return
     */
    @GetMapping("/price/findPriceByUserType.json")
    public CPViewResultInfo findProduct(Integer userType){
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        List<PriceDto> priceDtoList = priceAppService.findByUserType(userType);
        cpViewResultInfo.setData(priceDtoList);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("成功");
        return cpViewResultInfo;
    }

    /**
     * 会员充值费用
     * @return
     */
    @GetMapping("/price/rechargeFee.json")
    public CPViewResultInfo rechargeFee(String feeCode){
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        PriceDto priceDto = priceAppService.findByFeeCode(feeCode);
        cpViewResultInfo.setData(priceDto);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("成功");
        return cpViewResultInfo;
    }

}

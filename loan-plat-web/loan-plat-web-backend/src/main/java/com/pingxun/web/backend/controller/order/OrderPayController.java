package com.pingxun.web.backend.controller.order;

import com.pingxun.biz.order.app.dto.OrderPayDto;
import com.pingxun.biz.order.app.service.OrderPayAppService;
import com.pingxun.biz.price.app.dto.PriceDto;
import com.pingxun.biz.price.app.service.PriceAppService;
import com.pingxun.biz.user.app.dto.CwUserInfoDto;
import com.pingxun.biz.user.app.service.CwUserInfoAppService;
import com.pingxun.web.backend.controller.AbstractBackendController;
import com.pingxun.web.common.dto.CPViewResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * 订单管理
 * @author dujiangyu
 */
@RestController
public class OrderPayController extends AbstractBackendController{

    @Autowired
    private OrderPayAppService orderPayAppService;

    @Autowired
    private CwUserInfoAppService cwUserInfoAppService;

    @Autowired
    private PriceAppService priceAppService;

    /**
     * 查询支付记录
     * @param orderPayDto
     * @return
     */
    @PostMapping("/orderPay/findByCondition.json")
    @ResponseBody
    public CPViewResultInfo findByCondition(@RequestBody OrderPayDto orderPayDto){
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        Page<OrderPayDto> orderPayDtos = orderPayAppService.findByCondition(orderPayDto);
        cpViewResultInfo.setData(orderPayDtos);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("支付记录查询成功");
        return cpViewResultInfo;
    }

    /**
     * 查询支付记录详情
     * @param id
     * @return
     */
    @GetMapping("/orderPay/findById.json")
    @ResponseBody
    public CPViewResultInfo findById(Long id){
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        OrderPayDto orderPayDtos = orderPayAppService.findById(id);
        PriceDto priceDto= priceAppService.findByFeeCode(orderPayDtos.getFeeCode());
        orderPayDtos.setOldPrice(priceDto.getOriginalPrice());
        orderPayDtos.setActivePrice(priceDto.getPrice());
        CwUserInfoDto cwUserInfoDto = cwUserInfoAppService.findById(orderPayDtos.getUserId());
        orderPayDtos.setName(cwUserInfoDto.getName());
        orderPayDtos.setAccountNo(cwUserInfoDto.getAccountNo());
        orderPayDtos.setIdCard(cwUserInfoDto.getIdCard());
        orderPayDtos.setPhone(cwUserInfoDto.getPhone());
        orderPayDtos.setAreaName(cwUserInfoDto.getAreaName());
        orderPayDtos.setEffDate(cwUserInfoDto.getEffDate());
        cpViewResultInfo.setData(orderPayDtos);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("查询成功");
        return cpViewResultInfo;
    }
}

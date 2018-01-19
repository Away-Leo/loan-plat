package com.pingxun.biz.order.app.service;

import com.pingxun.biz.common.dto.Pages;
import com.pingxun.biz.order.app.dto.OrderPayDto;
import com.pingxun.biz.order.domain.entity.OrderPay;
import com.pingxun.biz.order.domain.service.OrderPayDomainService;
import com.pingxun.biz.user.app.dto.CwUserInfoDto;
import com.pingxun.biz.user.app.service.CwUserInfoAppService;
import com.pingxun.core.common.util.ObjectHelper;
import com.zds.common.lang.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * 订单支付服务
 * Created by dujy on 2017-12-07.
 */
@Transactional
@Service
public class OrderPayAppService {

    @Autowired
    private OrderPayDomainService orderPayDomainService;

    @Autowired
    private CwUserInfoAppService cwUserInfoAppService;
    /**
     * 新增订单支付
     * @param orderPayDto
     * @return
     */
    public Long create(OrderPayDto orderPayDto){
        return orderPayDomainService.update(orderPayDto).getId();
    }

    public OrderPayDto saveData(OrderPayDto orderPayDto){
        return orderPayDomainService.update(orderPayDto).to(OrderPayDto.class);
    }

    /**
     * 修改订单支付信息
     * @param orderPayDto
     * @return
     */
    public OrderPayDto update(OrderPayDto orderPayDto){
        return orderPayDomainService.update(orderPayDto).to(OrderPayDto.class);
    }

    /**
     * 查询订单支付详情
     * @param id
     * @return
     */
    public OrderPayDto findById(Long id){
        return orderPayDomainService.findById(id).to(OrderPayDto.class);
    }

    /**
     * @Author: Away
     * @Description: 按照签名码查找
     * @Param: signCode
     * @Return com.pingxun.biz.order.domain.entity.OrderPay
     * @Date 2017/12/14 11:12
     * @Copyright 重庆平讯数据
     */
    public OrderPayDto findBySignCode(String signCode) throws BusinessException{
        if(ObjectHelper.isNotEmpty(signCode)){
            OrderPay returnData=orderPayDomainService.findBySignCode(signCode);
            if(ObjectHelper.isNotEmpty(returnData)){
              return returnData.to(OrderPayDto.class);
            }else{
                throw new BusinessException("PX002","按照签名码查找支付数据为空");
            }
        }else{
            throw new BusinessException("PX001","按照签名码查找支付单出错，传入参数为空");
        }
    }

    /**
     * @Author: Away
     * @Description: 按照用户和费用项查找
     * @Param: userId
     * @Param: feeCode
     * @Return com.pingxun.biz.order.app.dto.OrderPayDto
     * @Date 2017/12/14 18:07
     * @Copyright 重庆平讯数据
     */
    public OrderPayDto findByUserIdAndFeeCode(Long userId, String feeCode) throws BusinessException{
        if(ObjectHelper.isNotEmpty(userId)&& ObjectHelper.isNotEmpty(feeCode)){
            OrderPay sourceData=orderPayDomainService.findByUserIdAndFeeCode(userId, feeCode);
            if(ObjectHelper.isNotEmpty(sourceData)){
                return sourceData.to(OrderPayDto.class);
            }else{
                return null;
            }
        }else{
            throw new BusinessException("PX001","按照userId和feeCode查找订单支付数据出错，参数为空");
        }
    }

    /**
     * @Author: Away
     * @Description: 按照返回的支付号和支付编码查找订单支付信息
     * @Param: backSerialNo
     * @Param: payNo
     * @Return com.pingxun.biz.order.domain.entity.OrderPay
     * @Date 2017/12/14 20:44
     * @Copyright 重庆平讯数据
     */
    public OrderPayDto findByBackSerialNoAndPayNo(String backSerialNo, String payNo) throws Exception{
        if(ObjectHelper.isNotEmpty(backSerialNo)&& ObjectHelper.isNotEmpty(payNo)){
            OrderPay orderPay=this.orderPayDomainService.findByBackSerialNoAndPayNo(backSerialNo, payNo);
            if(ObjectHelper.isNotEmpty(orderPay)){
                return orderPay.to(OrderPayDto.class);
            }else{
                return null;
            }
        }else{
            throw new BusinessException("PX001","按照返回的支付号和支付编码查找订单支付信息参数为空");
        }
    }

    /**
     * 查询支付交易记录
     * @return
     */
    public Page<OrderPayDto> findByCondition(OrderPayDto dto){
        Page<OrderPayDto> payDtos = Pages.map(orderPayDomainService.findByCondition(dto),OrderPayDto.class);
        for(OrderPayDto orderPayDto:payDtos){
            CwUserInfoDto cwUserInfoDto = cwUserInfoAppService.findById(orderPayDto.getUserId());
            orderPayDto.setName(cwUserInfoDto.getName());
            orderPayDto.setEffDate(cwUserInfoDto.getEffDate());

        }
        return payDtos;
    }
}

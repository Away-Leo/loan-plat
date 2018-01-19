package com.pingxun.biz.order.app.service;

import com.pingxun.biz.CPContext;
import com.pingxun.biz.common.dto.Pages;
import com.pingxun.biz.maillist.app.dto.MailListDto;
import com.pingxun.biz.order.app.dto.OrderDto;
import com.pingxun.biz.order.domain.entity.Order;
import com.pingxun.biz.order.domain.service.OrderDomainService;
import com.pingxun.biz.price.app.dto.PriceDto;
import com.pingxun.biz.price.app.service.PriceAppService;
import com.pingxun.core.common.util.ObjectHelper;
import com.zds.common.lang.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

/**
 * 订单服务
 * Created by dujy on 2017-05-20.
 */
@Transactional
@Service
public class OrderAppService {

    @Autowired
    private OrderDomainService orderDomainService;

    @Autowired
    private PriceAppService priceAppService;

    /**
     * 新增订单
     * @param orderDto
     * @return
     */
    public Long create(OrderDto orderDto){
        return orderDomainService.update(orderDto).getId();
    }

    /**
     * 修改订单信息
     * @param orderDto
     * @return
     */
    public OrderDto update(OrderDto orderDto){
        return orderDomainService.update(orderDto).to(OrderDto.class);
    }

    /**
     * 查询消息详情
     * @param id
     * @return
     */
    public OrderDto findById(Long id){
        if(ObjectHelper.isNotEmpty(id)){
            Order order=orderDomainService.findById(id);
            if(ObjectHelper.isNotEmpty(order)){
                return order.to(OrderDto.class);
            }else{
                throw new BusinessException("PX002","按照id查找订单数据为空");
            }
        }else{
            throw new BusinessException("PX001","按照id查找订单出错，id为空");
        }
    }

    public OrderDto newOrder(String feeCode) throws BusinessException{
        if(ObjectHelper.isNotEmpty(feeCode)){
            PriceDto priceDto=this.priceAppService.findByFeeCode(feeCode);
            OrderDto orderDto=new OrderDto();
            orderDto.setFeeCode(feeCode);
            orderDto.setFeeItem(priceDto.getFeeItem());
            orderDto.setOrderDate(new Date());
            orderDto.setOrderFee(priceDto.getPrice());
            orderDto.setOrderDescribe(priceDto.getFeeItem());
            orderDto.setOrderNo(new Date().getTime()+"");
            orderDto.setStatus(0);
            orderDto.setUserId(CPContext.getContext().getSeUserInfo().getId());
            return this.orderDomainService.create(orderDto).to(OrderDto.class);
        }else{
            throw new BusinessException("PX001","参数为空");
        }
    }


    /**
     * 查询用户通讯录
     * @return
     */
    public Page<OrderDto> findByCondition(OrderDto dto){
        return Pages.map(orderDomainService.findByCondition(dto),OrderDto.class);
    }
}

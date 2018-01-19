package com.pingxun.biz.order.domain.service;

import com.google.common.collect.Lists;
import com.pingxun.biz.CPContext;
import com.pingxun.biz.CwException;
import com.pingxun.biz.maillist.app.dto.MailListDto;
import com.pingxun.biz.maillist.domain.entity.MailList;
import com.pingxun.biz.order.app.dto.OrderDto;
import com.pingxun.biz.order.domain.entity.Order;
import com.pingxun.biz.order.domain.repository.OrderRepository;
import com.pingxun.biz.user.app.dto.CwUserInfoDto;
import com.pingxun.core.common.util.ObjectHelper;
import com.zds.common.lang.exception.BusinessException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * 订单服务
 * Created by dujy on 2017-05-20.
 */
@Service
public class OrderDomainService {

    @Autowired
    private OrderRepository repository;

    /**
     * 新增订单
     * @param orderDto
     * @return
     */
    public Order create(OrderDto orderDto){
        Order Order = new Order();
        Order.from(orderDto);
        return repository.save(Order);
    }

    /**
     * 修改订单状态
     * @param orderDto
     * @return
     */
    public Order update(OrderDto orderDto){
        Order order = repository.findOne(orderDto.getId());
        if(order == null){
            CwException.throwIt("订单不存在");
        }
        order.from(orderDto);
        return order;
    }

    /**
     * 查询订单详情
     * @param id
     * @return
     */
    public Order findById(Long id) throws BusinessException{
        Order returnData=repository.findOne(id);
        if(ObjectHelper.isNotEmpty(returnData)){
            return returnData;
        }else{
            throw new BusinessException("PX002","根据id查找订单出错，无此结果");
        }
    }

    /**
     * 查询已付款订单
     * @param orderDto
     * @return
     */
    public Page<Order> findByCondition(OrderDto orderDto){
        String[] fields = {"orderDate"};
        orderDto.setSortFields(fields);
        orderDto.setSortDirection(Sort.Direction.DESC);
        Specification<Order> supplierSpecification = (Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> predicates = Lists.newArrayListWithCapacity(20);

            if(StringUtils.isNotEmpty(orderDto.getOrderNo())){
                predicates.add(cb.like(root.get("orderNo"), "%"+orderDto.getOrderNo()+"%"));
            }

            query.where(cb.and(predicates.toArray(new Predicate[0])));
            return query.getRestriction();
        };
        return repository.findAll(supplierSpecification, orderDto.toPage());
    }
}

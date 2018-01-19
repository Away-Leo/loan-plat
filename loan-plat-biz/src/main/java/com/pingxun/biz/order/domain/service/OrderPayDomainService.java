package com.pingxun.biz.order.domain.service;

import com.google.common.collect.Lists;
import com.pingxun.biz.CwException;
import com.pingxun.biz.order.app.dto.OrderPayDto;
import com.pingxun.biz.order.domain.entity.OrderPay;
import com.pingxun.biz.order.domain.repository.OrderPayRepository;
import com.pingxun.biz.order.domain.repository.OrderRepository;
import com.pingxun.core.common.util.ObjectHelper;
import com.pingxun.core.common.util.Utils;
import com.zds.common.lang.exception.BusinessException;
import com.zds.common.util.StringUtils;
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
 * 订单支付服务
 * Created by dujy on 2017-05-20.
 */
@Service
public class OrderPayDomainService {

    @Autowired
    private OrderPayRepository repository;
    /**
     * 新增订单支付
     * @param orderPayDto
     * @return
     */
    public OrderPay create(OrderPayDto orderPayDto){
        OrderPay orderPay = new OrderPay();
        orderPay.from(orderPayDto);
        return repository.save(orderPay);
    }

    /**
     * 修改订单支付状态
     * @param orderPayDto
     * @return
     */
    public OrderPay update(OrderPayDto orderPayDto){
        OrderPay orderPay = repository.findOne(orderPayDto.getId());
        if(orderPay == null){
            CwException.throwIt("订单支付记录不存在");
        }
        orderPay.from(orderPayDto);
        return orderPay;
    }

    /**
     * 查询订单支付详情
     * @param id
     * @return
     */
    public OrderPay findById(Long id){
        return repository.findOne(id);
    }

    /**
     * @Author: Away
     * @Description: 按照签名码查找
     * @Param: signCode
     * @Return com.pingxun.biz.order.domain.entity.OrderPay
     * @Date 2017/12/14 11:08
     * @Copyright 重庆平讯数据
     */
    public OrderPay findBySignCode(String signCode){
        return repository.findBySignCode(signCode);
    }

    /**
     * @Author: Away
     * @Description: 通过用户id和费用项编号查找数据
     * @Param: userId
     * @Param: feeCode
     * @Return com.pingxun.biz.order.domain.entity.OrderPay
     * @Date 2017/12/14 18:05
     * @Copyright 重庆平讯数据
     */
    public OrderPay findByUserIdAndFeeCode(Long userId, String feeCode){
        return this.repository.findByUserIdAndFeeCode(userId, feeCode);
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
    public OrderPay findByBackSerialNoAndPayNo(String backSerialNo, String payNo){
        return this.repository.findByBackSerialNoAndPayNo(backSerialNo, payNo);
    }

    /**
     * @Author: Away
     * @Description: 按照支付号查找致富数据
     * @Param: payNo
     * @Return com.pingxun.biz.order.domain.entity.OrderPay
     * @Date 2017/12/15 21:47
     * @Copyright 重庆平讯数据
     */
    public OrderPay findByPayNo(String payNo) throws BusinessException{
        OrderPay sourceData=this.repository.findByPayNo(payNo);
        if(ObjectHelper.isNotEmpty(sourceData)){
            return sourceData;
        }else{
            throw new BusinessException("PX002","按照支付表支付号查找数据为空");
        }
    }

    /**
     * 查询支付收入
     * @param orderPayDto
     * @return
     */
    public Page<OrderPay> findByCondition(OrderPayDto orderPayDto){
        String[] fields = {"orderDate"};
        orderPayDto.setSizePerPage(1000);
        orderPayDto.setSortFields(fields);
        orderPayDto.setSortDirection(Sort.Direction.DESC);
        Specification<OrderPay> supplierSpecification = (Root<OrderPay> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> predicates = Lists.newArrayListWithCapacity(20);

            if(StringUtils.isNotEmpty(orderPayDto.getPayNo())){
                predicates.add(cb.like(root.get("payNo"), "%"+orderPayDto.getPayNo()+"%"));
            }
            if(StringUtils.isNotEmpty(orderPayDto.getBackSerialNo())){
                predicates.add(cb.like(root.get("backSerialNo"), "%"+orderPayDto.getBackSerialNo()+"%"));
            }

            if(StringUtils.isNotEmpty(orderPayDto.getStartDate())||StringUtils.isNotEmpty(orderPayDto.getEndDate())){
                predicates.add(cb.between(root.get("payDate"), Utils.strConvertDate(orderPayDto.getStartDate()),Utils.strConvertDate(orderPayDto.getEndDate())));
            }

            predicates.add(cb.equal(root.get("isPay"), Boolean.TRUE));

            query.where(cb.and(predicates.toArray(new Predicate[0])));
            return query.getRestriction();
        };
        return repository.findAll(supplierSpecification, orderPayDto.toPage());
    }

}

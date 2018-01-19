package com.pingxun.biz.user.domain.service;

import com.google.common.collect.Lists;
import com.pingxun.biz.CPContext;
import com.pingxun.biz.CwException;
import com.pingxun.biz.user.app.dto.CwUserInfoDto;
import com.pingxun.biz.user.domain.entity.CwUserInfo;
import com.pingxun.biz.user.domain.repository.SeUserInfoRepository;
import com.pingxun.core.common.util.Utils;
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
 * 用户信息服务
 * Created by dujy on 2017-05-20.
 */
@Service
public class CwUserInfoDomainService {

    @Autowired
    private SeUserInfoRepository repository;
    /**
     * 新增客户信息
     * @param cwUserInfoDto
     * @return
     */
    private CwUserInfo create(CwUserInfoDto cwUserInfoDto){
        CwUserInfo cwUserInfo = new CwUserInfo();
        cwUserInfo.from(cwUserInfoDto);
        return repository.save(cwUserInfo);
    }

    /**
     * 修改客户信息
     * @param cwUserInfoDto
     * @return
     */
    public CwUserInfo update(CwUserInfoDto cwUserInfoDto){
        //借款起始金额
        CwUserInfo cwUserInfo = repository.findByUserId(cwUserInfoDto.getUserId());
        if(cwUserInfo == null){
            cwUserInfo = create(cwUserInfoDto);
        }else{
            Boolean isAuth = cwUserInfo.getIsAuthentication();
            Boolean isPay = cwUserInfo.getIsPay();
            if(cwUserInfoDto.getUserType()==0){
                cwUserInfoDto.setUserType(cwUserInfo.getUserType());
            }
            cwUserInfo.from(cwUserInfoDto);
            cwUserInfo.prepareSave();
            //保存原始认证状态
            cwUserInfo.setIsAuthentication(isAuth);
            cwUserInfo.setIsPay(isPay);

        }
        return cwUserInfo;
    }

    /**
     * 修改消息提示接口
     * @param cwUserInfoDto
     * @return
     */
    public CwUserInfo updateMessageTip(CwUserInfoDto cwUserInfoDto){
        //借款起始金额
        CwUserInfo cwUserInfo = repository.findByUserId(cwUserInfoDto.getUserId());
        cwUserInfo.setMessageTipFlag(cwUserInfoDto.getMessageTipFlag());
        return cwUserInfo;
    }



    /**
     * 修改客户信息
     * @param cwUserInfoDto
     * @return
     */
    public CwUserInfo updateUser(CwUserInfoDto cwUserInfoDto){
        //借款起始金额
        CwUserInfo cwUserInfo = repository.findByUserId(cwUserInfoDto.getUserId());
        if(cwUserInfo == null){
            cwUserInfo = create(cwUserInfoDto);
        }else{
            if(cwUserInfoDto.getUserType()==0){
                cwUserInfoDto.setUserType(cwUserInfo.getUserType());
            }
            cwUserInfo.from(cwUserInfoDto);
        }
        return cwUserInfo;
    }

    /**
     * 认证成功接口
     * @param cwUserInfoDto
     * @return
     */
    public CwUserInfo authenticationSuccess(CwUserInfoDto cwUserInfoDto){
        //借款起始金额
        CwUserInfo cwUserInfo = repository.findByUserId(cwUserInfoDto.getUserId());
        if(cwUserInfo == null)
        {
            CwException.throwIt("用户不存在");
        }else{
            cwUserInfo.setIsAuthentication(Boolean.TRUE);
        }
        return cwUserInfo;
    }

    /**
     * 支付成功修改用户支付状态接口
     * @param cwUserInfoDto
     * @return
     */
    public CwUserInfo paySuccess(CwUserInfoDto cwUserInfoDto){
        //借款起始金额
        CwUserInfo cwUserInfo = repository.findByUserId(cwUserInfoDto.getUserId());
        if(cwUserInfo == null){
            CwException.throwIt("用户不存在");
        }else{
            cwUserInfo.setIsPay(Boolean.TRUE);
            if(cwUserInfo.getUserType()==2) {
                cwUserInfo.setEffDate(Utils.dateAddMonth(cwUserInfoDto.getVipValidMonth()));
            }
        }
        return cwUserInfo;
    }

    /**
     * 查询客户信息
     * @param id
     * @return
     */
    public CwUserInfo findById(Long id){
        return repository.findByUserId(id);
    }

    /**
     * @Author: Away
     * @Description: 按照是否认证和身份证查找
     * @Param: isAuthentication
     * @Param: idCard
     * @Return com.pingxun.biz.user.domain.entity.CwUserInfo
     * @Date 2018/1/8 11:32
     * @Copyright 重庆平讯数据
     */
    public CwUserInfo findByIsAuthenticationAndIdCardAndUserType(boolean isAuthentication,String idCard,int userType){
        return this.repository.findByIsAuthenticationAndIdCardAndUserType(isAuthentication, idCard,userType);
    }

    /**
     * 客户管理
     * @param cwUserInfoDto
     * @return
     */
    public Page<CwUserInfo> findByCondition(CwUserInfoDto cwUserInfoDto){
        String[] fields = {"registerDate"};
        cwUserInfoDto.setSizePerPage(1000);
        cwUserInfoDto.setSortFields(fields);
        cwUserInfoDto.setSortDirection(Sort.Direction.DESC);
        Specification<CwUserInfo> supplierSpecification = (Root<CwUserInfo> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> predicates = Lists.newArrayListWithCapacity(20);

            if(StringUtils.isNotEmpty(cwUserInfoDto.getName())){
                predicates.add(cb.like(root.get("name"), "%"+cwUserInfoDto.getName()+"%"));
            }
            if(StringUtils.isNotEmpty(cwUserInfoDto.getPhone())){
                predicates.add(cb.like(root.get("phone"), "%"+cwUserInfoDto.getPhone()+"%"));
            }

            if(StringUtils.isNotEmpty(cwUserInfoDto.getStartDate())||StringUtils.isNotEmpty(cwUserInfoDto.getEndDate())){
                predicates.add(cb.between(root.get("registerDate"), Utils.strConvertDate(cwUserInfoDto.getStartDate()),Utils.strConvertDate(cwUserInfoDto.getEndDate())));
            }

            if(StringUtils.isNotEmpty(cwUserInfoDto.getIdCard())){
                predicates.add(cb.like(root.get("idCard"), "%"+cwUserInfoDto.getIdCard()+"%"));
            }
            predicates.add(cb.equal(root.get("userType"), cwUserInfoDto.getUserType()));

            query.where(cb.and(predicates.toArray(new Predicate[0])));
            return query.getRestriction();
        };
        return repository.findAll(supplierSpecification, cwUserInfoDto.toPage());
    }
}

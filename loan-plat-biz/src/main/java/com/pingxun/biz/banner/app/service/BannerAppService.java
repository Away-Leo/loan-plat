package com.pingxun.biz.banner.app.service;

import com.pingxun.biz.banner.app.dto.BannerDto;
import com.pingxun.biz.banner.domain.entity.Banner;
import com.pingxun.biz.banner.domain.service.BannerDomainService;
import com.pingxun.biz.common.dto.Pages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * banner服务
 * Created by dujy on 2017-05-20.
 */
@Transactional
@Service
public class BannerAppService {

    @Autowired
    private BannerDomainService bannerDomainService;

    /**
     * 新增产品
     * @param bannerDto
     * @return
     */
    public Long create(BannerDto bannerDto)
    {
        return bannerDomainService.create(bannerDto).getId();
    }

    /**
     * 新增产品
     * @param bannerDto
     * @return
     */
    public Long update(BannerDto bannerDto)
    {
        return bannerDomainService.update(bannerDto).getId();
    }

    /**
     * 停用启用产品
     * @param bannerDto
     * @return
     */
    public void enable(BannerDto bannerDto)
    {
        bannerDomainService.enable(bannerDto);
    }

    /**
     * 查询产品详情
     * @param id
     * @return
     */
    public BannerDto findById(Long id)
    {
        BannerDto bannerDto = new BannerDto();
        Banner banner = bannerDomainService.findById(id);
        if(banner!=null){
            bannerDto = banner.to(BannerDto.class);
        }
        return bannerDto;
    }

    /**
     * 按条件查询banner图标
     * @param dto
     * @return
     */
    public Page<BannerDto> findByCondition(BannerDto dto)
    {
        return Pages.map(bannerDomainService.findByCondition(dto),BannerDto.class);
    }

    /**
     * 查询banner
     * @return
     */
    public List<BannerDto> findAll(String position, String versionNo)
    {
        List<BannerDto> bankDtoList = new ArrayList<>();
        List<Banner> bankList = bannerDomainService.findByBannerPosition(position,versionNo);
        for(Banner bank:bankList)
        {
            if(versionNo!=null&&!"1".equals(versionNo)){
                Boolean addFlag=false;
                if(versionNo.toUpperCase().contains("IOS")&&bank.getIsApple()){
                    bankDtoList.add(bank.to(BannerDto.class));
                    addFlag=Boolean.TRUE;
                }
                if(!versionNo.toUpperCase().contains("IOS")&&bank.getIsAndroid()&!addFlag){
                    bankDtoList.add(bank.to(BannerDto.class));
                }
            }else {
                bankDtoList.add(bank.to(BannerDto.class));
            }
        }
        return bankDtoList;
    }

}

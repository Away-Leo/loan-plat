package com.pingxun.biz.product.domain.service;

import com.google.common.collect.Lists;
import com.pingxun.biz.CPContext;
import com.pingxun.biz.CwException;
import com.pingxun.biz.apply.app.dto.ApplyDto;
import com.pingxun.biz.apply.app.service.ApplyAppService;
import com.pingxun.biz.banner.app.service.BannerAppService;
import com.pingxun.biz.log.app.LogEnum;
import com.pingxun.biz.log.app.dto.LogDto;
import com.pingxun.biz.log.app.service.LogAppService;
import com.pingxun.biz.message.app.dto.MessageDto;
import com.pingxun.biz.message.app.service.MessageAppService;
import com.pingxun.biz.parameter.app.dto.SpinnerParameterDto;
import com.pingxun.biz.parameter.app.service.SpinnerParameterAppService;
import com.pingxun.biz.product.app.dto.ProductDto;
import com.pingxun.biz.product.app.dto.ProductRecommendListDto;
import com.pingxun.biz.product.app.dto.ProductSearchDto;
import com.pingxun.biz.product.app.dto.ProductVersionDto;
import com.pingxun.biz.product.domain.dao.ProductDao;
import com.pingxun.biz.product.domain.entity.Product;
import com.pingxun.biz.product.domain.entity.ProductAuditVersion;
import com.pingxun.biz.product.domain.entity.ProductVersion;
import com.pingxun.biz.product.domain.repository.ProductAuditRepository;
import com.pingxun.biz.product.domain.repository.ProductRepository;
import com.pingxun.biz.product.domain.repository.ProductVersionRepository;
import com.pingxun.core.common.util.AppUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 产品服务
 * Created by dujy on 2017-05-20.
 */
@Service
public class ProductDomainService {
    public static Logger logger = LoggerFactory.getLogger(ProductDomainService.class);

    @Autowired
    private ProductRepository repository;
    @Autowired
    private ProductVersionRepository productVersionRepository;
    @Autowired
    private ProductAuditRepository productAuditRepository;
    @Autowired
    private LogAppService logAppService;

    @Autowired
    private SpinnerParameterAppService spinnerParameterAppService;

    @Autowired
    private ApplyAppService applyAppService;

    @Autowired
    private ProductDao productDao;

    /**
     * 新增产品
     * @param productDto
     * @return
     */
    public Product create(ProductDto productDto){
        Product product = new Product();
        product.from(productDto);
        product.setSaveDate(new Date());
        product.setOperateNo(CPContext.getContext().getSeUserInfo().getId());
        return repository.save(product);
    }

    /**
     * 修改产品
     * @param productDto
     * @return
     */
    public Product update(ProductDto productDto){
        Product product = repository.findOne(productDto.getId());
        if(product == null){
            CwException.throwIt("产品不存在");
        }
        Boolean isValid = product.getIsValid();
        product.from(productDto);
        if(isValid){
            product.setIsValid(Boolean.TRUE);
        }
        repository.save(product);
        //更新产品数据
        updateDataVersion();

        return product;
    }

    /**
     * 更新产品版本数据
     */
    private void updateDataVersion(){
        List<ProductVersion>  productVersionList = productVersionRepository.findAll();
        if(productVersionList.size() == 0 ){
            ProductVersion productVersion1 = new ProductVersion();
            productVersion1.setDataVersion(1);
            productVersionRepository.save(productVersion1);
        }else {
            ProductVersion productVersion = productVersionRepository.findOne(productVersionList.get(0).getId());
            productVersion.setDataVersion(productVersion.getDataVersion() + 1);
        }
    }
    /**
     * 产品停用、启用
     * @param productDto
     * @return
     */
    public Product enable(ProductVersionDto productDto){
        Product product = repository.findOne(productDto.getId());
        if(product == null){
            CwException.throwIt("产品不存在");
        }
        if(product.getIsValid()) {
            product.setIsValid(Boolean.FALSE);
            product.setOnlineDate(new Date());//下架时间
        }else{
            product.setIsValid(Boolean.TRUE);
            if(product.getOnlineDate()==null) {
                product.setOnlineDate(new Date());//上架时间
            }
        }
        return product;
    }

    /**
     * 查询产品详情
     * @param id
     * @return
     */
    public Product findById(Long id){
        Product product = repository.findOne(id);
        if(product==null) {
            CwException.throwIt("id不能为空");
        }
        return product;
    }

    /**
     * 查询所有产品列表
     * @return
     */
    public Page<Product> findAll(ProductSearchDto searchDto) {
        searchDto.setSizePerPage(200);
        String[] fields = {"showOrder"};
        searchDto.setSortFields(fields);
        searchDto.setSortDirection(Sort.Direction.ASC);
        Specification<Product> spec = new Specification<Product>() {
            @Override
            public Predicate toPredicate(Root<Product> root,
                                         CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<Predicate>();
                // list.add(cb.equal(root.get("isValid"),Boolean.TRUE));
                list.add(cb.notEqual(root.get("channel"),"重庆平讯数据服务有限公司"));
                if(!CPContext.getContext().getSeUserInfo().getUsername().equals("admin")&&
                        !CPContext.getContext().getSeUserInfo().getUsername().equals("zhouyue")&&
                        !CPContext.getContext().getSeUserInfo().getUsername().equals("yangxin")) {
//                    list.add(cb.equal(root.get("operateNo"), CPContext.getContext().getSeUserInfo().getId()));
                }
                Predicate[] p = new Predicate[list.size()];
                return cb.and(list.toArray(p));
            }
        };
        return repository.findAll(spec,searchDto.toPage());
    }

    /**
     * 按条件查询产品列表V1.0秒必贷款
     * @param searchDto
     * @return
     */
    public Page<Product> findByCondition(ProductSearchDto searchDto){
        //设置排序
        setProductSort(searchDto);
        searchDto.setSizePerPage(16);
        Specification<Product> supplierSpecification = (Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> predicates = Lists.newArrayListWithCapacity(20);
            //后台查询所有产品，app查询生效的产品
            if(searchDto.getSysType()==0) {
                predicates.add(cb.equal(root.get("isValid"), Boolean.TRUE));
            }
            //查询上架状态
            if(searchDto.getIsValid()!=null){
                predicates.add(cb.equal(root.get("isValid"), searchDto.getIsValid()));
            }
            //产品类型：新口子，全部
            if(searchDto.getProductType()!=null&&!"all".equals(searchDto.getProductType())){
                predicates.add(cb.equal(root.get("productType"), searchDto.getProductType()));
            }

            //指数类型 根据指数类型排序
            if(searchDto.getZsType()!=null&&!"all".equals(searchDto.getZsType())){
                // predicates.add(cb.equal(root.get("type"), searchDto.getZsType()));
            }

            //根据不同的应用展示不同的产品
            if(searchDto.getAppName()!=null&&!"".equals(searchDto.getAppName())&&!"all".equals(searchDto.getAppName()))
            {
                predicates.add(cb.like(root.get("belongApp"), "%"+searchDto.getAppName()+"%"));
            }
            if(searchDto.getSysType()==1){
                predicates.add(cb.notEqual(root.get("channel"), "重庆平讯数据服务有限公司"));
                if(!CPContext.getContext().getSeUserInfo().getUsername().equals("admin")&&
                        !CPContext.getContext().getSeUserInfo().getUsername().equals("zhouyue")&&
                        !CPContext.getContext().getSeUserInfo().getUsername().equals("yangxin")) {
                   // predicates.add(cb.equal(root.get("operateNo"), CPContext.getContext().getSeUserInfo().getId()));
                }
            }
            //产品名称
            if(!Objects.isNull(searchDto.getName())){
                predicates.add(cb.like(root.get("name"),"%"+searchDto.getName()+"%"));
            }
            //合作模式
            if(!Objects.isNull(searchDto.getCooperationModel())&&!"".equals(searchDto.getCooperationModel())){
                predicates.add(cb.equal(root.get("cooperationModel"),searchDto.getCooperationModel()));
            }
            //贷款分类
            if(!Objects.isNull(searchDto.getLoanType())&&!"".equals(searchDto.getLoanType())){
                predicates.add(cb.equal(root.get("sourceType"),searchDto.getLoanType()));
            }
            //流量控制项目
            if(searchDto.getSysType()==0) {
                predicates.add(cb.or(cb.and(cb.lessThanOrEqualTo(root.get("limitUserTop"), root.get("todayApplyUser"))
                        , cb.or(cb.equal(root.get("isHidden"), 2),cb.equal(root.get("isHidden"), 0))),
                       cb.lessThanOrEqualTo(root.get("todayApplyUser"), root.get("limitUserTop"))
                                ));
            }

            //APP构造查询条件 贷款金额 贷款期限，贷款类型
            if(!Objects.isNull(searchDto.getLoanAmount())){
                SpinnerParameterDto spinnerParameterDto = spinnerParameterAppService.findByCode(searchDto.getLoanAmount());
                if(spinnerParameterDto!=null){
                    predicates.add(cb.and(cb.greaterThanOrEqualTo(root.get("startAmount"),spinnerParameterDto.getStartValue()),
                            cb.lessThanOrEqualTo(root.get("startAmount"),spinnerParameterDto.getEndValue())));
                }
            }
            if(!Objects.isNull(searchDto.getLoanPeriod())){
                SpinnerParameterDto spinnerParameterDto = spinnerParameterAppService.findByCode(searchDto.getLoanPeriod());
                if(spinnerParameterDto!=null){
                    predicates.add(cb.and(cb.greaterThanOrEqualTo(root.get("startPeriod"),spinnerParameterDto.getStartValue()),
                            cb.lessThanOrEqualTo(root.get("startPeriod"),spinnerParameterDto.getEndValue())));
                }
            }

            /**终端判断*/
            checkTerminal(root,cb,predicates,searchDto);

            query.where(cb.and(predicates.toArray(new Predicate[0])));
            return query.getRestriction();
        };
        return repository.findAll(supplierSpecification, searchDto.toPage());
    }

    /**
     * 根据app设置排序规则
     * @param searchDto
     */
    private void setProductSort(ProductSearchDto searchDto){
        if(AppUtils.appNameXSD.equals(searchDto.getAppName().toUpperCase()))
        {
            String[] fields = {"showOrderXsd"};
            searchDto.setSortFields(fields);
        }else  if(AppUtils.appNameJDS.equals(searchDto.getAppName().toUpperCase()))
        {
            String[] fields = {"showOrderJds"};
            searchDto.setSortFields(fields);
        }else  if(AppUtils.appNameJQW.equals(searchDto.getAppName().toUpperCase()))
        {
            String[] fields = {"showOrderJqw"};
            searchDto.setSortFields(fields);
        }else  if(AppUtils.appNameDKQB.equals(searchDto.getAppName().toUpperCase()))
        {
            String[] fields = {"showOrderDkqb"};
            searchDto.setSortFields(fields);
        }else  if(AppUtils.appNameDSQB.equals(searchDto.getAppName().toUpperCase()))
        {
            String[] fields = {"showOrderDsqb"};
            searchDto.setSortFields(fields);
        }else  if(AppUtils.appNameLSQD.equals(searchDto.getAppName().toUpperCase()))
        {
            String[] fields = {"showOrderLsqd"};
            searchDto.setSortFields(fields);
        }else  if(AppUtils.appNamePXQB.equals(searchDto.getAppName().toUpperCase()))
        {
            String[] fields = {"showOrderPxqb"};
            searchDto.setSortFields(fields);
        }else  if(AppUtils.appNameLYB.equals(searchDto.getAppName().toUpperCase())){
            String[] fields = {"showOrderLyb"};
            searchDto.setSortFields(fields);
        }else{
            String[] fields = {"showOrder"};
            searchDto.setSortFields(fields);
        }
        //综合指数排序
        if(searchDto.getZsType()!=null){
            if("zhzs".equals(searchDto.getZsType())){
                String[] fields = {"showOrderXed"};
                searchDto.setSortFields(fields);
            }else if("cgl".equals(searchDto.getZsType())){
                String[] fields = {"showOrderJqs"};
                searchDto.setSortFields(fields);
            }else if("fksd".equals(searchDto.getZsType())){
                String[] fields = {"showOrderDkh"};
                searchDto.setSortFields(fields);
            }else if("dkll".equals(searchDto.getZsType())){
                String[] fields = {"showOrderJqhh"};
                searchDto.setSortFields(fields);
            }else if("zged".equals(searchDto.getZsType())){
                String[] fields = {"showOrderJdjq"};
                searchDto.setSortFields(fields);
            }else if("rq".equals(searchDto.getZsType())){
                String[] fields = {"showOrderDkt"};
                searchDto.setSortFields(fields);
            }
        }else{
            //IOS单独排序处理
            if(!Objects.isNull(searchDto.getChannelNo())){
                if(searchDto.getChannelNo().toUpperCase().contains("IOS")||
                        searchDto.getChannelNo().toUpperCase().contains("backend".toUpperCase())){
                    String[] fields = {"showOrderJqw"};
                    searchDto.setSortFields(fields);
                }else{
                    String[] fields = {"showOrder"};
                    searchDto.setSortFields(fields);
                }
            }else{
                String[] fields = {"showOrder"};
                searchDto.setSortFields(fields);
            }
        }


        if(searchDto.getSortColumn()!=null)
        {
            String[] fields = {searchDto.getSortColumn()};
            searchDto.setSortFields(fields);
            if(Sort.Direction.ASC.toString().equals(searchDto.getSortDesc())) {
                searchDto.setSortDirection(Sort.Direction.DESC);
            }else{
                searchDto.setSortDirection(Sort.Direction.ASC);
            }
        }else{
            if(Sort.Direction.ASC.toString().equals(searchDto.getSortDesc())) {
                searchDto.setSortDirection(Sort.Direction.DESC);
            }else{
                searchDto.setSortDirection(Sort.Direction.ASC);
            }
        }
    }

    /**
     * 查询版本号显示数据
     * @param versionNo
     * @return
     */
    private Boolean checkVersionShowData(String versionNo){
        ProductAuditVersion productAuditVersion = productAuditRepository.findByDataVersion(versionNo);
        if(Objects.isNull(productAuditVersion))
        {
            return Boolean.FALSE;
        }
        return productAuditVersion.getIsAudit();
    }

    /**
     * 判断来源终端
     * @param root
     * @param cb
     * @param predicates
     * @param searchDto
     */
    private void checkTerminal(Root<Product> root,CriteriaBuilder cb,List<Predicate> predicates,ProductSearchDto searchDto){
        Boolean versionAuditFlag = checkVersionShowData(searchDto.getVersionNo());
        if(!Objects.isNull(searchDto.getChannelNo())) {
            if (searchDto.getChannelNo().toUpperCase().contains("IOS")) {
                if (!versionAuditFlag) {
                    predicates.add(cb.equal(root.get("appleFlag"), Boolean.FALSE));
                    predicates.add(cb.equal(root.get("androidFlag"), Boolean.FALSE));
                } else {
                    predicates.add(cb.equal(root.get("appleFlag"), Boolean.TRUE));
                }
            } else if (searchDto.getChannelNo().toUpperCase().contains("WECHAT")) {
                predicates.add(cb.equal(root.get("wechatFlag"), Boolean.TRUE));
            } else {
                if (!"backend".toUpperCase().equals(searchDto.getChannelNo().toUpperCase())) {
                    if(!Objects.isNull(searchDto.getChannelNo())) {
                        Boolean isAudit = productDao.findAuditChannel(searchDto.getChannelNo(),searchDto.getVersionNo());
                        if(isAudit) {
                            //读取ios配置
                            if(!Objects.isNull(searchDto.getVersionNo())){
                                isAudit = checkVersionShowData(searchDto.getVersionNo());
                                //vivo审核产品特殊处理
                                if (!isAudit) {
                                    predicates.add(cb.equal(root.get("androidFlag"), Boolean.FALSE));
                                    predicates.add(cb.equal(root.get("appleFlag"), Boolean.FALSE));
                                }else{
                                    predicates.add(cb.equal(root.get("androidFlag"), Boolean.TRUE));
                                }
                            }else{
                                predicates.add(cb.equal(root.get("androidFlag"), Boolean.TRUE));
                            }
                        }else{
                            predicates.add(cb.equal(root.get("androidFlag"), Boolean.TRUE));
                        }
                    }
                }
            }
        }else{
            predicates.add(cb.equal(root.get("androidFlag"), Boolean.TRUE));
        }
    }

    /**
     * 借款申请
     * @param logDto
     */
    public void applyLoan(LogDto logDto){
       //产品申请
        logger.info("logType:{},productId:{},userId:{}",logDto.getType(),logDto.getProductId(),CPContext.getContext().getSeUserInfo().getUsername());
        if(logDto.getProductId()==null){
            CwException.throwIt("产品不存在！");
        }
        Product product= repository.findOne(logDto.getProductId());
        if(product!=null) {
            //product.setClickNum(product.getClickNum()+11);
            //product.setTodayApplyUser(productDao.findProductUserById(product.getId()).getApplyNum());
            logDto.setType(LogEnum.APPLY_LOAN);
        }

        //记录数据日志
        logDto.setApplyDate(new Date());
        logDto.setUserId(CPContext.getContext().getSeUserInfo().getId());
        logAppService.create(logDto);
        //增加借款申请
        if(logDto.getProductId()!=null) {
            ApplyDto applyDto = new ApplyDto();
            applyDto.setUserId(CPContext.getContext().getSeUserInfo().getId());
            applyDto.setProductId(logDto.getProductId());
            applyDto.setApplyDate(new Date());
            applyDto.setChannelNo(logDto.getChannelNo());
            applyDto.setAppName(logDto.getAppName());
            applyDto.setApplyArea(logDto.getApplyArea());
            applyAppService.create(applyDto);
            //记录发送消息日志
            sendRegisterMessage(logDto);
        }
    }

    @Autowired
    private MessageAppService messageAppService;

    /**
     * 记录申请日志
     * @param logDto
     */
    private void sendRegisterMessage(LogDto logDto){
        Product product= repository.findOne(logDto.getProductId());
        if(product!=null) {
            MessageDto messageDto = new MessageDto();
            messageDto.setUserId(CPContext.getContext().getSeUserInfo().getId());
            messageDto.setAppName(logDto.getAppName());
            messageDto.setTitle("借款申请已提交，待审核");
            messageDto.setContent("尊敬的用户，您好，你的" + product.getName() + "的借款申请已提交，请等待审核\n" +
                    "我们会及时为你审核，审核通过后及时为您发放贷款\n");
            messageAppService.create(messageDto);
        }
    }
    /**
     * 查询数据更新版本
     * @return
     */
    public ProductVersion findProductVersion(String channelNo){
        ProductVersion productVersion = productVersionRepository.findByChannelNo(channelNo);
        if(productVersion==null)
        {
            return null;
        }
        return productVersion;
    }


    /**
     * 设置产品推荐产品ID
     * @param productId
     * @return
     */
    public List<ProductRecommendListDto> findRecommendProductByProductId(Long productId, String versionNo, String channelNo){
        return productDao.findProductRecommendByProductId(productId,versionNo,channelNo);
    }


    /**
     * 查询推荐产品
     * @return
     */
    public Page<Product> findRecommendProduct(ProductSearchDto productSearchDto) {
        setProductSort(productSearchDto);
        Specification<Product> spec = new Specification<Product>() {
            @Override
            public Predicate toPredicate(Root<Product> root,
                                         CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<Predicate>();
                list.add(cb.equal(root.get("isRecommend"),"1"));//1:推荐，2：热门，3:新口子
                list.add(cb.like(root.get("belongApp"),"%"+productSearchDto.getAppName()+"%"));
                list.add(cb.equal(root.get("isValid"),Boolean.TRUE));

                list.add(cb.or(cb.and(cb.lessThanOrEqualTo(root.get("limitUserTop"), root.get("todayApplyUser"))
                        , cb.or(cb.equal(root.get("isHidden"), 2),cb.equal(root.get("isHidden"), 0))),
                        cb.lessThanOrEqualTo(root.get("todayApplyUser"), root.get("limitUserTop"))
                ));
                if(productSearchDto.getChannelNo()!=null)
                {
                    if(!productSearchDto.getChannelNo().toUpperCase().contains("IOS".toUpperCase())) {
                        Boolean isAudit = productDao.findAuditChannel(productSearchDto.getChannelNo(),productSearchDto.getVersionNo());
                        if(isAudit) {
                            //读取配置Vivo配置
                            if(!Objects.isNull(productSearchDto.getVersionNo())) {
                                //查询审核数据状态
                                isAudit = checkVersionShowData(productSearchDto.getVersionNo());
                                if (isAudit) {
                                    list.add(cb.equal(root.get("androidFlag"), Boolean.TRUE));
                                } else {
                                    list.add(cb.equal(root.get("androidFlag"), Boolean.FALSE));
                                    list.add(cb.equal(root.get("appleFlag"), Boolean.FALSE));
                                }
                            }else{//之前的版本处理
                                list.add(cb.equal(root.get("androidFlag"), Boolean.TRUE));
                            }
                        }else{
                            list.add(cb.equal(root.get("androidFlag"), Boolean.TRUE));
                        }
                    }else{
                        Boolean versionAuditFlag = checkVersionShowData(productSearchDto.getChannelNo());
                        if (versionAuditFlag) {
                            list.add(cb.equal(root.get("appleFlag"), Boolean.TRUE));
                        } else {
                            list.add(cb.equal(root.get("appleFlag"), Boolean.FALSE));
                            list.add(cb.equal(root.get("androidFlag"), Boolean.FALSE));
                        }
                    }
                }

                Predicate[] predicates = new Predicate[list.size()];
                return cb.and(list.toArray(predicates));
            }
        };
        return repository.findAll(spec,productSearchDto.toPage());
    }
}

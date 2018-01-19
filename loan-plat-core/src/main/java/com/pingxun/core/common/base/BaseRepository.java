package com.pingxun.core.common.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
* @Title: BaseRepository.java
* @Description: 基础数据操作库
* @author Away
* @date 2018/1/9 15:54
* @copyright 重庆平讯数据
* @version V1.0
*/
@NoRepositoryBean
@Transactional(readOnly=true)
public interface BaseRepository<T,ID extends Serializable> extends JpaRepository<T,ID> {

    /**
     *
     * @Title: findByHqlPage
     * @Description: 分页查询
     * @author dengyy
     * @param pageable 分页信息
     * @param hql 查询hql
     * @param condition 查询条件
     * @return
     */
    public Page<T> findByHqlPage(Pageable pageable, String hql, Map<String, Object> condition);

    /**
     *
     * @Title: findByHql
     * @Description: 通过HQL查找
     * @author dengyy
     * @param hql  HQL语句
     * @param condition 查询条件
     * @return
     */
    public List<T> findByHql(String hql, Map<String, Object> condition);

    /**
     * 多条件sql分页查询
     * @param pageable 分页参数
     * @param sql sql语句
     * @param condition 查询条件
     * @return
     */
    public Page<T> findBySqlEntityPage(Pageable pageable, String sql, Map<String, Object> condition, Class<T> tClass) throws Exception;

    /**
     * 多条件sql查询
     * @param sql
     * @param condition
     * @param tClass
     * @return
     * @throws Exception
     */
    public List<T> findBySql(String sql,Map<String,Object> condition,Class<T> tClass) throws Exception;

    /**
     * 获取当前查询条件下总条数
     *
     * @param hql
     *            查询HQL
     * @param condition
     *            查询条件
     * @return
     */
    public Long countAll(String hql, Map<String, Object> condition);

    /**
     * 获取当前查询条件下总条数
     *
     * @param sql
     *            查询SQL
     * @param condition
     *            查询条件
     * @return
     */
    public Long countAllSql(String sql, Map<String, Object> condition);

    /**
     * 保存域对象
     *
     * @param t
     *            域对象
     * @return
     */
    public T saveEntity(T t);

    /**
     * 更新实体
     *
     * @param t
     *            需更新的实体
     * @return
     */
    public T updateEntity(T t);

    /**
     * 逻辑删除
     *
     * @param t
     *            需要删除的域对象
     * @return
     */
    public T logicDelete(T t);

    /**
     * 根据条件查询List<Map<>>
     * @param sql
     * @param condition
     * @return
     * @throws Exception
     */
    public List<Map<String,Object>> findListMapByCondition(String sql, Map<String, Object> condition) throws Exception;

    /**
     * 批量保存
     * @param list
     * @return
     */
    public List<T> batchSave(List<T> list);

    /**
     * 批量更新
     * @param list
     * @return
     */
    public List<T> batchUpdate(List<T> list);


    /**
     * 批量逻辑删除
     * @param list
     * @return
     */
    public List<T> batchLogicDelete(List<T> list);
}

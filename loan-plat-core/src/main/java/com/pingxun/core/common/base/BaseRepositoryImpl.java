package com.pingxun.core.common.base;

import com.pingxun.core.common.util.ObjectHelper;
import org.apache.commons.beanutils.ConvertUtils;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by konghao on 2016/12/7.
 */
public class BaseRepositoryImpl<T , ID extends Serializable> extends SimpleJpaRepository<T,ID>
        implements BaseRepository<T,ID> {

	static Logger logger = LoggerFactory.getLogger(BaseRepositoryImpl.class);
	private final EntityManager entityManager;

	public BaseRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
		super(entityInformation, entityManager);
		this.entityManager = entityManager;
	}

	public BaseRepositoryImpl(Class<T> domainClass, EntityManager em) {
		this(JpaEntityInformationSupport.getEntityInformation(domainClass, em), em);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findByHql(String hql, Map<String, Object> condition) {
		// 获得query对象
		Query q = entityManager.createQuery(hql);
		// 将查询条件注入HQL语句中
		for (Serializable key : condition.keySet()) {
			q.setParameter(key.toString(), condition.get(key));
		}
		// 执行查询
		List<T> sourceData = (List<T>) q.getResultList();
		return sourceData;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Page<T> findByHqlPage(Pageable pageable, String hql, Map<String, Object> condition) {
		if (pageable == null) {
			throw new IllegalArgumentException("pageable 不能为空!");
		}
		// 创建query
		Query q = entityManager.createQuery(hql);
		// 设置查询条件
		for (Serializable key : condition.keySet()) {
			q.setParameter(key.toString(), condition.get(key));
		}
		//分页参数
		Integer pageSize = pageable.getPageSize();
		Integer pageNo = pageable.getPageNumber();
		q.setFirstResult(pageSize * pageNo);
		q.setMaxResults(pageable.getPageSize());
		// 申明分页数据
		Page<T> pager = new PageImpl<T>(q.getResultList(),pageable,this.countAll(hql, condition));
		return pager;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Page<T> findBySqlEntityPage(Pageable pageable, String sql, Map<String, Object> condition, Class<T> tClass)
			throws Exception {
		if (pageable == null) {
			throw new IllegalArgumentException("pageable 不能为空!");
		}
		Session session = entityManager.unwrap(Session.class);
		SQLQuery query = session.createSQLQuery(sql);
		// 设置查询条件
		for (Serializable key : condition.keySet()) {
			query.setParameter(key.toString(), condition.get(key));
		}
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		// 设置分页条件
		Integer pageSize = pageable.getPageSize();
		Integer pageNo = pageable.getPageNumber();
		query.setFirstResult(pageSize * pageNo);
		query.setMaxResults(pageable.getPageSize());
		// 查询结果
		List<Map<String, Object>> result = query.list();
		List returnListData = new ArrayList();
		if (tClass != null) {
			List<Object> entityList = convert(tClass, result);
			returnListData = entityList;
		}
		// 申明分页数据
		Page<T> pager = new PageImpl<T>(returnListData,pageable,this.countAllSql(sql, condition));
		return pager;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<T> findBySql(String sql, Map<String, Object> condition, Class<T> tClass) throws Exception {
		Session session = entityManager.unwrap(Session.class);
		SQLQuery query = session.createSQLQuery(sql);
		// 设置查询条件
		for (Serializable key : condition.keySet()) {
			query.setParameter(key.toString(), condition.get(key));
		}
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		// 查询结果
		List<Map<String, Object>> result = query.list();
		List returnListData = new ArrayList();
		if (tClass != null) {
			List<Object> entityList = convert(tClass, result);
			returnListData = entityList;
		}
		return returnListData;
	}

	@Override
	public Long countAll(String hql, Map<String, Object> condition) {
		if (hql == null) {
			return 0l;
		}
		String tmpHql = hql.toLowerCase();
		hql = "select count(*) " + hql.substring(tmpHql.indexOf("from"));
		logger.debug("count(*) hql ---->" + hql);
		// 创建query
		Query q = entityManager.createQuery(hql);
		// 设置查询条件
		for (Serializable key : condition.keySet()) {
			q.setParameter(key.toString(), condition.get(key));
		}
		Long result = (Long) q.getResultList().get(0);
		return result;
	}

	@Override
	public Long countAllSql(String sql, Map<String, Object> condition) {
		if (sql == null) {
			return 0l;
		}
		String tmpHql = sql.toLowerCase();
		sql = "select count(*) " + sql.substring(tmpHql.indexOf("from"));
		logger.debug("count(*) sql ---->" + sql);
		// 创建query
		Query q = entityManager.createNativeQuery(sql);
		// 设置查询条件
		for (Serializable key : condition.keySet()) {
			q.setParameter(key.toString(), condition.get(key));
		}
		String resultStr = q.getResultList().get(0).toString();
		return Long.valueOf(resultStr);
	}

	@Override
	@Transactional
	public T saveEntity(T t) {
		return this.saveAndFlush(t);
	}

	@Override
	@Transactional
	public T updateEntity(T t) {
		return this.saveAndFlush(t);
	}

	@Override
	public T logicDelete(T t) {
		return this.entityManager.merge(t);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<Map<String, Object>> findListMapByCondition(String sql, Map<String, Object> condition)
			throws Exception {
		// 得到Session
		Session session = entityManager.unwrap(Session.class);
		// 创建查询对象
		SQLQuery query = session.createSQLQuery(sql);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		// 设置查询条件
		if (ObjectHelper.isNotEmpty(condition) && condition.size() > 0) {
			for (Serializable key : condition.keySet()) {
				query.setParameter(key.toString(), condition.get(key));
			}
		}
		List<Map<String, Object>> sourceData = query.list();
		return sourceData;
	}

	@Override
	@Transactional
	public List<T> batchSave(List<T> list) {
		for (int i = 0; i < list.size(); i++) {
			entityManager.persist(list.get(i));
			entityManager.flush();
			entityManager.clear();
		}
		return list;
	}

	@Override
	@Transactional
	public List<T> batchUpdate(List<T> list) {
		for (int i = 0; i < list.size(); i++) {
			entityManager.merge(list.get(i));
			entityManager.flush();
			entityManager.clear();
		}
		return list;
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public List<T> batchLogicDelete(List<T> list) {
		for (int i = 0; i < list.size(); i++) {
			entityManager.merge(list.get(i));
			entityManager.flush();
			entityManager.clear();
		}
		return list;
	}

	/**
	 * 将list<Map<>>转换为实体对象集合
	 *
	 * @param clazz
	 * @param list
	 * @return
	 */
	private List<Object> convert(Class<?> clazz, List<Map<String, Object>> list) {
		List<Object> result;
		if (ObjectHelper.isEmpty(list)) {
			return new ArrayList<Object>();
		}
		result = new ArrayList<Object>();
		try {
			PropertyDescriptor[] props = Introspector.getBeanInfo(clazz).getPropertyDescriptors();
			for (Map<String, Object> map : list) {
				Object obj = clazz.newInstance();
				for (String key : map.keySet()) {
					String attrName = key;
					for (PropertyDescriptor prop : props) {
						attrName = removeUnderLine(attrName);
						if (!attrName.toUpperCase().equals(prop.getName().toUpperCase())) {
							continue;
						}
						Method method = prop.getWriteMethod();
						Object value = map.get(key);
						if (value != null) {
							value = ConvertUtils.convert(value, prop.getPropertyType());
						}
						method.invoke(obj, value);
					}
				}
				result.add(obj);
			}
		} catch (Exception e) {
			throw new RuntimeException("数据转换错误");
		}
		return result;
	}

	/**
	 * 去掉下划线
	 *
	 * @param attrName
	 * @return
	 */
	private String removeUnderLine(String attrName) {
		// 去掉数据库字段的下划线
		if (attrName.contains("_")) {
			String[] names = attrName.split("_");
			String firstPart = names[0];
			String otherPart = "";
			for (int i = 1; i < names.length; i++) {
				String word = names[i].replaceFirst(names[i].substring(0, 1), names[i].substring(0, 1).toUpperCase());
				otherPart += word;
			}
			attrName = firstPart + otherPart;
		}
		return attrName;
	}

}

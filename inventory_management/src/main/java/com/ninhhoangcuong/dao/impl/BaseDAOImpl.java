package com.ninhhoangcuong.dao.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.ninhhoangcuong.dao.BaseDAO;
import com.ninhhoangcuong.model.Paging;

@Repository
@Transactional(rollbackFor = Exception.class)
public class BaseDAOImpl<E> implements BaseDAO<E> {
	final static Logger LOGGER = Logger.getLogger(BaseDAOImpl.class);
	@Autowired
	SessionFactory sessionFactory;

	@Override
	public List<E> findAll(String queryString, Map<String, Object> mapParams, Paging paging) {
		LOGGER.info("get all record from db");
		StringBuilder query = new StringBuilder("");
		StringBuilder countQuery = new StringBuilder();
		// count record from db
		countQuery.append(" select count(*) from ").append(getGenericName())
				.append(" as model where model.activeFlag=1");
		// select recod from db
		query.append(" from ").append(getGenericName()).append(" as model where model.activeFlag=1");
		if (queryString != null && !StringUtils.isEmpty(queryString)) {
			query.append(queryString);
			countQuery.append(queryString);
		}
		Query<E> queryStament = sessionFactory.getCurrentSession().createQuery(query.toString());
		Query<E> countQueryStament = sessionFactory.getCurrentSession().createQuery(countQuery.toString());
		// set parameter for query
		if (mapParams != null && !mapParams.isEmpty()) {
			mapParams.forEach((k, v) -> {
				queryStament.setParameter(k, v);
				countQueryStament.setParameter(k, v);
			});
		}
		if (paging != null) {
			// set ban ghi dau tien
			queryStament.setFirstResult(paging.getOffset());
			// so luong ban ghi moi lan lay
			queryStament.setMaxResults(paging.getRecordPerPage());
			paging.setTotalRows((long) countQueryStament.uniqueResult());
		}
		LOGGER.info("Query find All " + query.toString());
		return queryStament.list();
	}

	@Override
	public E findById(Class<E> e, Serializable id) {
		LOGGER.info("find by ID");
		return sessionFactory.getCurrentSession().get(e, id);
	}

	@Override
	public List<E> findByProperty(String property, Object value) {
		LOGGER.info("find by property");
		LOGGER.info("Find by property");
		StringBuilder queryString = new StringBuilder();
		queryString.append(" from ").append(getGenericName()).append(" as model where model.activeFlag=1 and model.")
				.append(property).append(" = :input");
		LOGGER.info(" query find by property ===>" + queryString.toString());
		return sessionFactory.getCurrentSession().createQuery(queryString.toString()).setParameter("input", value)
				.getResultList();
	}

	@Override
	public void save(E instance) {
		LOGGER.info("save");
		sessionFactory.getCurrentSession().save(instance);
	}

	@Override
	public void update(E instance) {
		LOGGER.info("update");
		sessionFactory.getCurrentSession().merge(instance);
	}

	public String getGenericName() {
		String s = getClass().getGenericSuperclass().toString();
		Pattern pattern = Pattern.compile("\\<(.*?)\\>");
		Matcher matcher = pattern.matcher(s);
		return matcher.find() ? matcher.group(1) : null;
//		String s = getClass().getGenericSuperclass().toString();
//		Pattern pattern = Pattern.compile("\\<(.*?)\\>");
//		Matcher m = pattern.matcher(s);
//		String generic="null";
//		if(m.find()) {
//			generic = m.group(1);
//		}
//		return generic;
	}
}

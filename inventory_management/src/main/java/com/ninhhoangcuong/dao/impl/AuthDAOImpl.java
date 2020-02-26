package com.ninhhoangcuong.dao.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.ninhhoangcuong.dao.AuthDAO;
import com.ninhhoangcuong.model.Auth;

@Repository
@Transactional(rollbackOn = Exception.class)
public class AuthDAOImpl extends BaseDAOImpl<Auth> implements AuthDAO<Auth> {

	@Override
	public Auth find(int roleId, int menuId) {
		LOGGER.info("find auth form db");
		String hql = " from Auth as model where model.role.id=:roleId and model.menu.id=:menuId";
		Query<Auth> query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("roleId", roleId);
		query.setParameter("menuId", menuId);
		List<Auth> auths = query.getResultList();
		if (!CollectionUtils.isEmpty(auths)) {
			return auths.get(0);
		}
		return null;
	}

}

package com.ninhhoangcuong.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ninhhoangcuong.dao.RoleDAO;
import com.ninhhoangcuong.model.Paging;
import com.ninhhoangcuong.model.Role;

@Service
public class RoleService {
	private static final Logger log = Logger.getLogger(RoleService.class);
	@Autowired
	private RoleDAO<Role> roleDAO;

	public List<Role> getList(Role role, Paging paging) {
		log.info("Get List role !");
		StringBuilder queryString = new StringBuilder();
		Map<String, Object> mapParams = new HashMap<String, Object>();
		return roleDAO.findAll(queryString.toString(), mapParams, paging);
	}

	public void saveRole(Role role) throws Exception {
		log.info("Insert role " + role.toString());
		role.setActiveFlag(1);
		role.setCreateDate(new Date());
		role.setUpdateDate(new Date());
		roleDAO.save(role);
	}

	public void updateRole(Role role) throws Exception {
		log.info("Update role " + role.toString());
		role.setUpdateDate(new Date());
		roleDAO.update(role);
	}

	public void deleteRole(Role role) throws Exception {
		log.info("Delete role " + role.toString());
		role.setActiveFlag(0);
		role.setUpdateDate(new Date());
		roleDAO.update(role);
	}

	public List<Role> findByProperties(String property, Object value) {
		log.info("find by properties " + property + " value " + value.toString());
		return roleDAO.findByProperty(property, value);
	}

	public Role findById(int id) {
		log.info("find Role by id : " + id);
		return roleDAO.findById(Role.class, id);
	}
}

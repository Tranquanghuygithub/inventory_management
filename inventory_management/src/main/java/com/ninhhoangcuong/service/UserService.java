package com.ninhhoangcuong.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ninhhoangcuong.dao.RoleDAO;
import com.ninhhoangcuong.dao.UserDAO;
import com.ninhhoangcuong.dao.UserRoleDAO;
import com.ninhhoangcuong.model.Paging;
import com.ninhhoangcuong.model.Role;
import com.ninhhoangcuong.model.UserRole;
import com.ninhhoangcuong.model.Users;
import com.ninhhoangcuong.utils.HashingPassword;

@Service
public class UserService {
	final static Logger log = Logger.getLogger(UserService.class);
	@Autowired
	private UserDAO<Users> userDAO;
	@Autowired
	private UserRoleDAO<UserRole> userRoleDAO;
	@Autowired
	private RoleDAO<Role> roleDAO;

	public List<Users> findByProperties(String property, Object value) {
		log.info("find user by property");
		return userDAO.findByProperty(property, value);
	}

	public Users findById(Integer id) {
		log.info("find user by id : " + id);
		return userDAO.findById(Users.class, id);
	}

	public void save(Users users) throws Exception {
		users.setActiveFlag(1);
		users.setCreateDate(new Date());
		users.setUpdateDate(new Date());
		users.setPassword(HashingPassword.encrypt(users.getPassword()));
		userDAO.save(users);
		UserRole userRole = new UserRole();
		userRole.setUsers(users);
		Role role = new Role();
		role.setId(users.getRoleID());
		userRole.setRole(role);
		userRole.setCreateDate(new Date());
		userRole.setUpdateDate(new Date());
		userRole.setActiveFlag(1);
		userRoleDAO.save(userRole);
	}

	public void update(Users users) throws Exception {
		log.info("update user : " + users.toString());
		Users user = findById(users.getId());
		if (user != null) {
			UserRole userRole = (UserRole) user.getUserRoles().iterator().next();
			Role role = userRole.getRole();
			// set new role
			role.setId(users.getRoleID());
			userRole.setRole(role);
			userRole.setUpdateDate(new Date());
			user.setName(users.getName());
			user.setEmail(users.getEmail());
			user.setUserName(users.getUserName());
			user.setUpdateDate(new Date());
			userRoleDAO.update(userRole);
		}
		userDAO.update(user);
	}

	public void deleteUser(Users users) throws Exception {
		users.setActiveFlag(0);
		users.setUpdateDate(new Date());
		userDAO.update(users);
	}

	public List<Users> getList(Users users, Paging paging) {
		StringBuilder queryString = new StringBuilder();
		Map<String, Object> mapParams = new HashMap<String, Object>();
		if (users != null) {
			if (!StringUtils.isEmpty(users.getName())) {
				queryString.append(" and model.name like :name ");
				mapParams.put("name", "%" + users.getName() + "%");
			}
			if (!StringUtils.isEmpty(users.getUserName())) {
				queryString.append(" and model.userName like :userName ");
				mapParams.put("userName", "%" + users.getUserName() + "%");
			}
		}
		return userDAO.findAll(queryString.toString(), mapParams, paging);
	}
}

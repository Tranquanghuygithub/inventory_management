package com.ninhhoangcuong.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ninhhoangcuong.dao.AuthDAO;
import com.ninhhoangcuong.dao.MenuDAO;
import com.ninhhoangcuong.model.Auth;
import com.ninhhoangcuong.model.Menu;
import com.ninhhoangcuong.model.Paging;
import com.ninhhoangcuong.model.Role;

@Service
public class MenuService {
	@Autowired
	private MenuDAO<Menu> menuDAO;
	@Autowired
	private AuthDAO<Auth> authDAO;
	static final Logger log = Logger.getLogger(MenuService.class);

	public List<Menu> getList(Menu menu, Paging paging) {
		log.info("get All Menu");
		StringBuilder queryString = new StringBuilder();
		queryString.append(" or model.activeFlag=0");
		Map<String, Object> mapParams = new HashMap<String, Object>();
		if (menu != null) {
			if (!StringUtils.isEmpty(menu.getUrl())) {
				queryString.append(" and model.url like :url");
				mapParams.put("url", "%" + menu.getUrl() + "%");
			}
		}
		return menuDAO.findAll(queryString.toString(), mapParams, paging);
	}

	public void changeStatus(Integer id) throws Exception {
		Menu menu = menuDAO.findById(Menu.class, id);
		if (menu != null) {
			menu.setActiveFlag(menu.getActiveFlag() == 1 ? 0 : 1);
			menuDAO.update(menu);
			return;
		}
	}

	public void updatePermission(int roleId, int menuId, int permission) throws Exception {
		Auth auth = authDAO.find(roleId, menuId);
		// kiem tra xem cap day co trong db chua neu co roi thi update ko them moi
		if (auth != null) {
			log.info("Update auth ");
			auth.setPermission(permission);
			authDAO.update(auth);
		} else {
			if (permission == 1) {
				log.info("Insert auth");
				Role role = new Role();
				Menu menu = new Menu();
				auth = new Auth();
				auth.setActiveFlag(1);
				role.setId(roleId);
				menu.setId(menuId);
				auth.setRole(role);
				auth.setMenu(menu);
				auth.setPermission(1);
				auth.setCreateDate(new Date());
				auth.setUpdateDate(new Date());
				authDAO.save(auth);
			}
		}
	}
}

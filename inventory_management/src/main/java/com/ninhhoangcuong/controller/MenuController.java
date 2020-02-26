package com.ninhhoangcuong.controller;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ninhhoangcuong.model.Auth;
import com.ninhhoangcuong.model.AuthForm;
import com.ninhhoangcuong.model.Menu;
import com.ninhhoangcuong.model.Paging;
import com.ninhhoangcuong.model.Role;
import com.ninhhoangcuong.service.MenuService;
import com.ninhhoangcuong.service.RoleService;
import com.ninhhoangcuong.utils.Constant;

@Controller
public class MenuController {
	@Autowired
	private MenuService menuService;
	@Autowired
	private RoleService roleService;
	final static Logger log = Logger.getLogger(MenuController.class);

	@RequestMapping(value = { "/menu/list", "/menu/list/" })
	public String redirectToList() {
		return "redirect:/menu/list/1";
	}

	@RequestMapping("/menu/list/{page}")
	public String show(Model model, @ModelAttribute("searchForm") Menu menu, @PathVariable("page") int page,
			HttpSession session) {
		Paging paging = new Paging(15);
		paging.setIndexPage(page);
		List<Menu> menus = menuService.getList(menu, paging);
		List<Role> roles = roleService.getList(null, null);
		// sort list
		roles.sort(Comparator.comparingInt(Role::getId));
		menus.forEach(m -> {
			Map<Integer, Integer> mapAuth = new TreeMap<Integer, Integer>();
			roles.forEach(r -> {
				mapAuth.put(r.getId(), 0);
			});
			m.getAuths().forEach(a -> {
				Auth auth = (Auth) a;
				mapAuth.put(auth.getRole().getId(), auth.getPermission());
			});
			m.setMapAuth(mapAuth);
		});
		if (session.getAttribute(Constant.MSG_SUCCESS) != null) {
			model.addAttribute(Constant.MSG_SUCCESS, session.getAttribute(Constant.MSG_SUCCESS));
			session.removeAttribute(Constant.MSG_SUCCESS);
		}
		if (session.getAttribute(Constant.MSG_ERROR) != null) {
			model.addAttribute(Constant.MSG_ERROR, session.getAttribute(Constant.MSG_ERROR));
			session.removeAttribute(Constant.MSG_ERROR);
		}
		model.addAttribute("menuList", menus);
		model.addAttribute("roles", roles);
		model.addAttribute("pageInfo", paging);
		return "menu-list";
	}

	@GetMapping("/menu/change-status/{id}")
	public String change(Model model, @PathVariable("id") int id, HttpSession session) {
		try {
			menuService.changeStatus(id);
			session.setAttribute(Constant.MSG_SUCCESS, "Change Status Success !!!");
		} catch (Exception e) {
			e.printStackTrace();
			log.info(e.getMessage());
			session.setAttribute(Constant.MSG_ERROR, "Change Status False !!!");
		}
		return "redirect:/menu/list";
	}

	@GetMapping("menu/permission")
	public String permission(Model model) {
		model.addAttribute("modelForm", new AuthForm());
		initSelectBox(model);
		return "menu-permission";
	}

	@PostMapping("/menu/update-permission")
	public String update(Model model, @ModelAttribute("modelForm") AuthForm authForm, HttpSession session) {
		try {
			menuService.updatePermission(authForm.getRoleId(), authForm.getMenuId(), authForm.getPermission());
			session.setAttribute(Constant.MSG_SUCCESS, "Update permission Success !!!");
		} catch (Exception e) {
			e.printStackTrace();
			session.setAttribute(Constant.MSG_ERROR, "Update permission Error !!!");
			log.info("Here is error !!!!!!!!!!!!!");
		}
		return "redirect:/menu/list";
	}

	private void initSelectBox(Model model) {
		Map<Integer, String> mapMenus = menuService.getList(null, null).stream()
				.collect(Collectors.toMap(Menu::getId, Menu::getUrl, (oldValue, newValue) -> newValue));
		Map<Integer, String> mapRoles = roleService.getList(null, null).stream()
				.collect(Collectors.toMap(Role::getId, Role::getRoleName, (oldValue, newValue) -> newValue));
		model.addAttribute("mapRoles", mapRoles);
		model.addAttribute("mapMenus", mapMenus);
	}
}

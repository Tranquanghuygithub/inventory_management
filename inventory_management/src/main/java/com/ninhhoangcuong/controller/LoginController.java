package com.ninhhoangcuong.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.ninhhoangcuong.model.Auth;
import com.ninhhoangcuong.model.Menu;
import com.ninhhoangcuong.model.Role;
import com.ninhhoangcuong.model.UserRole;
import com.ninhhoangcuong.model.Users;
import com.ninhhoangcuong.service.UserService;
import com.ninhhoangcuong.utils.Constant;
import com.ninhhoangcuong.validate.LoginValidator;

@Controller
public class LoginController {
	@Autowired
	private UserService userService;
	@Autowired
	private LoginValidator loginValidator;

	@InitBinder
	private void initBinder(WebDataBinder binder) {
		if (binder.getTarget() == null) {
			return;
		}
		if (binder.getTarget().getClass() == Users.class) {
			binder.setValidator(loginValidator);
		}
	}

	@GetMapping("/login")
	public String login(Model model) {
		model.addAttribute("loginForm", new Users());
		return "login/login";
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/processLogin")
	public String processLogin(Model model, @ModelAttribute("loginForm") @Validated Users user, BindingResult result,
			HttpSession session) {
		if (result.hasErrors()) {
			return "login/login";
		}
		Users users = userService.findByProperties("userName", user.getUserName()).get(0);
		UserRole userRole = (UserRole) users.getUserRoles().iterator().next();
		Role role = userRole.getRole();
		//danh sach menu cha
		List<Menu> parrentMenuList = new ArrayList<Menu>();
		//danh sach menu con
		List<Menu> childMenuList = new ArrayList<Menu>();
		role.getAuths().forEach(o1 -> {
			Auth auth = (Auth) o1;
			Menu menu = auth.getMenu();
			//lay tat ca cac menu con va menu cha dc phep su dung
			if (menu.getParentId() == 0 && menu.getOrderIndex() != -1 && menu.getActiveFlag() == 1
					&& auth.getPermission() == 1 && auth.getActiveFlag() == 1) {
				menu.setIdMenu(menu.getUrl().replace("/", "")+"Id");
				parrentMenuList.add(menu);
			}else if (menu.getParentId() !=0 && menu.getOrderIndex() != -1 && menu.getActiveFlag() == 1
					&& auth.getPermission() == 1 && auth.getActiveFlag() == 1) {
					menu.setIdMenu(menu.getUrl().replace("/", "")+"Id");
					childMenuList.add(menu);
			}
		});
		parrentMenuList.forEach(o1->{
			//danh sach cac menu con
			List<Menu> childMenus = new ArrayList<Menu>();
			childMenuList.forEach(o2 -> {
				//so sanh neu menu cha == parrendId thi la menu con
				if(o2.getParentId() == o1.getId()) {
					childMenus.add(o2);
				}
			});
			o1.setChild(childMenus);
		});
		//sap xep lai cac menu cha
		parrentMenuList.sort(Comparator.comparingInt(Menu::getOrderIndex));
		//sap xep lai cac menu con
		parrentMenuList.forEach(o1->{
			o1.getChild().sort(Comparator.comparingInt(Menu::getOrderIndex));
		});
		session.setAttribute(Constant.MENU_SESSION, parrentMenuList);
		session.setAttribute(Constant.USER_INFO, users);
		
		return "redirect:/index";
	}
}

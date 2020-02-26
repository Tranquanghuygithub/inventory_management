package com.ninhhoangcuong.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ninhhoangcuong.model.Paging;
import com.ninhhoangcuong.model.Role;
import com.ninhhoangcuong.model.UserRole;
import com.ninhhoangcuong.model.Users;
import com.ninhhoangcuong.service.RoleService;
import com.ninhhoangcuong.service.UserService;
import com.ninhhoangcuong.utils.Constant;
import com.ninhhoangcuong.validate.UserValidator;

@Controller
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private UserValidator userValidator;
	static final Logger log = Logger.getLogger(UserController.class);

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		if (binder.getTarget() == null) {
			return;
		}
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(simpleDateFormat, true));
		if (binder.getTarget().getClass() == Users.class) {
			binder.setValidator(userValidator);
		}
	}

	@RequestMapping(value = { "/user/list", "/user/list/" })
	public String redirectShowList() {
		return "redirect:/user/list/1";
	}

	@RequestMapping(value = "/user/list/{page}")
	public String showCategoryList(Model model, HttpSession session, @ModelAttribute("searchForm") Users user,
			@PathVariable("page") int page) {
		// current page
		Paging paging = new Paging(3);
		paging.setIndexPage(page);
		List<Users> users = userService.getList(user, paging);
		if (session.getAttribute(Constant.MSG_SUCCESS) != null) {
			model.addAttribute(Constant.MSG_SUCCESS, session.getAttribute(Constant.MSG_SUCCESS));
			session.removeAttribute(Constant.MSG_SUCCESS);
		}
		if (session.getAttribute(Constant.MSG_ERROR) != null) {
			model.addAttribute(Constant.MSG_ERROR, session.getAttribute(Constant.MSG_ERROR));
			session.removeAttribute(Constant.MSG_ERROR);
		}
		model.addAttribute("pageInfo", paging);
		model.addAttribute("users", users);
		return "user-list";
	}

	@GetMapping("/user/add")
	public String add(Model model) {
		log.info("add user !");
		Map<String, String> mapParams = roleService.getList(null, null).stream()
				.collect(Collectors.toMap(r -> r.getId().toString(), Role::getRoleName, (o, n) -> n));
		model.addAttribute("mapRole", mapParams);
		model.addAttribute("titlePage", "Add user");
		model.addAttribute("modelForm", new Users());
		model.addAttribute("viewOnly", false);
		return "user-action";
	}

	@GetMapping("/user/edit/{id}")
	public String edit(Model model, @PathVariable("id") int id) {
		log.info("edit user with id = " + id);
		List<Users> users = userService.findByProperties("id", id);
		if (users != null && !users.isEmpty()) {
			Users user = users.get(0);
			Map<String, String> mapParams = roleService.getList(null, null).stream()
					.collect(Collectors.toMap(r -> r.getId().toString(), Role::getRoleName, (o, n) -> n));
			UserRole userRole = (UserRole) user.getUserRoles().iterator().next();
			user.setRoleID(userRole.getRole().getId());
			model.addAttribute("mapRole", mapParams);
			model.addAttribute("titlePage", "Edit user");
			model.addAttribute("modelForm", user);
			model.addAttribute("viewOnly", false);
			model.addAttribute("editMode", true);
			return "user-action";
		}
		return "redirect:/user/list";
	}

	@GetMapping("/user/view/{id}")
	public String view(Model model, @PathVariable("id") int id) {
		log.info("view user with id = " + id);
		List<Users> users = userService.findByProperties("id", id);
		if (users != null && !users.isEmpty()) {
			Users user = users.get(0);
			Map<String, String> mapParams = roleService.getList(null, null).stream()
					.collect(Collectors.toMap(r -> r.getId().toString(), Role::getRoleName, (o, n) -> n));
			UserRole userRole = (UserRole) user.getUserRoles().iterator().next();
			user.setRoleID(userRole.getRole().getId());
			user.setRoleName(userRole.getRole().getRoleName());
			model.addAttribute("mapRole", mapParams);
			model.addAttribute("titlePage", "View user");
			model.addAttribute("modelForm", user);
			model.addAttribute("viewOnly", true);
			model.addAttribute("editModel", true);
			return "user-action";
		}
		return "redirect:/user/list";
	}

	@PostMapping("/user/save")
	public String save(Model model, @ModelAttribute("modelForm") @Validated Users user, BindingResult result,
			HttpSession session) {
		if (result.hasErrors()) {
			if (user.getId() != null) {
				model.addAttribute("titlePage", "Edit user");
				model.addAttribute("editMode", true);
			} else {
				model.addAttribute("titlePage", "Add user");
			}
			Map<String, String> mapParams = roleService.getList(null, null).stream()
					.collect(Collectors.toMap(r -> r.getId().toString(), Role::getRoleName, (o, n) -> n));
			model.addAttribute("mapRole", mapParams);
			model.addAttribute("modelForm", user);
			model.addAttribute("viewOnly", false);
			return "user-action";
		}
		// neu category ton tai Id => chuc nang la update
		if (user.getId() != null && user.getId() != 0) {
			try {
				userService.update(user);
				session.setAttribute(Constant.MSG_SUCCESS, "Update success !!!");
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e.getMessage());
				session.setAttribute(Constant.MSG_ERROR, "Update has Error !!!");
			}
		} else {
			// khong co id => chuc nag insert
			log.info("inser user");
			try {
				userService.save(user);
				session.setAttribute(Constant.MSG_SUCCESS, "Insert Success !!!");
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e.getMessage());
				session.setAttribute(Constant.MSG_ERROR, "Insert has Error !!!");
			}
		}
		return "redirect:/user/list";
	}

	@GetMapping("/user/delete/{id}")
	public String delete(Model model, @PathVariable("id") int id, HttpSession session) {
		log.info("Delete user id : " + id);
		Users user = userService.findById(id);
		if (user != null) {
			try {
				userService.deleteUser(user);
				// thong bao
				session.setAttribute(Constant.MSG_SUCCESS, "Delete Success !!!");
			} catch (Exception e) {
				e.printStackTrace();
				// thong bao
				session.setAttribute(Constant.MSG_ERROR, "Delete has Error !!!");
			}
		}
		return "redirect:/user/list";
	}
}

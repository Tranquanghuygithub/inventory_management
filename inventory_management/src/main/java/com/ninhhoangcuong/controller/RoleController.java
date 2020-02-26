package com.ninhhoangcuong.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
import com.ninhhoangcuong.service.RoleService;
import com.ninhhoangcuong.utils.Constant;
import com.ninhhoangcuong.validate.RoleValidator;

@Controller
public class RoleController {
	@Autowired
	private RoleService roleService;
	@Autowired
	private RoleValidator roleValidator;
	static final Logger log = Logger.getLogger(RoleController.class);

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		if (binder.getTarget() == null) {
			return;
		}
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(simpleDateFormat, true));
		if (binder.getTarget().getClass() == Role.class) {
			binder.setValidator(roleValidator);
		}
	}

	@RequestMapping(value = { "/role/list", "/role/list/" })
	public String redirectShowList() {
		return "redirect:/role/list/1";
	}

	@RequestMapping(value = "/role/list/{page}")
	public String showCategoryList(Model model, HttpSession session, @ModelAttribute("searchForm") Role role,
			@PathVariable("page") int page) {
		// current page
		Paging paging = new Paging(3);
		paging.setIndexPage(page);
		List<Role> roles = roleService.getList(role, paging);
		if (session.getAttribute(Constant.MSG_SUCCESS) != null) {
			model.addAttribute(Constant.MSG_SUCCESS, session.getAttribute(Constant.MSG_SUCCESS));
			session.removeAttribute(Constant.MSG_SUCCESS);
		}
		if (session.getAttribute(Constant.MSG_ERROR) != null) {
			model.addAttribute(Constant.MSG_ERROR, session.getAttribute(Constant.MSG_ERROR));
			session.removeAttribute(Constant.MSG_ERROR);
		}
		model.addAttribute("pageInfo", paging);
		model.addAttribute("roles", roles);
		return "role-list";
	}

	@GetMapping("/role/add")
	public String add(Model model) {
		log.info("add role !");
		model.addAttribute("titlePage", "Add role");
		model.addAttribute("modelForm", new Role());
		model.addAttribute("viewOnly", false);
		return "role-action";
	}

	@GetMapping("/role/edit/{id}")
	public String edit(Model model, @PathVariable("id") int id) {
		log.info("edit role with id = " + id);
		List<Role> roles = roleService.findByProperties("id", id);
		if (roles != null && !roles.isEmpty()) {
			Role role = roles.get(0);
			model.addAttribute("titlePage", "Edit role");
			model.addAttribute("modelForm", role);
			model.addAttribute("viewOnly", false);
			return "role-action";
		}
		return "redirect:/role/list";
	}

	@GetMapping("/role/view/{id}")
	public String view(Model model, @PathVariable("id") int id) {
		log.info("view role with id = " + id);
		List<Role> roles = roleService.findByProperties("id", id);
		if (roles != null && !roles.isEmpty()) {
			Role role = roles.get(0);
			model.addAttribute("titlePage", "View user");
			model.addAttribute("modelForm", role);
			model.addAttribute("viewOnly", true);
			return "role-action";
		}
		return "redirect:/role/list";
	}

	@PostMapping("/role/save")
	public String save(Model model, @ModelAttribute("modelForm") @Validated Role role, BindingResult result,
			HttpSession session) {
		if (result.hasErrors()) {
			if (role.getId() != null) {
				model.addAttribute("titlePage", "Edit role");
				model.addAttribute("editMode", true);
			} else {
				model.addAttribute("titlePage", "Add role");
			}

			model.addAttribute("modelForm", role);
			model.addAttribute("viewOnly", false);
			return "role-action";
		}
		// chuc nang la update
		if (role.getId() != null && role.getId() != 0) {
			try {
				roleService.updateRole(role);
				session.setAttribute(Constant.MSG_SUCCESS, "Update success !!!");
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e.getMessage());
				session.setAttribute(Constant.MSG_ERROR, "Update has Error !!!");
			}
		} else {
			// chuc nag insert
			try {
				roleService.saveRole(role);
				session.setAttribute(Constant.MSG_SUCCESS, "Insert Success !!!");
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e.getMessage());
				session.setAttribute(Constant.MSG_ERROR, "Insert has Error !!!");
			}
		}
		return "redirect:/role/list";
	}

	@GetMapping("/role/delete/{id}")
	public String view(Model model, @PathVariable("id") int id, HttpSession session) {
		log.info("Delete role id : " + id);
		Role role = roleService.findById(id);
		if (role != null) {
			try {
				roleService.deleteRole(role);
				// thong bao
				session.setAttribute(Constant.MSG_SUCCESS, "Delete Success !!!");
			} catch (Exception e) {
				e.printStackTrace();
				// thong bao
				session.setAttribute(Constant.MSG_ERROR, "Delete has Error !!!");
			}
		}
		return "redirect:/role/list";
	}
}

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

import com.ninhhoangcuong.model.Category;
import com.ninhhoangcuong.model.Paging;
import com.ninhhoangcuong.model.ProductInfo;
import com.ninhhoangcuong.service.ProductService;
import com.ninhhoangcuong.utils.Constant;
import com.ninhhoangcuong.validate.ProductInfoValidator;

@Controller
public class ProductInfoController {
	@Autowired
	private ProductService productService;
	@Autowired
	private ProductInfoValidator productInfoValidator;
	static final Logger log = Logger.getLogger(ProductInfoController.class);

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		if (binder.getTarget() == null) {
			return;
		}
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(simpleDateFormat, true));
		if (binder.getTarget().getClass() == ProductInfo.class) {
			binder.setValidator(productInfoValidator);
		}
	}

	@RequestMapping(value = { "/product-info/list", "/product-info/list/" })
	public String redirectShowProductInfoList() {
		return "redirect:/product-info/list/1";
	}

	@RequestMapping(value = "/product-info/list/{page}")
	public String showProductInfoList(Model model, HttpSession session,
			@ModelAttribute("searchForm") ProductInfo productInfo, @PathVariable("page") int page) {
		// current page
		Paging paging = new Paging(3);
		paging.setIndexPage(page);
		List<ProductInfo> productInfos = productService.getAllProductInfo(productInfo, paging);
		// display message after action
		if (session.getAttribute(Constant.MSG_SUCCESS) != null) {
			model.addAttribute(Constant.MSG_SUCCESS, session.getAttribute(Constant.MSG_SUCCESS));
			session.removeAttribute(Constant.MSG_SUCCESS);
		}
		if (session.getAttribute(Constant.MSG_ERROR) != null) {
			model.addAttribute(Constant.MSG_ERROR, session.getAttribute(Constant.MSG_ERROR));
			session.removeAttribute(Constant.MSG_ERROR);
		}
		model.addAttribute("pageInfo", paging);
		model.addAttribute("productInfos", productInfos);
		return "productInfo-list";
	}

	@GetMapping("/product-info/add")
	public String add(Model model) {
		Map<String, String> mapCategory = productService.getAllCategory(null, null).stream().collect(
				Collectors.toMap(c -> c.getId().toString(), Category::getName, (oldValue, newValue) -> newValue));
		model.addAttribute("titlePage", "Add ProductInfo");
		model.addAttribute("modelForm", new ProductInfo());
		model.addAttribute("viewOnly", false);
		model.addAttribute("mapCategory", mapCategory);
		return "productInfo-action";
	}

	@GetMapping("/product-info/edit/{id}")
	public String edit(Model model, @PathVariable("id") int id) {
		log.info("edit ProductInfo with id = " + id);
		ProductInfo productInfo = productService.findProductInfoById(id);
		if (productInfo != null) {
			Map<String, String> mapCategory = productService.getAllCategory(null, null).stream().collect(
					Collectors.toMap(c -> c.getId().toString(), Category::getName, (oldValue, newValue) -> newValue));
			productInfo.setCateId(productInfo.getCategory().getId());
			model.addAttribute("mapCategory", mapCategory);
			model.addAttribute("titlePage", "Edit ProductInfo");
			model.addAttribute("modelForm", productInfo);
			model.addAttribute("viewOnly", false);
			return "productInfo-action";
		}
		return "redirect:/product-info/list";
	}

	@GetMapping("/product-info/view/{id}")
	public String view(Model model, @PathVariable("id") int id) {
		log.info("view ProductInfo with id = " + id);
		ProductInfo productInfo = productService.findProductInfoById(id);
		if (productInfo != null) {
			model.addAttribute("titlePage", "View ProductInfo");
			model.addAttribute("modelForm", productInfo);
			model.addAttribute("viewOnly", true);
			return "productInfo-action";
		}
		return "redirect:/product-info/list";
	}

	@PostMapping("/product-info/save")
	public String save(Model model, @ModelAttribute("modelForm") @Validated ProductInfo productInfo,
			BindingResult result, HttpSession session) {
		if (result.hasErrors()) {
			if (productInfo.getId() != null) {
				model.addAttribute("titlePage", "Edit ProductInfo");
			} else {
				model.addAttribute("titlePage", "Add ProductInfo");
			}
			Map<String, String> mapCategory = productService.getAllCategory(null, null).stream().collect(
					Collectors.toMap(c -> c.getId().toString(), Category::getName, (oldValue, newValue) -> newValue));
			model.addAttribute("mapCategory", mapCategory);
			model.addAttribute("modelForm", productInfo);
			model.addAttribute("viewOnly", false);
			return "productInfo-action";
		}
		Category category = new Category();
		category.setId(productInfo.getCateId());
		productInfo.setCategory(category);
		// neu ProductInfo ton tai Id => chuc nang la update
		if (productInfo.getId() != null && productInfo.getId() != 0) {
			try {
				productService.updateProductInfo(productInfo);
				session.setAttribute(Constant.MSG_SUCCESS, "Update success !!!");
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e.getMessage());
				session.setAttribute(Constant.MSG_ERROR, "Update has Error !!!");
			}
		} else {
			// khong co id => chuc nag insert
			try {
				productService.saveProductInfo(productInfo);
				session.setAttribute(Constant.MSG_SUCCESS, "Insert Success !!!");
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e.getMessage());
				session.setAttribute(Constant.MSG_ERROR, "Insert has Error !!!");
			}
		}
		return "redirect:/product-info/list";
	}

	@GetMapping("/product-info/delete/{id}")
	public String delete(Model model, @PathVariable("id") int id, HttpSession session) {
		log.info("Delete ProductInfo id : " + id);
		ProductInfo productInfo = productService.findProductInfoById(id);
		if (productInfo != null) {
			try {
				productService.deleteProductInfo(productInfo);
				// thong bao
				session.setAttribute(Constant.MSG_SUCCESS, "Delete Success !!!");
			} catch (Exception e) {
				e.printStackTrace();
				// thong bao
				session.setAttribute(Constant.MSG_ERROR, "Delete has Error !!!");
			}
		}
		return "redirect:/product-info/list";
	}
}

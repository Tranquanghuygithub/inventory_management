package com.ninhhoangcuong.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ninhhoangcuong.model.Paging;
import com.ninhhoangcuong.model.ProductInStock;
import com.ninhhoangcuong.service.ProductInStockService;

@Controller
public class ProductInStockController {
	@Autowired
	private ProductInStockService productInStockService;

	@RequestMapping(value = { "/product-in-stock/list", "/product-in-stock/list/" })
	public String redirectToPage1() {
		return "redirect:/product-in-stock/list/1";
	}

	@RequestMapping(value = "/product-in-stock/list/{page}")
	public String showProductInStockList(@ModelAttribute("searchForm") ProductInStock productInStock, Model model,
			@PathVariable("page") int page) {
		Paging paging = new Paging(5);
		paging.setIndexPage(page);
		List<ProductInStock> productInStocks = productInStockService.getAll(productInStock, paging);
		model.addAttribute("productInStocks", productInStocks);
		model.addAttribute("pageInfo", paging);
		model.addAttribute("titlePage", "List Product In Stock");
		return "productInStock-list";
	}
}

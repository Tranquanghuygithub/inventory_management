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
import org.springframework.web.servlet.ModelAndView;

import com.ninhhoangcuong.model.Invoice;
import com.ninhhoangcuong.model.Paging;
import com.ninhhoangcuong.model.ProductInfo;
import com.ninhhoangcuong.service.InvoiceReport;
import com.ninhhoangcuong.service.InvoiceService;
import com.ninhhoangcuong.service.ProductService;
import com.ninhhoangcuong.utils.Constant;
import com.ninhhoangcuong.validate.InvoiceValidator;

@Controller
public class GoodsReceiptController {
	@Autowired
	private InvoiceService invoiceService;
	@Autowired
	private InvoiceValidator invoiceValidator;
	@Autowired
	private ProductService productService;
	private static final Logger log = Logger.getLogger(GoodsReceiptController.class);

	@InitBinder
	private void initBinder(WebDataBinder binder) {
		if (binder.getTarget() == null) {
			return;
		}
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(simpleDateFormat, true));
		if (binder.getTarget().getClass() == Invoice.class) {
			binder.setValidator(invoiceValidator);
		}
	}

	@RequestMapping(value = { "/goods-receipt/list", "/goods-receipt/list/" })
	public String redirect() {
		return "redirect:/goods-receipt/list/1";
	}

	@RequestMapping(value = "/goods-receipt/list/{page}")
	public String showListReceipt(Model model, @ModelAttribute("searchForm") Invoice invoice,
			@PathVariable("page") int page, HttpSession session) {
		Paging paging = new Paging(5);
		paging.setIndexPage(page);
		if (invoice == null) {
			invoice = new Invoice();
		}
		invoice.setType(Constant.TYPE_GOODS_RECEIPT);
		List<Invoice> invoices = invoiceService.getList(invoice, paging);
		if (session.getAttribute(Constant.MSG_SUCCESS) != null) {
			model.addAttribute(Constant.MSG_SUCCESS, session.getAttribute(Constant.MSG_SUCCESS));
			session.removeAttribute(Constant.MSG_SUCCESS);
		}
		if (session.getAttribute(Constant.MSG_ERROR) != null) {
			model.addAttribute(Constant.MSG_ERROR, session.getAttribute(Constant.MSG_ERROR));
			session.removeAttribute(Constant.MSG_ERROR);
		}
		model.addAttribute("pageInfo", paging);
		model.addAttribute("invoices", invoices);
		return "goods-receipt-list";
	}

	@GetMapping("/goods-receipt/add")
	public String add(Model model) {
		model.addAttribute("titlePage", "Add Invoice");
		model.addAttribute("modelForm", new Invoice());
		model.addAttribute("viewOnly", false);
		model.addAttribute("mapProduct", initMapProduct());
		return "goods-receipt-action";
	}

	@GetMapping("/goods-receipt/edit/{id}")
	public String edit(Model model, @PathVariable("id") int id) {
		log.info("Edit invoice with id : " + id);
		Invoice invoice = invoiceService.findInvoiceById(id);
		if (invoice != null) {
			model.addAttribute("titlePage", "View Invoice");
			invoice.setProductId(invoice.getProductInfo().getId());
			model.addAttribute("modelForm", invoice);
			model.addAttribute("viewOnly", false);
			model.addAttribute("mapProduct", initMapProduct());
			return "goods-receipt-action";
		}
		return "redirect:/goods-receipt-list";
	}

	@GetMapping("/goods-receipt/view/{id}")
	public String view(Model model, @PathVariable("id") int id) {
		log.info("View invoice with id : " + id);
		Invoice invoice = invoiceService.findInvoiceById(id);
		if (invoice != null) {
			model.addAttribute("titlePage", "View Invoice");
			model.addAttribute("modelForm", invoice);
			model.addAttribute("viewOnly", true);
			model.addAttribute("mapProduct", initMapProduct());
			return "goods-receipt-action";
		}
		return "redirect:/goods-receipt-list";
	}

	@PostMapping("/goods-receipt/save")
	public String save(Model model, @ModelAttribute("modelForm") @Validated Invoice invoice, BindingResult result,
			HttpSession session) {
		if (result.hasErrors()) {
			if (invoice.getId() != null) {
				model.addAttribute("titlePage", "Edit Invoice");
			} else {
				model.addAttribute("titlePage", "Add Invoice");
			}
			model.addAttribute("modelForm", invoice);
			model.addAttribute("viewOnly", false);
			model.addAttribute("mapProduct", initMapProduct());
			return "goods-receipt-action";
		}
		invoice.setType(Constant.TYPE_GOODS_RECEIPT);
		// edit
		if (invoice.getId() != null && invoice.getId() != 0) {
			try {
				invoiceService.update(invoice);
				session.setAttribute(Constant.MSG_SUCCESS, "Edit Invoice Success !!!");
			} catch (Exception e) {
				e.printStackTrace();
				log.info(e.getMessage());
				session.setAttribute(Constant.MSG_ERROR, "Edit Invoice Error");
			}
		} else {
			try {
				invoiceService.save(invoice);
				session.setAttribute(Constant.MSG_SUCCESS, "Add Invoice Success !!!");
			} catch (Exception e) {
				e.printStackTrace();
				log.info(e.getMessage());
				session.setAttribute(Constant.MSG_ERROR, "Add Invoice Error !!!");
			}
		}
		return "redirect:/goods-receipt/list";
	}

	@GetMapping("/goods-receipt/export")
	public ModelAndView exportReportReceipt() {
		ModelAndView modelAndView = new ModelAndView();
		Invoice invoice = new Invoice();
		invoice.setType(Constant.TYPE_GOODS_RECEIPT);
		List<Invoice> invoices = invoiceService.getList(invoice, null);
		modelAndView.addObject(Constant.KEY_INVOICE_REPORT, invoices);
		modelAndView.setView(new InvoiceReport());
		return modelAndView;
	}

	private Map<String, String> initMapProduct() {
		Map<String, String> mapParams = productService.getAllProductInfo(null, null).stream().collect(
				Collectors.toMap(p -> p.getId().toString(), ProductInfo::getName, (oldValue, newValue) -> newValue));
		return mapParams;
	}
}

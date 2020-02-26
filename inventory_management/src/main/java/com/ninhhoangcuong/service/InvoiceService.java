package com.ninhhoangcuong.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ninhhoangcuong.dao.InvoiceDAO;
import com.ninhhoangcuong.model.Invoice;
import com.ninhhoangcuong.model.Paging;
import com.ninhhoangcuong.model.ProductInfo;
import com.ninhhoangcuong.utils.Constant;

@Service
public class InvoiceService {
	@Autowired
	private InvoiceDAO<Invoice> invoiceDAO;
	@Autowired
	private ProductInStockService productInStockService;
	@Autowired
	private HistoryService historyService;
	private static final Logger log = Logger.getLogger(InvoiceService.class);

	public void save(Invoice invoice) throws Exception {
		ProductInfo productInfo = new ProductInfo();
		productInfo.setId(invoice.getProductId());
		invoice.setProductInfo(productInfo);
		invoice.setActiveFlag(1);
		invoice.setCreateDate(new Date());
		invoice.setUpdateDate(new Date());
		invoiceDAO.save(invoice);
		historyService.save(invoice, Constant.ACTION_ADD);
		productInStockService.saveOrUpdate(invoice);
	}

	public void update(Invoice invoice) throws Exception {
		int originalQty = invoiceDAO.findById(Invoice.class, invoice.getId()).getQty();
		ProductInfo productInfo = new ProductInfo();
		productInfo.setId(invoice.getProductId());
		invoice.setProductInfo(productInfo);
		invoice.setUpdateDate(new Date());
		Invoice invoice2 = new Invoice();
		invoice2.setProductInfo(invoice.getProductInfo());
		invoice2.setQty(invoice.getQty() - originalQty);
		invoice2.setPrice(invoice.getPrice());
		invoice2.setType(invoice.getType());
		invoiceDAO.update(invoice);
		historyService.save(invoice, Constant.ACTION_EDIT);
		productInStockService.saveOrUpdate(invoice2);
	}

	public List<Invoice> find(String property, Object value) {
		log.info("find invoice by property : " + property + " value : " + value.toString());
		return invoiceDAO.findByProperty(property, value);
	}

	public Invoice findInvoiceById(int id) {
		log.info("find invoice by id : " + id);
		return invoiceDAO.findById(Invoice.class, id);
	}

	public List<Invoice> getList(Invoice invoice, Paging paging) {
		StringBuilder queryString = new StringBuilder();
		Map<String, Object> mapParams = new HashMap<String, Object>();
		if (invoice != null) {
			if (invoice.getType() != 0) {
				queryString.append(" and model.type =:type");
				mapParams.put("type", invoice.getType());
			}
			if (!StringUtils.isEmpty(invoice.getCode())) {
				queryString.append(" and model.code =:code");
				mapParams.put("code", invoice.getCode());
			}
			if (invoice.getFromDate() != null) {
				queryString.append(" and model.updateDate >= :fromDate");
				mapParams.put("fromDate", invoice.getFromDate());
			}
			if (invoice.getToDate() != null) {
				queryString.append(" and model.updateDate <= :toDate");
				mapParams.put("toDate", invoice.getToDate());
			}
		}
		return invoiceDAO.findAll(queryString.toString(), mapParams, paging);
	}
}

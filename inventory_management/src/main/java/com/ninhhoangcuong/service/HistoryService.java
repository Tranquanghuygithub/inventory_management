package com.ninhhoangcuong.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ninhhoangcuong.dao.HistoryDAO;
import com.ninhhoangcuong.model.History;
import com.ninhhoangcuong.model.Invoice;
import com.ninhhoangcuong.model.Paging;

@Service
public class HistoryService {
	@Autowired
	private HistoryDAO<History> historyDAO;
	private static final Logger log = Logger.getLogger(HistoryService.class);

	public List<History> getAll(History history, Paging paging) {
		log.info("get All history !");
		StringBuilder queryString = new StringBuilder();
		Map<String, Object> mapParams = new HashMap<String, Object>();
		if (history != null) {
			// search by product info
			if (history.getProductInfo() != null) {
				// search by category name
				if (!StringUtils.isEmpty(history.getProductInfo().getCategory().getName())) {
					queryString.append("and model.productInfo.category.name like :cateName ");
					mapParams.put("cateName", "%" + history.getProductInfo().getCategory().getName() + "%");
				}
				// search by name product
				if (!StringUtils.isEmpty(history.getProductInfo().getName())) {
					queryString.append("and model.productInfo.name like :productName");
					mapParams.put("productName", history.getProductInfo().getName());
				}
				// search by code product
				if (!StringUtils.isEmpty(history.getProductInfo().getCode())) {
					queryString.append("and model.productInfo.code =:code");
					mapParams.put("code", history.getProductInfo().getCode());
				}
			}
			// search by action name
			if (!StringUtils.isEmpty(history.getActionName())) {
				queryString.append(" and model.actionName like :actionName ");
				mapParams.put("actionName", history.getActionName());
			}
			// search by type
			if (history.getType() > 0) {
				queryString.append(" and model.type =:typeAction ");
				mapParams.put("typeAction", history.getType());
			}
		}
		return historyDAO.findAll(queryString.toString(), mapParams, paging);
	}

	public void save(Invoice invoice, String action) throws Exception {
		log.info("save history !");
		History history = new History();
		history.setProductInfo(invoice.getProductInfo());
		history.setType(invoice.getType());
		history.setQty(invoice.getQty());
		history.setPrice(invoice.getPrice());
		history.setActionName(action);
		history.setActiveFlag(1);
		history.setCreateDate(new Date());
		history.setUpdateDate(new Date());
		historyDAO.save(history);
	}

}

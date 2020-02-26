package com.ninhhoangcuong.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ninhhoangcuong.dao.ProductInStockDAO;
import com.ninhhoangcuong.model.Invoice;
import com.ninhhoangcuong.model.Paging;
import com.ninhhoangcuong.model.ProductInStock;
import com.ninhhoangcuong.model.ProductInfo;

@Service
public class ProductInStockService {
	@Autowired
	private ProductInStockDAO<ProductInStock> productInStockDAO;

	private final static Logger log = Logger.getLogger(ProductInStockService.class);

	public List<ProductInStock> getAll(ProductInStock productInStock, Paging paging) {
		log.info("Show All Product In Stock");
		StringBuilder queryString = new StringBuilder();
		Map<String, Object> mapParams = new HashMap<String, Object>();
		if (productInStock != null && productInStock.getProductInfo() != null) {
			// search category name
			if (!StringUtils.isEmpty(productInStock.getProductInfo().getCategory().getName())) {
				queryString.append(" and model.productInfo.category.name like :cateName ");
				mapParams.put("cateName", "%" + productInStock.getProductInfo().getCategory().getName() + "%");
			}
			// search code product
			if (!StringUtils.isEmpty(productInStock.getProductInfo().getCode())
					&& productInStock.getProductInfo().getCode() != null) {
				queryString.append(" and model.productInfo.code = :code");
				mapParams.put("code", productInStock.getProductInfo().getCode());
			}
			// search by name product
			if (!StringUtils.isEmpty(productInStock.getProductInfo().getName())
					&& productInStock.getProductInfo().getName() != null) {
				queryString.append(" and model.productInfo.name like :name");
				mapParams.put("name", "%" + productInStock.getProductInfo().getName() + "%");
			}
		}
		return productInStockDAO.findAll(queryString.toString(), mapParams, paging);
	}

	public void saveOrUpdate(Invoice invoice) throws Exception {
		log.info("Save or Update product in stock");
		if (invoice.getProductInfo() != null) {
			int id = invoice.getProductInfo().getId();
			List<ProductInStock> productInStocks = productInStockDAO.findByProperty("productInfo.id", id);
			ProductInStock productInStock = null;
			if (productInStocks != null && !productInStocks.isEmpty()) {
				productInStock = productInStocks.get(0);
				// update
				log.info("update qty " + invoice.getQty() + " and price = " + invoice.getPrice());
				if (invoice.getType() == 2) {
					productInStock.setQty(productInStock.getQty() - invoice.getQty());
				} else {
					productInStock.setQty(productInStock.getQty() + invoice.getQty());
					productInStock.setPrice(invoice.getPrice());
				}
				productInStock.setUpdateDate(new Date());
				productInStockDAO.update(productInStock);
			} else if (invoice.getType() == 1) {// neu la save va la nhap hang moi vao kho
				log.info("insert to stock qty : " + invoice.getQty() + " and price : " + invoice.getPrice());
				productInStock = new ProductInStock();
				ProductInfo productInfo = new ProductInfo();
				productInfo.setId(invoice.getProductInfo().getId());
				productInStock.setProductInfo(productInfo);
				productInStock.setActiveFlag(1);
				productInStock.setCreateDate(new Date());
				productInStock.setUpdateDate(new Date());
				productInStock.setPrice(invoice.getPrice());
				productInStock.setQty(invoice.getQty());
				productInStockDAO.save(productInStock);
			}
		}
	}
}

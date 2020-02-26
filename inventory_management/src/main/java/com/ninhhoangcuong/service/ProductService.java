package com.ninhhoangcuong.service;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.ninhhoangcuong.dao.CategoryDAO;
import com.ninhhoangcuong.dao.ProductInfoDAO;
import com.ninhhoangcuong.model.Category;
import com.ninhhoangcuong.model.Paging;
import com.ninhhoangcuong.model.ProductInfo;
import com.ninhhoangcuong.utils.ConfigLoader;

@Service
public class ProductService {
	private static final Logger log = Logger.getLogger(ProductService.class);
	@Autowired
	private CategoryDAO<Category> categoryDAO;
	@Autowired
	private ProductInfoDAO<ProductInfo> productInfoDAO;

	public void saveCategory(Category category) throws Exception {
		log.info("insert category " + category.toString());
		category.setCreateDate(new Date());
		category.setUpdateDate(new Date());
		category.setActiveFlag(1);
		categoryDAO.save(category);
	}

	public void updateCategory(Category category) throws Exception {
		log.info("update category " + category.toString());
		category.setActiveFlag(1);
		category.setUpdateDate(new Date());
		categoryDAO.update(category);
	}

	public void deleteCategory(Category category) throws Exception {
		log.info("Delete category : " + category.toString());
		category.setActiveFlag(0);
		category.setUpdateDate(new Date());
		categoryDAO.update(category);
	}

	public List<Category> findCategory(String property, Object value) {
		log.info("find category : property - " + property + " value - " + value.toString());
		return categoryDAO.findByProperty(property, value);
	}

	public List<Category> getAllCategory(Category category, Paging paging) {
		log.info("Get all Category");
		StringBuilder queryString = new StringBuilder();
		Map<String, Object> mapParams = new HashMap<String, Object>();
		if (category != null) {
			if (category.getId() != null && category.getId() != 0) {
				queryString.append("and model.id = :id");
				mapParams.put("id", category.getId());
			}
			if (category.getCode() != null && !StringUtils.isEmpty(category.getCode())) {
				queryString.append("and model.code=:code");
				mapParams.put("code", category.getCode());
			}
			if (category.getName() != null && !StringUtils.isEmpty(category.getName())) {
				queryString.append("and model.name like :name");
				mapParams.put("name", "%" + category.getName() + "%");
			}
		}
		return categoryDAO.findAll(queryString.toString(), mapParams, paging);
	}

	public Category findCategoryById(int id) {
		log.info("Find category by Id : " + id);
		return categoryDAO.findById(Category.class, id);
	}

	// product info service
	public void saveProductInfo(ProductInfo productInfo) throws Exception {
		log.info("insert productInfo " + productInfo.toString());
		productInfo.setCreateDate(new Date());
		productInfo.setUpdateDate(new Date());
		productInfo.setActiveFlag(1);
		String fileName = processUploadFile(productInfo.getMultipartFile());
		productInfo.setImgUrl("/upload/" + fileName);
		productInfoDAO.save(productInfo);
	}

	public void updateProductInfo(ProductInfo productInfo) throws Exception {
		log.info("update productInfo " + productInfo.toString());
		productInfo.setActiveFlag(1);
		productInfo.setUpdateDate(new Date());
		if (!productInfo.getMultipartFile().getOriginalFilename().isEmpty()) {
			String fileName = processUploadFile(productInfo.getMultipartFile());
			productInfo.setImgUrl("/upload/" + fileName);
		}
		productInfoDAO.update(productInfo);
	}

	public void deleteProductInfo(ProductInfo productInfo) throws Exception {
		log.info("Delete productInfo : " + productInfo.toString());
		productInfo.setActiveFlag(0);
		productInfo.setUpdateDate(new Date());
		productInfoDAO.update(productInfo);
	}

	public List<ProductInfo> findProductInfo(String property, Object value) {
		log.info("find productInfo : property - " + property + " value - " + value.toString());
		return productInfoDAO.findByProperty(property, value);
	}

	public List<ProductInfo> getAllProductInfo(ProductInfo productInfo, Paging paging) {
		log.info("Get all productInfo");
		StringBuilder queryString = new StringBuilder();
		Map<String, Object> mapParams = new HashMap<String, Object>();
		if (productInfo != null) {
			if (productInfo.getId() != null && productInfo.getId() != 0) {
				queryString.append("and model.id = :id");
				mapParams.put("id", productInfo.getId());
			}
			if (productInfo.getCode() != null && !StringUtils.isEmpty(productInfo.getCode())) {
				queryString.append("and model.code=:code");
				mapParams.put("code", productInfo.getCode());
			}
			if (productInfo.getName() != null && !StringUtils.isEmpty(productInfo.getName())) {
				queryString.append("and model.name like :name");
				mapParams.put("name", "%" + productInfo.getName() + "%");
			}
		}
		return productInfoDAO.findAll(queryString.toString(), mapParams, paging);
	}

	public ProductInfo findProductInfoById(int id) {
		log.info("Find ProductInfo by Id : " + id);
		return productInfoDAO.findById(ProductInfo.class, id);
	}

	private String processUploadFile(MultipartFile multipartFile) throws IllegalStateException, IOException {
		if (multipartFile != null) {
			File dir = new File(ConfigLoader.getInstance().getValue("upload.location"));
			if (!dir.exists()) {
				dir.mkdirs();
			}
		}
		String fileName = System.currentTimeMillis() + "_" + multipartFile.getOriginalFilename();
		File file = new File(ConfigLoader.getInstance().getValue("upload.location"), fileName);
		multipartFile.transferTo(file);
		return fileName;
	}
}

package com.ninhhoangcuong.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ninhhoangcuong.dao.ProductInStockDAO;
import com.ninhhoangcuong.model.ProductInStock;

@Repository
@Transactional(rollbackFor = Exception.class)
public class ProductInStockDAOImpl extends BaseDAOImpl<ProductInStock> implements ProductInStockDAO<ProductInStock> {

}
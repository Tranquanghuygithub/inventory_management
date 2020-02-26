package com.ninhhoangcuong.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ninhhoangcuong.dao.ProductInfoDAO;
import com.ninhhoangcuong.model.ProductInfo;

@Repository
@Transactional(rollbackFor = Exception.class)
public class ProductInfoImpl extends BaseDAOImpl<ProductInfo> implements ProductInfoDAO<ProductInfo> {

}

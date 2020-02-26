package com.ninhhoangcuong.dao.impl;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.ninhhoangcuong.dao.MenuDAO;
import com.ninhhoangcuong.model.Menu;

@Repository
@Transactional(rollbackOn = Exception.class)
public class MenuDAOImpl extends BaseDAOImpl<Menu> implements MenuDAO<Menu> {

}

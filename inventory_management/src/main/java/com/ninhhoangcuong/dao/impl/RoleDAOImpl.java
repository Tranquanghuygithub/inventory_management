package com.ninhhoangcuong.dao.impl;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.ninhhoangcuong.dao.RoleDAO;
import com.ninhhoangcuong.model.Role;

@Repository
@Transactional(rollbackOn = Exception.class)
public class RoleDAOImpl extends BaseDAOImpl<Role> implements RoleDAO<Role> {

}

package com.ninhhoangcuong.dao.impl;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.ninhhoangcuong.dao.UserRoleDAO;
import com.ninhhoangcuong.model.UserRole;

@Repository
@Transactional(rollbackOn = Exception.class)
public class UserRoleDAOImpl extends BaseDAOImpl<UserRole> implements UserRoleDAO<UserRole> {

}

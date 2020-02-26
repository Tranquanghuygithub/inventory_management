package com.ninhhoangcuong.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ninhhoangcuong.dao.UserDAO;
import com.ninhhoangcuong.model.Users;
@Repository
@Transactional(rollbackFor = Exception.class)
public class UserDAOImpl extends BaseDAOImpl<Users> implements UserDAO<Users> {

}

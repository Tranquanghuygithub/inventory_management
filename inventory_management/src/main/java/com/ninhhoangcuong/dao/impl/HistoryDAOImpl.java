package com.ninhhoangcuong.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ninhhoangcuong.dao.HistoryDAO;
import com.ninhhoangcuong.model.History;

@Repository
@Transactional(rollbackFor = Exception.class)
public class HistoryDAOImpl extends BaseDAOImpl<History> implements HistoryDAO<History> {

}

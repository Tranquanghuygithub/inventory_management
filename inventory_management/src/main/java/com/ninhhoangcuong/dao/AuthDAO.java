package com.ninhhoangcuong.dao;

import com.ninhhoangcuong.model.Auth;

public interface AuthDAO<E> extends BaseDAO<E> {
	public Auth find(int roleId, int menuId);
}

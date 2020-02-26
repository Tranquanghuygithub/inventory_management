package com.ninhhoangcuong.security;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;

import com.ninhhoangcuong.model.Auth;
import com.ninhhoangcuong.model.UserRole;
import com.ninhhoangcuong.model.Users;
import com.ninhhoangcuong.utils.Constant;

public class FilterSytem implements HandlerInterceptor {
	Logger logger = Logger.getLogger(FilterSytem.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		logger.info("Request URL : " + request.getRequestURI());
		Users user = (Users) request.getSession().getAttribute(Constant.USER_INFO);
		if (user == null) {
			response.sendRedirect(request.getContextPath() + "/login");
			return true;
		} else {
			String url = request.getServletPath();
			if (!hasPermission(url, user)) {
				logger.info("Asscess Diend : " + request.getRequestURI());
				response.sendRedirect(request.getContextPath() + "/access-denied");
			}
		}
		return true;
	}

	public boolean hasPermission(String url, Users user) {
		UserRole userRole = (UserRole) user.getUserRoles().iterator().next();
		Set<Auth> auths = userRole.getRole().getAuths();
		for (Auth auth : auths) {
			if (url.contains(auth.getMenu().getUrl())) {
				return auth.getPermission() == 1;
			}
		}
		return false;
	}
}

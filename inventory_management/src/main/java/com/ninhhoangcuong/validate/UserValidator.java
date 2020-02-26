package com.ninhhoangcuong.validate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.ninhhoangcuong.model.Users;
import com.ninhhoangcuong.service.UserService;

@Component
public class UserValidator implements Validator {
	@Autowired
	private UserService userService;

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz == Users.class;
	}

	@Override
	public void validate(Object target, Errors errors) {
		Users user = (Users) target;
		ValidationUtils.rejectIfEmpty(errors, "userName", "msg.required");
		ValidationUtils.rejectIfEmpty(errors, "password", "msg.required");
		if (user.getId() == null) {
			ValidationUtils.rejectIfEmpty(errors, "name", "msg.required");
		}
		if (user.getUserName() != null) {
			List<Users> users = userService.findByProperties("userName", user.getUserName());
			if (users != null && !users.isEmpty()) {
				// kiem tra xem la save or update
				// day la update
				if (user.getId() != null && user.getId() != 0) {
					// kiem tra xem co trung voi username hien tai ko neu trung thi ko doi username
					if (users.get(0).getId() != user.getId()) {
						errors.rejectValue("userName", "msg.username.exist");
					}
				} else {
					// chua co id => save => username ton tai
					errors.rejectValue("userName", "msg.username.exist");
				}
			}
		}
	}

}

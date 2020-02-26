package com.ninhhoangcuong.validate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.ninhhoangcuong.model.Users;
import com.ninhhoangcuong.service.UserService;
import com.ninhhoangcuong.utils.HashingPassword;

@Component
public class LoginValidator implements Validator {
	@Autowired
	private UserService userService;

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz == Users.class;
	}

	@Override
	public void validate(Object target, Errors errors) {
		Users users = (Users) target;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userName", "msg.required");
		ValidationUtils.rejectIfEmpty(errors, "password", "msg.required");
		if (!StringUtils.isEmpty(users.getUserName()) && !StringUtils.isEmpty(users.getPassword())) {
			List<Users> users2 = userService.findByProperties("userName", users.getUserName());
			if (users != null && !users2.isEmpty()) {
				if (!users2.get(0).getPassword().equals(HashingPassword.encrypt(users.getPassword()))) {
					errors.rejectValue("password", "msg.wrong.password");
				}
			} else {
				errors.rejectValue("userName", "msg.wrong.username");
			}
		}
	}

}

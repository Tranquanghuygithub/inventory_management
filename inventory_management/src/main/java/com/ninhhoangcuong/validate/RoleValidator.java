package com.ninhhoangcuong.validate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.ninhhoangcuong.model.Role;
import com.ninhhoangcuong.service.RoleService;

@Component
public class RoleValidator implements Validator {
	@Autowired
	private RoleService roleService;

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz == Role.class;
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "roleName", "msg.required");
		ValidationUtils.rejectIfEmpty(errors, "description", "msg.required");
		Role role = (Role) target;
		if (role.getRoleName() != null) {
			List<Role> roles = roleService.findByProperties("roleName", role.getRoleName());
			if (roles != null && !roles.isEmpty()) {
				// kiem tra xem la save or update
				// day la updagte
				if (role.getId() != 0 && role.getId() != null) {
					// kiem tra xem id truyen vao co trung vs id hien tai ko neu trung la ko sua
					// ten,ko trung la trung ten
					if (roles.get(0).getId() != role.getId()) {
						errors.rejectValue("roleName", "msg.roleName.exist");
					}
				} else {
					// la save
					errors.rejectValue("roleName", "msg.roleName.exist");

				}
			}
		}
	}
}

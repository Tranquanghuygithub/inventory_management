package com.ninhhoangcuong.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.ninhhoangcuong.utils.Constant;

@Controller
public class LogoutController {
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute(Constant.MENU_SESSION);
		session.removeAttribute(Constant.USER_INFO);
		return "redirect:/login";
	}
}

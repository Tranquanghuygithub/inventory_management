package com.ninhhoangcuong.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Hello {
	@GetMapping("/hello")
	public String HelloSpring() {
		return "error-page/page_403";
	}
}

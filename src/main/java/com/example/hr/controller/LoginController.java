package com.example.hr.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

	// 실제 인증(세션/권한)은 Day 12에서 다룬다. 지금은 화면만 연결.
	@GetMapping("/login")
	public String login() {
		return "login";
	}
}

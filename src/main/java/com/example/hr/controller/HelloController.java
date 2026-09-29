package com.example.hr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.hr.dto.EmpSearchCond;
import com.example.hr.service.EmpService;

@Controller

public class HelloController {
	
	// 서비스의 총건수를 조회 하고 
	// 화면에 전달
	@Autowired
	private EmpService service;
	
	@GetMapping("/hello")
	public void hello(Model model){
		int totalCnt = service.totalCnt(new EmpSearchCond());
		// 화면에 데이터를 전달 하기 위해서 Model에 저장
		model.addAttribute("totalCnt", totalCnt);
		
	}
}

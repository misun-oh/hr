package com.example.hr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.hr.dto.DeptDto;
import com.example.hr.service.DeptService;

@Controller
public class DeptController {

	@Autowired
	private DeptService service;

	@GetMapping("/depts")
	public String list(Model model) {
		service.selectAll(model);
		return "depts";
	}

	@PostMapping("/dept-save")
	public String save(@ModelAttribute DeptDto dept, Model model) {
		// deptId(PK), deptTitle, locationId(NOT NULL+FK) 중 하나라도 비어있으면
		// DB까지 안 보내고 여기서 걸러서 목록 화면으로 되돌림(+에러 메시지)
		if (!StringUtils.hasText(dept.getDeptId())
				|| !StringUtils.hasText(dept.getDeptTitle())
				|| !StringUtils.hasText(dept.getLocationId())) {
			service.selectAll(model);
			model.addAttribute("errorMessage", "부서 코드/부서명/근무지를 모두 입력하세요");
			return "depts";
		}
		service.register(dept);
		return "redirect:/depts";
	}
}

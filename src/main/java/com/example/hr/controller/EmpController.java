package com.example.hr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.hr.dto.EmpDto;
import com.example.hr.dto.EmpSearchCond;
import com.example.hr.service.DeptService;
import com.example.hr.service.EmpService;

import lombok.extern.slf4j.Slf4j;

// templates 	: 경로의 파일은 직접 호출 할 수 없고 컨트롤러를 통해서만 볼수 있다
// static 		: 요청하면 바로 서비스
@Controller
@Slf4j
public class EmpController {

	@Autowired
	private EmpService service;

	@Autowired
	private DeptService deptService;

	// 여러개의 주소를 매핑
	// 검색조건(keyword, deptId, workingOnly, sort)+페이지 정보(page)를 cond 하나로 받는다
	@GetMapping({"/", "/emps"})
	public String getMethodName(@ModelAttribute EmpSearchCond cond, Model model) {

		service.selectByCond(cond, model);

		model.addAttribute("cond", cond);
		model.addAttribute("depts", deptService.selectAll());

		return "/index";
	}
	
	
	/*
	 * 사원 상세화면
	 * 사번을 이용해서 사원정보를 조회 후 화면에 전달
	 */
	@GetMapping("/emp-detail")
	public void empDetail(@RequestParam(value = "empId"
											, defaultValue = "") 
							String empId, Model model) {
		System.out.println("empId : " + empId);
		log.info("empId" + empId);
		
		EmpDto emp = service.selectById(empId);
		model.addAttribute("emp", emp);
		
		//model.addAttribute("emp", service.selectById(empId));
		
	}
	
	@GetMapping("/admin/dashboard")
	public void getMethodName() {
		
	}
	
	 
}






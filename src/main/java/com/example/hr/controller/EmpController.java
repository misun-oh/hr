package com.example.hr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.hr.dto.EmpDto;
import com.example.hr.dto.EmpSearchCond;
import com.example.hr.service.EmpService;

import lombok.extern.slf4j.Slf4j;

// templates 	: 경로의 파일은 직접 호출 할 수 없고 컨트롤러를 통해서만 볼수 있다
// static 		: 요청하면 바로 서비스
@Controller
@Slf4j
public class EmpController {

	@Autowired
	private EmpService service;

	// 여러개의 주소를 매핑
	// 검색/페이징 조건은 GET 쿼리스트링(keyword, deptId, workingOnly, sort, page)으로 전달됨
	@GetMapping({"/", "/emps"})
	public String list(@ModelAttribute EmpSearchCond cond, Model model) {
		service.selectByCond(cond, model);
		return "index";
	}

	/*
	 * 사원 상세화면
	 * 사번을 이용해서 사원정보를 조회 후 화면에 전달
	 */
	@GetMapping("/emp-detail")
	public String empDetail(@RequestParam(value = "empId", defaultValue = "") String empId, Model model) {
		log.info("empId : {}", empId);

		EmpDto emp = service.selectById(empId);
		model.addAttribute("emp", emp);
		if (emp != null) {
			model.addAttribute("subordinates", service.selectByManagerId(empId));
		}
		return "emp-detail";
	}

	/*
	 * 사원 등록/수정 폼
	 * empId가 없으면 등록, 있으면 기존 값을 채운 수정 폼
	 */
	@GetMapping("/emp-form")
	public String empForm(@RequestParam(value = "empId", required = false) String empId, Model model) {
		if (empId != null && !empId.isBlank()) {
			model.addAttribute("emp", service.selectById(empId));
		} else {
			model.addAttribute("emp", new EmpDto());
		}
		service.addFormOptions(model);
		return "emp-form";
	}

	// 등록/수정 저장 : hidden empId 값이 있으면 수정, 없으면 신규 등록
	@PostMapping("/emp-save")
	public String empSave(@ModelAttribute EmpDto emp) {
		if (emp.getEmpId() == null || emp.getEmpId().isBlank()) {
			service.register(emp);
		} else {
			service.modify(emp);
		}
		return "redirect:/emp-detail?empId=" + emp.getEmpId();
	}

	@PostMapping("/emp-delete")
	public String empDelete(@RequestParam("empId") String empId) {
		service.remove(empId);
		return "redirect:/emps";
	}
}

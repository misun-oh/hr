package com.example.hr.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.hr.dto.EmpDto;
import com.example.hr.dto.EmpForm;
import com.example.hr.dto.EmpSearchCond;
import com.example.hr.service.AttachmentService;
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

	@Autowired
	private AttachmentService attachmentService;

	private static final String REF_TYPE_EMP = "EMP";

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
		model.addAttribute("attachments", attachmentService.selectByRef(REF_TYPE_EMP, empId));

		//model.addAttribute("emp", service.selectById(empId));

	}

	@GetMapping("/emp-form")
	public String empForm(Model model) {
		if (!model.containsAttribute("form")) {
			model.addAttribute("form", new EmpForm());
		}
		model.addAttribute("depts", deptService.selectAll());
		return "emp-form";
	}

	@PostMapping("/emps")
	public String register(@ModelAttribute("form") EmpForm form,
			BindingResult bindingResult,
			@RequestParam(value = "images", required = false) List<MultipartFile> images,
			Model model, RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("depts", deptService.selectAll());
			return "emp-form";
		}
		try {
			service.register(form, images);
			redirectAttributes.addFlashAttribute("msg", "사원이 등록되었습니다.");
			return "redirect:/emps";
		} catch (IllegalArgumentException e) {
			model.addAttribute("depts", deptService.selectAll());
			model.addAttribute("imageError", e.getMessage());
			return "emp-form";
		} catch (IOException e) {
			model.addAttribute("depts", deptService.selectAll());
			model.addAttribute("imageError", "이미지 저장 중 오류가 발생했습니다.");
			return "emp-form";
		}
	}

	@GetMapping("/admin/dashboard")
	public void getMethodName() {

	}
	
	 
}






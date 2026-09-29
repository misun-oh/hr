package com.example.hr.controller;

import com.example.hr.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.hr.dto.EmpDto;
import com.example.hr.dto.EmpSearchCond;
import com.example.hr.dto.PageDto;
import com.example.hr.service.EmpService;

import lombok.extern.slf4j.Slf4j;

// templates 	: 경로의 파일은 직접 호출 할 수 없고 컨트롤러를 통해서만 볼수 있다
// static 		: 요청하면 바로 서비스
@Controller
@Slf4j
public class EmpController {
        

	private final Config config;
	@Autowired
	private EmpService service;

	EmpController(Config config) {
		this.config = config;
	}

	// 여러개의 주소를 매핑
	@GetMapping({"/", "/emps"})
	public String getMethodName(Model model,
								@RequestParam(value = "page", required = false , defaultValue="1") int page, 
								@ModelAttribute EmpSearchCond cond) {
		
		
		System.out.println(cond.getKeyword());
		System.out.println(cond.getDeptId());
		System.out.println(cond.isWorkingOnly());
		System.out.println(cond.getSize());
		
		//model.addAttribute("totalCnt", service.totalCnt());
		service.selectByCond(model, cond);
		System.out.println("page : " + page);
		
		// 페이지 블럭을 만들기 위해서 pageDto객체를 생성하여 모델에 저장
		// 요청페이지 번호 : 요청정보로 부터 수집 (기본값 1) 
		// totalCnt : 데이터베이스의 총 건수를 조회
		// 검색시 게시물 목록과 페이징이 일치
		int totalCnt = service.totalCnt(cond);
		
		model.addAttribute("pageDto", new PageDto(page, totalCnt));	
		model.addAttribute("cond", cond);
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






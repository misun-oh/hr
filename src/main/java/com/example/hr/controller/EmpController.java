package com.example.hr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

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

	@Autowired
	private EmpService service;

	// 여러개의 주소를 매핑
	// 검색/페이징 조건은 GET 쿼리스트링(keyword, deptId, workingOnly, sort, page)으로 전달됨
	@GetMapping({"/", "/emps"})
	public String list(@ModelAttribute EmpSearchCond cond, Model model) {
		// selectByCond : 검색/페이징 조건(cond)에 맞는 사원 목록만 리턴
		// pageInfo    : 같은 조건의 검색결과 건수로 PageDto(페이지 번호/블럭/이전·다음버튼 등)를 계산해서 리턴
		List<EmpDto> list = service.selectByCond(cond);
		PageDto page = service.pageInfo(cond);

		model.addAttribute("list", list);
		model.addAttribute("totalCnt", service.totalCnt());
		model.addAttribute("filteredCnt", page.getTotalCnt());
		model.addAttribute("page", page);
		model.addAttribute("cond", cond);
		model.addAttribute("depts", service.selectDeptList());
		return "index";
	}

	/*
	 * 사원 상세화면
	 * 사번을 이용해서 사원정보를 조회 후 화면에 전달
	 */
	@GetMapping("/emp-detail")
	public String empDetail(@RequestParam(value = "empId", defaultValue = "") String empId, Model model) {
		// {}는 플레이스홀더(자리표시자) - 문자열 뒤에 오는 인자(empId)가 순서대로 그 자리에 채워짐
		// "empId : " + empId 처럼 문자열을 직접 합치지 않아도 돼서 더 효율적이고 안전함
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
	public String empSave(@ModelAttribute EmpDto emp, Model model) {
		// jobCode는 job 테이블 FK라, 값이 없는 채로 DB까지 가면 SQLIntegrityConstraintViolationException(500 에러)이 남
		// DB까지 가기 전에 미리 걸러서 폼 화면으로 되돌리고 에러 메시지를 보여줌
		if (!StringUtils.hasText(emp.getJobCode())) {
			model.addAttribute("emp", emp);
			model.addAttribute("errorMessage", "직급을 선택하세요");
			service.addFormOptions(model);
			return "emp-form";
		}

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

package com.example.hr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.hr.service.DeptService;
import com.example.hr.service.EmpService;

@Controller
public class DashboardController {

	@Autowired
	private EmpService empService;

	@Autowired
	private DeptService deptService;

	@GetMapping("/dashboard")
	public String dashboard(Model model) {
		int totalCnt = empService.totalCnt();
		int activeCnt = empService.activeCnt();

		model.addAttribute("totalCnt", totalCnt);
		model.addAttribute("activeCnt", activeCnt);
		model.addAttribute("inactiveCnt", totalCnt - activeCnt);
		model.addAttribute("avgSalary", empService.avgSalary());
		model.addAttribute("deptCnt", deptService.totalCnt());
		model.addAttribute("recentHires", empService.selectRecentHires(5));
		model.addAttribute("deptStats", deptService.selectAllWithStats());
		return "dashboard";
	}
}

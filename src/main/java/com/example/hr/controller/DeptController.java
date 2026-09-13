package com.example.hr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
	public String save(@ModelAttribute DeptDto dept) {
		service.register(dept);
		return "redirect:/depts";
	}
}

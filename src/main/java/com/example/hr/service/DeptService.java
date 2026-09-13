package com.example.hr.service;

import java.util.List;

import org.springframework.ui.Model;

import com.example.hr.dto.DeptDto;

public interface DeptService {
	void selectAll(Model model);

	List<DeptDto> selectAllWithStats();

	int totalCnt();

	void register(DeptDto dept);
}

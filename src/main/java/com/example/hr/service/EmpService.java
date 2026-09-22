package com.example.hr.service;

import org.springframework.ui.Model;

import com.example.hr.dto.EmpDto;
import com.example.hr.dto.EmpSearchCond;

public interface EmpService {
	int totalCnt();

	void selectByCond(EmpSearchCond cond, Model model);

	EmpDto selectById(String empId);
	
	EmpDto login(String id, String pw) throws Exception;
	
	int updateFailCount(String id);

	int resetFailCount(String id);
}

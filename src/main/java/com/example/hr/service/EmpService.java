package com.example.hr.service;

import org.springframework.ui.Model;

import com.example.hr.dto.EmpDto;
import com.example.hr.dto.EmpSearchCond;

public interface EmpService {
	int totalCnt(EmpSearchCond cond);
	
	void selectByCond(Model model, EmpSearchCond cond);
	
	EmpDto selectById(String empId);
	
	EmpDto login(String id, String pw) throws Exception;
	
	int updateFailCount(String id);

	int resetFailCount(String id);
}

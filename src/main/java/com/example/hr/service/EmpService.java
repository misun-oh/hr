package com.example.hr.service;

import java.io.IOException;
import java.util.List;

import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.example.hr.dto.EmpDto;
import com.example.hr.dto.EmpForm;
import com.example.hr.dto.EmpSearchCond;

public interface EmpService {
	int totalCnt();

	void selectByCond(EmpSearchCond cond, Model model);

	EmpDto selectById(String empId);

	EmpDto login(String id, String pw) throws Exception;

	int updateFailCount(String id);

	int resetFailCount(String id);

	void register(EmpForm form, List<MultipartFile> images) throws IOException;
}

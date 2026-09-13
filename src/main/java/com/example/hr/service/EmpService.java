package com.example.hr.service;

import java.util.List;

import org.springframework.ui.Model;

import com.example.hr.dto.CodeName;
import com.example.hr.dto.EmpDto;
import com.example.hr.dto.EmpSearchCond;
import com.example.hr.dto.PageDto;

public interface EmpService {
	int totalCnt();

	int activeCnt();

	Integer avgSalary();

	// 검색/페이징 조건에 맞는 목록만 리턴
	List<EmpDto> selectByCond(EmpSearchCond cond);

	// 같은 조건의 페이징 정보(전체/검색결과 건수, 전체 페이지수)만 리턴
	PageDto pageInfo(EmpSearchCond cond);

	List<EmpDto> selectRecentHires(int limit);

	EmpDto selectById(String empId);

	List<EmpDto> selectByManagerId(String managerId);

	// 등록/수정 폼에 필요한 부서·직급·관리자 목록을 model에 담는다
	void addFormOptions(Model model);

	String register(EmpDto emp);

	void modify(EmpDto emp);

	void remove(String empId);

	List<CodeName> selectDeptList();
}

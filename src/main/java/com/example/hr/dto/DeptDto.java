package com.example.hr.dto;

import lombok.Data;

@Data
public class DeptDto {
	private String deptId;
	private String deptTitle;
	private String locationId;

	// 조인/집계로 채워지는 읽기 전용 필드 (부서 목록 화면용)
	private String locationName;
	private int empCount;
	private Integer avgSalary;
}

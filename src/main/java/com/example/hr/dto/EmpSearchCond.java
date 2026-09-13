package com.example.hr.dto;

import lombok.Data;

// 사원 목록 검색조건 + 페이징/정렬 정보
@Data
public class EmpSearchCond {
	private String keyword;
	private String deptId;
	private boolean workingOnly;
	private String sort = "hireDate";
	private int page = 1;

	private static final int PAGE_SIZE = 10;

	public int getPageSize() {
		return PAGE_SIZE;
	}

	// LIMIT ... OFFSET ... 계산용
	public int getOffset() {
		int p = page < 1 ? 1 : page;
		return (p - 1) * PAGE_SIZE;
	}

	// 정렬 파라미터를 화이트리스트로 걸러서 안전한 ORDER BY 절만 허용 (${} 인젝션 방지)
	public String getOrderByClause() {
		if (sort == null) {
			return "e.hire_date desc";
		}
		return switch (sort) {
			case "name" -> "e.emp_name asc";
			case "salary" -> "e.salary desc";
			default -> "e.hire_date desc";
		};
	}
}

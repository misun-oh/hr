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
	// OFFSET은 "몇 번째 행이냐"가 아니라 "몇 개를 건너뛰냐(skip)"를 의미 (0부터 시작)
	//   ex) OFFSET 0  -> 0개 건너뛰고 시작 = 1~10번째
	//       OFFSET 10 -> 10개 건너뛰고 시작 = 11~20번째
	// page(1부터 시작)를 OFFSET(0부터 시작) 기준으로 바꾸기 위해 (page - 1) * PAGE_SIZE 계산
	// page가 0 이하로 들어오면 1페이지로 취급 (offset이 음수가 되는 걸 방지)
	public int getOffset() {
		int p = page < 1 ? 1 : page;
		return (p - 1) * PAGE_SIZE;
	}

	// 정렬 파라미터를 화이트리스트로 걸러서 안전한 ORDER BY 절만 허용 (${} 인젝션 방지)
	public String getOrderByClause() {
		if (sort == null) {
			return "e.hire_date desc";
		}
		// 스위치 표현식(switch expression, Java 14+) : switch문이 아니라 값을 바로 리턴하는 식(expression)
		// - "case 값 -> 결과값" 형태로 쓰면 break 없이도 자동으로 다음 case로 안 넘어감(fall-through 없음)
		// - switch 자체가 값을 만들어내므로 return switch(...) { ... }; 처럼 바로 리턴 가능
		// - default를 꼭 써줘야 함(모든 경우를 다루지 않으면 컴파일 에러)
		return switch (sort) {
			case "name" -> "e.emp_name asc";
			case "salary" -> "e.salary desc";
			default -> "e.hire_date desc";
		};
	}
}

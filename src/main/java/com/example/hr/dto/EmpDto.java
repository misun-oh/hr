package com.example.hr.dto;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.util.StringUtils;

import lombok.Data;

@Data
public class EmpDto {
	private String empId;
	private String empName;
	private String email;
	private String empNo;
	private String phone;
	private String deptId;
	private String jobCode;
	private int salary;
	private Double bonus;
	private String managerId;

	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate hireDate;

	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate entDate;

	// entYn : DB의 emp.ent_yn 컬럼과 매핑되는 퇴직여부 필드 (Y:퇴사, N:재직)
	// 기본값 "N" -> 신규 등록폼에서 별다른 값을 안 넣으면 "재직" 상태로 시작
	private String entYn = "N";
	// entYn=Y 이면 active = false
	// entYn=N 이면 active = true
	private boolean active = true;

	// 조인해서 채워지는 읽기 전용 필드 (목록/상세 화면용)
	private String deptName;
	private String jobName;
	private String managerName;
	private String locationName;
	private String salGrade;
	private Integer salGradeMin;
	private Integer salGradeMax;
	private Integer yearsOfService;

	public void setEntYn(String entYn) {
		this.entYn = entYn;
		// 삼항연산자를 이용해서 active값을 세팅
		active = entYn.equalsIgnoreCase("Y") ? false : true;
	}

	// 등록/수정 폼의 재직 여부 체크박스(active) 값을 entYn 컬럼값으로 변환
	public void setActive(boolean active) {
		this.active = active;
		this.entYn = active ? "N" : "Y";
	}

	// 폼에서 부서/관리자를 선택 안 하면 빈 문자열("")로 넘어오는데,
	// DB의 dept_id/manager_id는 FK라 "없음"은 반드시 null이어야 해서 여기서 정리
	//
	// StringUtils.hasText(str) : null도 아니고, 빈 문자열도 아니고, 공백(스페이스)만 있는 것도 아닐 때 true
	//   (= str != null && !str.isBlank() 와 같은 뜻의 스프링 유틸리티. hasText가 false면 값이 "없다"고 봄)
	// 참고) String.isBlank() (Java 11+) : 문자열이 비어있거나 공백문자로만 이루어져 있으면 true를 리턴하는 표준 메서드
	//   단, isBlank()는 대상이 null이면 NullPointerException이 나므로 null 체크를 직접 따로 해줘야 함
	//   (str == null || str.isBlank())  <-  hasText 없이 순수 자바로 쓰면 이렇게 작성
	public void setDeptId(String deptId) {
		this.deptId = StringUtils.hasText(deptId) ? deptId : null;
	}

	public void setManagerId(String managerId) {
		this.managerId = StringUtils.hasText(managerId) ? managerId : null;
	}
}

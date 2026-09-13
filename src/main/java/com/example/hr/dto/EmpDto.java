package com.example.hr.dto;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

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

	// entYn : 퇴직여부(Y:퇴사, N:재직)
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
}

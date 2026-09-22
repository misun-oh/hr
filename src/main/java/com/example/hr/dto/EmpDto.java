package com.example.hr.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class EmpDto {
	private int empId;
	private String empName;
	private String email;
	private String empNo;
	private LocalDate hireDate;
	// entYn : 퇴직여부(Y:퇴사, N:재직)
	private String entYn;
	// entYn=Y 이면 active = false
	// entYn=N 이면 active = true
	private boolean active;
	private String phone;
	private int salary;
	private float bonus;
	
	private String id;
	private String pw;
	// 계정 잠금 여부 (0:정상, 1:잠금)
	private int isLocked;
	private int loginFailCount;
	private String role;
	
	public void setEntYn(String entYn) {
		this.entYn = entYn;
		// 삼항연산자를 이용해서 active값을 세팅
		active = entYn.equalsIgnoreCase("Y") ? false : true;
	}	
	
	
	
	
	
}

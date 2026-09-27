package com.example.hr.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class EmpForm {
	private String empName;
	private String empNo;
	private String email;
	private String phone;
	private String deptId;
	private Integer salary;
	private LocalDate hireDate;
}

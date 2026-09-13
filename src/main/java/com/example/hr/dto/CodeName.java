package com.example.hr.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// 셀렉트박스용 코드-이름 쌍 (부서/직급/근무지 목록)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CodeName {
	private String code;
	private String name;
}

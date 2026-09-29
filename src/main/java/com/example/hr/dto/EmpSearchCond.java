package com.example.hr.dto;

import java.beans.ConstructorProperties;

import lombok.Data;

@Data
public class EmpSearchCond {
	
	/* 검색용 필드 추가 */
	private String keyword; // 검색어
	private String deptId;	// 부서아이디
	private boolean workingOnly;	// 재직여부 - checkbox : 체크되어 있을때만 값을 전달
    private String sort = "EMP_ID";

	/* 페이징 처리를 위한 필드 */
    private int page = 1;
    private int size = 0;
	private int offset = 10;	
	
	public EmpSearchCond(){
		
	}
	
	// 컨트롤러로 수집된 값을 생성자의 매개변수로 사용
	@ConstructorProperties({"page"})
	public EmpSearchCond(int page){
		this.offset=10;
		System.out.println("size 초기화 : " + size );		
		this.size=(page-1)*offset;
	}
	
	public int getSize() {
		return this.size=(page-1)*offset;
	}
}

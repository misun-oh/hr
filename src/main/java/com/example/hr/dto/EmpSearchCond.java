package com.example.hr.dto;

import lombok.Data;

/*
사원 목록 검색 조건 + 페이징 요청 정보를 함께 담는 DTO.
컨트롤러가 쿼리스트링(?keyword=&deptId=&workingOnly=&sort=&page=)을
@ModelAttribute 로 이 객체에 바인딩해서 서비스/매퍼로 그대로 넘긴다.
*/
@Data
public class EmpSearchCond {
    private String keyword;
    private String deptId;
    private boolean workingOnly;
    private EmpSort sort = EmpSort.EMP_ID;

    private int page = 1;   // 1부터 시작
    private int size = 10;  // 페이지당 게시물 수

    // LIMIT #{size} OFFSET #{offset} 에서 사용
    public int getOffset() {
        return (Math.max(page, 1) - 1) * size;
    }
}

package com.example.hr.dto;

/*
정렬 화이트리스트
ORDER BY 뒤에는 #{} 를 못쓰고 ${} 를 써야 하는데,
${} 에 사용자 입력을 그대로 넣으면 SQL 인젝션 위험이 있다.
그래서 사용 가능한 정렬 기준을 enum 으로 미리 정해두고,
화면에서는 이 enum 의 이름(EMP_ID, HIRE_DATE ...)만 파라미터로 받는다.
*/
public enum EmpSort {
    EMP_ID("emp_id", "asc"),
    HIRE_DATE("hire_date", "desc"),
    SALARY("salary", "desc"),
    NAME("emp_name", "asc");

    private final String column;
    private final String direction;

    EmpSort(String column, String direction) {
        this.column = column;
        this.direction = direction;
    }

    public String getColumn() { return column; }
    public String getDirection() { return direction; }
}

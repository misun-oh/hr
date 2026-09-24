package com.example.hr.dto;

/*
 * enum(enumeration)이란 정해진 값만 사용할 수 있도록 만든 자료형이다.
 *
 * 일반적인 String은 "emp_id", "salary", "아무 문자열"처럼 어떤 값이든
 * 담을 수 있지만, enum은 아래에 선언한 EMP_ID, HIRE_DATE, SALARY, NAME
 * 중 하나만 값으로 사용할 수 있다.
 *
 * 이 enum은 사원 목록의 정렬 기준을 미리 정해 둔 화이트리스트다.
 * ORDER BY 뒤에는 #{sort}를 사용할 수 없어서 ${sort.column}처럼 SQL에
 * 값을 직접 삽입해야 한다. 사용자 입력을 그대로 사용하면 SQL 인젝션
 * 위험이 있으므로, 화면에서는 enum 이름만 전달하고 실제 컬럼명과
 * 정렬 방향은 이 enum에서 결정한다.
 */
public enum EmpSort {
	// enum 상수마다 실제 데이터베이스 컬럼명과 기본 정렬 방향을 함께 보관한다.
    EMP_ID("emp_id", "asc"),
    HIRE_DATE("hire_date", "desc"),
    SALARY("salary", "desc"),
    NAME("emp_name", "asc");

	// MyBatis ORDER BY 절에 사용할 실제 컬럼명
    private final String column;
	// MyBatis ORDER BY 절에 사용할 정렬 방향
    private final String direction;

    /*
     * enum 상수를 생성할 때 호출되는 생성자다.
     *
     * 예를 들어 EMP_ID("emp_id", "asc")가 선언되면 Java가 내부적으로
     * EmpSort("emp_id", "asc") 생성자를 호출해서 column과 direction에
     * 값을 저장한다.
     *
     * enum은 일반 클래스처럼 new EmpSort(...)를 직접 호출해서 만들 수 없고,
     * EMP_ID, HIRE_DATE 같은 상수를 선언할 때 Java가 자동으로 생성한다.
     */
    EmpSort(String column, String direction) {
        this.column = column;
        this.direction = direction;
    }

	// MyBatis XML에서 ${sort.column}으로 호출된다.
    public String getColumn() { return column; }
	// MyBatis XML에서 ${sort.direction}으로 호출된다.
    public String getDirection() { return direction; }
}

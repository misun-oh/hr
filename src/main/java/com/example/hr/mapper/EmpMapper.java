package com.example.hr.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.example.hr.dto.CodeName;
import com.example.hr.dto.EmpDto;
import com.example.hr.dto.EmpSearchCond;

/*
 * 1. 쿼리는 잘 실행되는지 확인을 위해 클라이언트도구(mysql 워크벤치)에서 확인!!!
 * 2. ;은 제거 해야함!
 * 3. 테스트 진행
 */
@Mapper
public interface EmpMapper {
	// 전체 사원의 수를 카운트 (검색조건 없음, 대시보드용)
	@Select("select count(*) from emp")
	public int totalCnt();

	// 재직중인 사원 수
	@Select("select count(*) from emp where ent_yn = 'N'")
	public int activeCnt();

	// 재직중인 사원의 평균 급여
	@Select("select round(avg(salary)) from emp where ent_yn = 'N'")
	public Integer avgSalary();

	// 검색조건에 맞는 목록 (페이징 포함)
	public List<EmpDto> selectByCond(EmpSearchCond cond);

	// 검색조건에 맞는 전체 건수 (페이징 계산용)
	public int countByCond(EmpSearchCond cond);

	// 최근 입사자
	public List<EmpDto> selectRecentHires(int limit);

	// 상세조회 (부서명/직급명/관리자명/근무지/급여등급 조인)
	public EmpDto selectById(String id);

	// 부하 직원 목록 (조직 탭)
	public List<EmpDto> selectByManagerId(String managerId);

	// 사번 채번을 위한 현재 최대 사번
	@Select("select max(emp_id) from emp")
	public String selectMaxEmpId();

	public void insert(EmpDto emp);

	public void update(EmpDto emp);

	public void deleteById(String id);

	// 등록/수정 폼, 검색 필터의 부서 셀렉트박스용
	@Select("select dept_id as code, dept_title as name from dept order by dept_id")
	public List<CodeName> selectDeptList();

	// 등록/수정 폼의 직급 셀렉트박스용
	@Select("select job_code as code, job_name as name from job order by job_code")
	public List<CodeName> selectJobList();

	// 등록/수정 폼의 관리자 셀렉트박스용 (재직중인 사원만)
	@Select("select emp_id as code, emp_name as name from emp where ent_yn = 'N' order by emp_id")
	public List<CodeName> selectManagerList();
}

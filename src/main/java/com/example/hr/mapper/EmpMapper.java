package com.example.hr.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.hr.dto.EmpDto;
import com.example.hr.dto.EmpSearchCond;

/*
 * 1. 쿼리는 잘 실행되는지 확인을 위해 클라이언트도구(mysql 워크벤치)에서 확인!!!
 * 2. ;은 제거 해야함!
 * 3. 테스트 진행
 */
@Mapper
public interface EmpMapper {
	// 전체 사원의 수를 카운트
	@Select("select count(*) from emp")
	public int totalCnt();

	// 검색조건(cond)에 맞는 현재 페이지 목록
	public List<EmpDto> selectByCond(EmpSearchCond cond);

	// 검색조건(cond)에 맞는 전체 건수 (페이징 계산용)
	public int countByCond(EmpSearchCond cond);

	@Select("select * from emp where emp_id=#{id}")
	public EmpDto selectById(String id);
	
	@Select("select * from emp where id=#{id}")
	public EmpDto selectByUserId(String id);
	
	
	public int updateFailCount(String id);
	
	@Update("update emp set is_locked=1 where id=#{id}")
	public int lockUserAccount(String id);
	
	@Update("update emp set login_fail_count=0, last_login_at=now() where id=#{id}")
	public int resetFailCount(String id);
	
	
	// 비밀번호 업데이트
	// 여러개의 값을 받아올때 어노테이션으로 이름을 명시
	@Update("update emp set pw=#{pw} where id=#{id}")
	public int updatePW(@Param("id") String id, @Param("pw") String pw);
	// 객체로 받아서 사용
	//public int updatePW(EmpDto emp);
	
	
	
	
	
}

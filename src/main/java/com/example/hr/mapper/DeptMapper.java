package com.example.hr.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.example.hr.dto.CodeName;
import com.example.hr.dto.DeptDto;

@Mapper
public interface DeptMapper {

	// 부서 목록 + 부서별 재직 인원수/평균급여 (집계)
	public List<DeptDto> selectAllWithStats();

	@Select("select count(*) from dept")
	public int totalCnt();

	public void insert(DeptDto dept);

	// 부서 추가 모달의 근무지 셀렉트박스용
	@Select("select l.local_code as code, n.national_name as name "
			+ "from location l join national n on l.national_code = n.national_code "
			+ "order by l.local_code")
	public List<CodeName> selectLocationList();
}

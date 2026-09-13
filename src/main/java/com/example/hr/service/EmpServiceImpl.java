package com.example.hr.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.example.hr.dto.CodeName;
import com.example.hr.dto.EmpDto;
import com.example.hr.dto.EmpSearchCond;
import com.example.hr.dto.PageDto;
import com.example.hr.mapper.EmpMapper;

/*
 * 인터페이스의 구현체
 */
// @Service : 이 클래스를 스프링 빈으로 등록 (컨테이너가 앱 시작 시 싱글톤으로 딱 1개 생성해서 관리)
// 빈으로 등록해서 쓰는 이유
//   1. 상태가 없는(stateless) 컴포넌트라 여러 요청이 동시에 써도 안전함 (필드는 mapper 하나뿐이고 읽기 전용)
//      -> PageDto처럼 요청마다 값이 달라지는 객체와 반대로, 인스턴스를 공유해도 문제가 없음
//   2. 컨트롤러 등 다른 곳에서 매번 new EmpServiceImpl(mapper)로 직접 만들 필요 없이
//      스프링이 알아서 만들어서 필요한 곳(@Autowired)에 주입해줌 -> 객체 생성/의존관계 관리를 스프링에 위임
//   3. 트랜잭션(@Transactional), AOP, 예외변환(DataAccessException 변환) 같은
//      스프링의 부가기능을 이 빈 위에 자동으로 적용받을 수 있음
//
// 쉽게 말하면
//   - EmpServiceImpl : 하나만 만들어놓고 모두가 같이 써도 되는 "공용 기능 모음집" (계산기 같은 도구) -> 빈으로 등록
//   - PageDto        : 쓸 때마다 값이 달라지는 "결과물" (계산기로 계산한 답) -> 매번 새로 new해서 사용
@Service
public class EmpServiceImpl implements EmpService {

	// @Autowired : 필드 주입
	// 리플렉션으로 필드에 직접 주입

	// DI
	// 1. 필드 주입
	// 2. Setter 주입
	// 3. 생성자 주입
	// @Autowired
	private final EmpMapper mapper;

	public EmpServiceImpl(EmpMapper mapper) {
		this.mapper = mapper;
	}

	@Override
	public int totalCnt() {
		return mapper.totalCnt();
	}

	@Override
	public int activeCnt() {
		return mapper.activeCnt();
	}

	@Override
	public Integer avgSalary() {
		return mapper.avgSalary();
	}

	@Override
	public List<EmpDto> selectByCond(EmpSearchCond cond) {
		return mapper.selectByCond(cond);
	}

	@Override
	public PageDto pageInfo(EmpSearchCond cond) {
		int filteredCnt = mapper.countByCond(cond);
		// PageDto는 스프링 빈이 아니라 매번 값이 달라지는 단순 값 객체(VO)라서 DI 없이 new로 직접 생성
		// 생성자(page, totalCnt)만 넘기면 totalPage/startPage/endPage/prev/next는 생성자 안에서 자동 계산됨
		return new PageDto(cond.getPage(), filteredCnt);
	}

	@Override
	public List<EmpDto> selectRecentHires(int limit) {
		return mapper.selectRecentHires(limit);
	}

	@Override
	public EmpDto selectById(String empId) {
		return mapper.selectById(empId);
	}

	@Override
	public List<EmpDto> selectByManagerId(String managerId) {
		return mapper.selectByManagerId(managerId);
	}

	@Override
	public void addFormOptions(Model model) {
		model.addAttribute("depts", mapper.selectDeptList());
		model.addAttribute("jobs", mapper.selectJobList());
		model.addAttribute("managers", mapper.selectManagerList());
	}

	@Override
	public String register(EmpDto emp) {
		// deptId/managerId 빈문자열 -> null 정리는 EmpDto.setDeptId/setManagerId에서 처리하므로
		// 여기서 별도로 normalize() 호출 안 해도 됨
		emp.setEmpId(nextEmpId());
		mapper.insert(emp);
		return emp.getEmpId();
	}

	@Override
	public void modify(EmpDto emp) {
		mapper.update(emp);
	}

	@Override
	public void remove(String empId) {
		mapper.deleteById(empId);
	}

	@Override
	public List<CodeName> selectDeptList() {
		return mapper.selectDeptList();
	}

	// EMP_ID가 VARCHAR(3)라 자동증가가 아니라 현재 최댓값 다음 번호로 채번
	private String nextEmpId() {
		String maxId = mapper.selectMaxEmpId();
		int next = (maxId == null) ? 1 : Integer.parseInt(maxId) + 1;
		return String.format("%03d", next);
	}
}

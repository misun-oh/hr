package com.example.hr.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;

import com.example.hr.dto.CodeName;
import com.example.hr.dto.EmpDto;
import com.example.hr.dto.EmpSearchCond;
import com.example.hr.mapper.EmpMapper;

/*
 * 인터페이스의 구현체
 */
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
	public void selectByCond(EmpSearchCond cond, Model model) {
		List<EmpDto> list = mapper.selectByCond(cond);
		int filteredCnt = mapper.countByCond(cond);
		int totalPages = (int) Math.ceil(filteredCnt / (double) cond.getPageSize());

		model.addAttribute("list", list);
		model.addAttribute("totalCnt", mapper.totalCnt());
		model.addAttribute("filteredCnt", filteredCnt);
		model.addAttribute("totalPages", Math.max(totalPages, 1));
		model.addAttribute("cond", cond);
		model.addAttribute("depts", mapper.selectDeptList());
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
		normalize(emp);
		emp.setEmpId(nextEmpId());
		mapper.insert(emp);
		return emp.getEmpId();
	}

	@Override
	public void modify(EmpDto emp) {
		normalize(emp);
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

	// 폼에서 선택 안 한 부서/관리자는 빈 문자열로 넘어오므로 null로 정리
	private void normalize(EmpDto emp) {
		if (!StringUtils.hasText(emp.getDeptId())) {
			emp.setDeptId(null);
		}
		if (!StringUtils.hasText(emp.getManagerId())) {
			emp.setManagerId(null);
		}
	}

	// EMP_ID가 VARCHAR(3)라 자동증가가 아니라 현재 최댓값 다음 번호로 채번
	private String nextEmpId() {
		String maxId = mapper.selectMaxEmpId();
		int next = (maxId == null) ? 1 : Integer.parseInt(maxId) + 1;
		return String.format("%03d", next);
	}
}

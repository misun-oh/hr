package com.example.hr.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.example.hr.dto.DeptDto;
import com.example.hr.mapper.DeptMapper;

@Service
public class DeptServiceImpl implements DeptService {

	private final DeptMapper mapper;

	public DeptServiceImpl(DeptMapper mapper) {
		this.mapper = mapper;
	}

	@Override
	public void selectAll(Model model) {
		model.addAttribute("list", mapper.selectAllWithStats());
		model.addAttribute("totalCnt", mapper.totalCnt());
		model.addAttribute("locations", mapper.selectLocationList());
	}

	@Override
	public List<DeptDto> selectAllWithStats() {
		return mapper.selectAllWithStats();
	}

	@Override
	public int totalCnt() {
		return mapper.totalCnt();
	}

	@Override
	public void register(DeptDto dept) {
		// dept.location_id는 DB에서 NOT NULL(+FK) 컬럼이라 null로 저장할 수 없음
		// (예전엔 비어있으면 null로 바꿔서 저장했는데, 그러면 항상 SQLIntegrityConstraintViolationException 발생)
		// 비어있는지 여부는 DeptController.save()에서 미리 걸러서 여기까지 안 넘어오게 함
		mapper.insert(dept);
	}
}

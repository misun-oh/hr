package com.example.hr.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;

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
		if (!StringUtils.hasText(dept.getLocationId())) {
			dept.setLocationId(null);
		}
		mapper.insert(dept);
	}
}

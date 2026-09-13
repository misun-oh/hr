package com.example.hr.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import com.example.hr.dto.EmpDto;
import com.example.hr.dto.EmpSearchCond;
import com.example.hr.dto.PageDto;

@SpringBootTest
public class EmpServiceTest {

	@Autowired
	EmpService service;

	@Test
	public void test() {
		int totalCnt = service.totalCnt();
		System.out.println(totalCnt);
		assertEquals(21, totalCnt);
	}

	@Test
	public void selectByCond() {
		List<EmpDto> list = service.selectByCond(new EmpSearchCond());
		System.out.println(list);
	}

	@Test
	public void pageInfo() {
		PageDto page = service.pageInfo(new EmpSearchCond());
		System.out.println(page);
	}
}

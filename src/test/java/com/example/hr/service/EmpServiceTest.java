package com.example.hr.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import com.example.hr.dto.EmpSearchCond;

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
		Model model = new ExtendedModelMap();
		service.selectByCond(new EmpSearchCond(), model);
		System.out.println(model.getAttribute("list"));
	}
}

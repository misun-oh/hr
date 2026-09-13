package com.example.hr.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.hr.dto.EmpDto;
import com.example.hr.dto.EmpSearchCond;

/*
 * DI(의존성주입)
 * 객체를 직접 생성하지 않고 프레임 워크로 부터 주입받아서 사용
 */
@SpringBootTest
public class EmpMapperTest {

	@Autowired
	EmpMapper mapper;

	@Test
	public void test1() {
		int totalCnt = mapper.totalCnt();
		System.out.println(totalCnt);

		// 검증
		assertEquals(21, totalCnt);
	}

	@Test
	public void selectByCond() {
		EmpSearchCond cond = new EmpSearchCond();
		List<EmpDto> list = mapper.selectByCond(cond);
		System.out.println(list);
	}

	@Test
	public void selectById() {
		EmpDto emp = mapper.selectById("id");
		System.out.println(emp);

		assertNull(emp);
	}
}

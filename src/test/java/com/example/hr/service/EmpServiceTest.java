package com.example.hr.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import com.example.hr.dto.EmpDto;
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
	}
	
	@Test
	public void login_없는ID() {
		try {
			service.login("id123", "pw123");
		} catch (Exception e) {
			// TODO 사용자정의예외처리로 변경하기
			String msg = e.getMessage();
			System.out.println(msg);
		}
	}

	@Test
	public void login_계정잠김() {
		// 잠긴계정을 이용해서 테스트
		try {
			service.login("lock", "pw123");
		} catch (Exception e) {
			// TODO 사용자정의예외처리로 변경하기
			String msg = e.getMessage();
			System.out.println(msg);
		}
	}
	

	@Test
	public void login_비밀번호_불일치() {
		// 잠긴계정을 이용해서 테스트
		try {
			service.login("201", "pw123");
		} catch (Exception e) {
			// TODO 사용자정의예외처리로 변경하기
			String msg = e.getMessage();
			System.out.println(msg);
		}
	}
	
	
	@Test
	public void login_5회_잠김() {
		// 잠긴계정을 이용해서 테스트
		try {
			service.login("201", "pw123");
		} catch (Exception e) {
			// TODO 사용자정의예외처리로 변경하기
			String msg = e.getMessage();
			System.out.println(msg);
		}
	}
	
	@Test
	public void login_정상처리() {
		// 잠긴계정을 이용해서 테스트
		try {
			EmpDto emp = service.login("200", "1234");
			System.out.println(emp.getEmpName() + "님 환영 합니다.");
			
		} catch (Exception e) {
			// TODO 사용자정의예외처리로 변경하기
			String msg = e.getMessage();
			System.out.println(msg);
		}
	}
}

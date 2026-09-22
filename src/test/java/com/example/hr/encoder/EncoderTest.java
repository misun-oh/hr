package com.example.hr.encoder;



import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.hr.dto.EmpDto;
import com.example.hr.mapper.EmpMapper;

@SpringBootTest
// 설정파일의 위치를 등록
//@SpringJUnitConfig(classes = Config.class)
public class EncoderTest {
	
	@Autowired
	BCryptPasswordEncoder encoder;
	
	@Autowired
	EmpMapper mapper;
	
	// 모든사용자 조회 후 반복문을 통해서 일괄적으로 비밀번호 업데이트 
	// 만약 비밀번호 비어있지 않으면 비밀번호를 암호화 해서 저장
	// 비어있다면 1234를 암호화 해서 저장
	// ---> DB 프로시저를 작성 
	@Test
	public void updatePWAll() {
		// 1. 사용자목록 조회
		List<EmpDto> list = mapper.selectByCond();
		
		// 2. 반복문 
		for(EmpDto emp : list) {
			System.out.println("========================");
			System.out.println( emp.getId() );
			System.out.println( emp.getPw() );
			
			String encodePW = encoder.encode("1234");
			
			
			// 	2-1. 업데이트 문장 실행
			int res = mapper.updatePW(emp.getId(), encodePW);
			System.out.println("========================");
		}
	}
	
	@Test
	public void loginTest() {
		System.out.println(encoder+"===================");
		// 사용자 정보 조회
		EmpDto emp = mapper.selectByUserId("200");

		System.out.println("emp : " + emp);
		// 비밀번호 검증
		boolean res = encoder.matches("1234", emp.getPw());
		if(res) {
			System.out.println("로그인 성공");
		} else {
			System.out.println("로그인 실패");
		}
		
		
	}
	
	@Test
	public void updatePW() {
		System.out.println("mapper : " + mapper); 
//		System.out.println("encoder" + encoder);
//		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String encodePW = encoder.encode("ABCD1234");
		System.out.println("encodePW : " + encodePW);
		int res = mapper.updatePW("200", encodePW);
		System.out.println(res);
		
		// 업데이트문 실행후 워크벤치에서 commit/rollback을 안한경우 락걸려서 무한대기
		// commit/rollback 실행 할때까지 락걸림.....
	}
	
	@Test
	public void encoderTest() {
		//BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		// 평문을 암호화 하여 반환
		String hashd = encoder.encode("1234");
		System.out.println(hashd);
		
		// 비밀번호 검증
		boolean res = encoder.matches("1234", hashd);
		System.out.println(res);
		
		boolean res1 = encoder.matches("12345", hashd);
		System.out.println(res1);
	}
	
	
	
	
}

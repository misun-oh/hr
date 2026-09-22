package com.example.hr.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.hr.dto.EmpDto;
import com.example.hr.service.EmpService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class LoginController {
	
	// @RequiredArgsConstructor를 이용한 생성자 주입
	private final EmpService service;
	
	@GetMapping("/login")
	public void login() {
		
	}
	
	// /login -> post방식으로 요청
	// id,pw수집
	// 로그인 -> 회원목록페이지로 이동
	@PostMapping("/login")
	public String loginAction(@RequestParam(name = "id") String id,
								@RequestParam(name = "pw") String pw,
								HttpSession session,
								Model model) {
		
		
		System.out.println("id : " + id);
		System.out.println("pw : " + pw);
		
		/*  1. 아이디 비밀번호 검증
		 		입력된 아이디로 사용자 조회
		 		
		 		비밀번호가 일치하면 로그인 성공 -> 세션에 사용자 정보 저장 -> 사원목록
		 	
		 	    비밀번호가 불일치시 로그인 실패  
		 			-> login_fail_count 업데이트 (5회 초과 실패시 계정 잠금)
		 			-> 메세지 처리
		 			-> 뒤로가기
		*/ 
		try {
			EmpDto emp = service.login(id, pw);
			// 인증된 사용자의 정보를 세션영역에 저장
			session.setAttribute("user", emp);
			int res = service.resetFailCount(id);
			System.out.println("res : " + res);
			
			// 이전 요청정보가 있는경우 요청페이지로 이동
			if(session.getAttribute("prevRequestUrl") != null) {
				String prev = (String)session.getAttribute("prevRequestUrl");
				session.removeAttribute("prevRequestUrl");
				return "redirect:" + prev; 
			}
			
			// 사원 목록 페이지로 이동하기
			return "redirect:emps";
			
			
			
		} catch (Exception e) {
			// failCnt 업데이트
			//e.printStackTrace();
			// 로그인 실패 카운팅, 5회 실패시 계정 잠금
			int res = service.updateFailCount(id);
			System.out.println("res : " + res);
			// 오류 메세지 내용을 화면에 전달 
			model.addAttribute("error", e.getMessage());
			return "login";
		}
		
	}
	
	
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {

		// 세션초기화
		session.invalidate();
		
		return "/login";
	}
	
	
	
	
	
	
	
	
	
}

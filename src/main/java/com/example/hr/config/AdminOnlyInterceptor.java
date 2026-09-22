package com.example.hr.config;

import org.springframework.web.servlet.HandlerInterceptor;

import com.example.hr.dto.EmpDto;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AdminOnlyInterceptor implements HandlerInterceptor{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.out.println("AdminOnlyInterceptor");
		// 로그인한 사용자의 권한 체크
		// /dashboard 요청은 관리자만 할수 있다!
		
		// 세션에 로그인한 사용자 정보가 있는지 확인
		// 권한정보를 조회
		EmpDto emp = request.getSession(false) != null 
						? (EmpDto)request.getSession(false).getAttribute("user") 
								: null;
		System.out.println(emp + "=================");
		if(emp.getRole().equals("ADMIN")) {
			return true;
		} else {
			response.sendError(HttpServletResponse.SC_FORBIDDEN
								, "관리자만 접근 할 수 있습니다.");
			return false;
		}
		
	}
}

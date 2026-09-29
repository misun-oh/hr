package com.example.hr.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer{
	

	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		
		// LoginCheckInterceptor (로그인 체크) 
		// 로그인이 되어 있지 않으면 로그인 페이지로 리다이렉트
		// 리다이렉트 타겟경로를 제외하지 않으면 무한반복에 빠질수 있다..(리다이렉트 횟수가 너무 많습니다)
		registry.addInterceptor(new LoginCheckInterceptor())
					.excludePathPatterns("/css/**", "/js/**", "/login", "/logout");
		
		// 관리자권한 체크
		registry.addInterceptor(new AdminOnlyInterceptor())
					.addPathPatterns("/admin/**");
	}
	
	
}

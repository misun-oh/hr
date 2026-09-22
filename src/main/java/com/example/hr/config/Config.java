package com.example.hr.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class Config implements WebMvcConfigurer{
	
	// 외부라이브러리에서 제공하는 객체를 Bean으로 등록
	// 스프링 컨터이너에 미리 생성해 두었다가 필요한 시점에 주입 받아서 사용 
	@Bean
	public BCryptPasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		
		// LoginCheckInterceptor (로그인 체크) 
		// 로그인이 되어 있지 않으면 로그인 페이지로 리다이렉트
		// 리다이렉트 타겟경로를 제외하지 않으면 무한반복에 빠질수 있다..(리다이렉트 횟수가 너무 많습니다)
		registry.addInterceptor(new LoginCheckInterceptor())
					.excludePathPatterns("/css/**", "/js/**", "/login", "/logout");
		
		registry.addInterceptor(new AdminOnlyInterceptor())
					.addPathPatterns("/dashboard");
	}
	
	
}

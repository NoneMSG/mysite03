package com.jx372.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class AuthInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(
			HttpServletRequest request, 
			HttpServletResponse response, 
			Object handler)
			throws Exception {
		//1. handler 종류
		if(handler instanceof HandlerMethod==false){
			return false;
		}
		
		//2. @Auth가 붙어 있는지 확인
		Auth auth= ((HandlerMethod)handler).getMethodAnnotation(Auth.class);

		//3. @Auth가 붙어 있지 않으면
		if(auth == null){
			
			return true;
		}
		
		//4.접근제어
		HttpSession session = request.getSession();
		if(session ==null){
			response.sendRedirect(request.getContextPath()+"/user/login");
			return false;
		}
		
		if(session.getAttribute("authUser") == null){
			response.sendRedirect(request.getContextPath()+"/user/login");
			return false;
		}
		
		//5.인증된 사용자
		return true;
	}

}

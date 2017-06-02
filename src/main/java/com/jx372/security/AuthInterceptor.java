package com.jx372.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.jx372.mysite.vo.UserVo;

public class AuthInterceptor extends HandlerInterceptorAdapter {

	//return값이 false면 메서드로 접근을 안 시키고 true면 접근이 가능하도록 한다.
	@Override
	public boolean preHandle(
			HttpServletRequest request, 
			HttpServletResponse response, 
			Object handler)
			throws Exception {
		//1. handler 종류
		if(handler instanceof HandlerMethod==false){
			return true;
		}
		
		//2. Method에 @Auth가 붙어 있는지 확인
		Auth auth= ((HandlerMethod)handler).getMethodAnnotation(Auth.class);

		//3. @Auth가 붙어 있지 않으면
		if(auth == null){
			//4. class에 붙어 있는지 확인
			auth = ((HandlerMethod)handler).
					getMethod().
					getDeclaringClass().
					getAnnotation(Auth.class);
			if(auth==null){
				return true;
			}			
		}
				
		//5.접근제어 @Auth가 붙어있는경우 
		HttpSession session = request.getSession();
		if(session ==null){
			response.sendRedirect(request.getContextPath()+"/user/login");
			return false;
		}
		
		if(session.getAttribute("authUser") == null){
			response.sendRedirect(request.getContextPath()+"/user/login");
			return false;
		}
		
		//6.인증된 사용자 Role체크
		//admin이 아니면 모두 빼준다
		Auth.Role role =auth.value();
		UserVo authUser = (UserVo)session.getAttribute("authUser");
//		System.out.println(authUser.getRole());
//		System.out.println(role.toString());
		if(role==Auth.Role.ADMIN && authUser.getRole().equals("ADMIN")==false){
			return false;
		}
		return true;
	}

}

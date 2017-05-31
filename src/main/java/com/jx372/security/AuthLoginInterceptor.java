package com.jx372.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.jx372.mysite.service.UserService;
import com.jx372.mysite.vo.UserVo;

public class AuthLoginInterceptor extends HandlerInterceptorAdapter{
	
	//컨테이너에서 관리하는 객체에 의해서 userService를 사용 가능 자동 주입해줌 근데 web application context로 가서 저장이됨
	@Autowired
	private UserService userService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, 
			HttpServletResponse response, 
			Object handler)
			throws Exception {

//			//컨테이너를 얻어올 수 있다.
//			ApplicationContext ac = 
//					WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
//			//Web contianer에서 userService를 가리키고 있는 객체를 가져옴
//			UserService userService = ac.getBean(UserService.class);
			

			String email =request.getParameter("email");
			String password = request.getParameter("password");
			
			UserVo userVo = userService.getUser(email, password);
			if(userVo==null){
				response.sendRedirect(request.getContextPath()+"/user/login?result=fail");
				return false;
			}
			
			//login성공 처리
			HttpSession session  = request.getSession(true);
			session.setAttribute("authUser", userVo);
			response.sendRedirect(request.getContextPath()+"/main");
			return false;
	}

}

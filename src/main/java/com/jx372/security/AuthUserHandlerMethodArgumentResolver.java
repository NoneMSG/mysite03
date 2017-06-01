package com.jx372.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.jx372.mysite.vo.UserVo;

public class AuthUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

	@Override
	public Object resolveArgument(
			MethodParameter parameter, 
			ModelAndViewContainer mavContainer, 
			NativeWebRequest webRequest,
			WebDataBinderFactory binderFactory) throws Exception {
		
			if(supportsParameter(parameter)==false){
				//null을 return 하면 관심있는 parameter도 null이 될 수 있기 때문에 pass시킨다.
				return WebArgumentResolver.UNRESOLVED; 
			}
			
			//@AuthUser 가 parmeter에 등록이 되어 있고 파라미터 타입이 UserVo일 때 
			HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class); //was마다 클래스이름이 다를 수 있기 때문에
			HttpSession session = request.getSession();
			if(session == null){
				//지원되는 paramter인데 타입이 안 맞으면 null 해도 된다.
				return null;
			}
			
		return session.getAttribute("authUser");
	}

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		AuthUser authUser = parameter.getParameterAnnotation( AuthUser.class);
		
		//@AuthUser가 없을 때
		if(authUser==null){
			return false;
		}
		
		//parameter타입이 UserVo가 아닐 때
		if(parameter.getParameterType().equals(UserVo.class) == false){
			return false;
		}
		//검증이 되었으면 허가
		return true;
	}

}

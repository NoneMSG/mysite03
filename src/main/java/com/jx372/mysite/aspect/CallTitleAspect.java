package com.jx372.mysite.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CallTitleAspect {
	
//	@Around( "execution(* *..controller.*.*(..) )")
//	public Object aroundAspect(ProceedingJoinPoint pjp) throws Throwable{
//		return null;
//	}
}

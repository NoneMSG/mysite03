package com.jx372.mysite.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
public class MeasureExecutionTimeAspect {
	  //             범위  패키지경로.모든 클래스.모든함수(모든파라미터)
	@Around( "execution(* *..repository.*.*(..) ) || execution(* *..service.*.*(..) ) ")
	public Object aroundAdvice(ProceedingJoinPoint pjp) throws Throwable{
		//before
		StopWatch sw = new StopWatch();
		sw.start();
		
		//method 실행
		Object result = pjp.proceed();
		
		//after
		sw.stop();
		Long totalTime = sw.getTotalTimeMillis();
		String className = pjp.getTarget().getClass().getName();
		String methodName = pjp.getSignature().getName();
		String taskName = className+"."+methodName;
		
		System.out.println("[ExecutionTime]["+taskName+"]"+totalTime+"mills");
		
		return result;
	}
}

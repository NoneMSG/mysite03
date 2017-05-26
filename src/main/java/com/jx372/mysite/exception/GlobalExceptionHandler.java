package com.jx372.mysite.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(Exception.class )
	public ModelAndView handleException(Exception e){
		//1.로깅
		StringWriter errors = new StringWriter();
		e.printStackTrace(new PrintWriter(errors));
		
		//2.안내페이지가기
		ModelAndView mav = new ModelAndView();
		mav.setViewName("error/exception");
		mav.addObject("exception", errors.toString());
		return mav;
	}
}

package com.jx372.mysite.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class DomainController {
	
	
	@RequestMapping({"/", "/main"})
	public String index(){
		//view resolver bean을 설정해주었기 때문에 URL을 간략하게 사용할 수 있다.

		return "main/index";
	}
	
	
	//ResponseBody 어노테이션이 없다면 문자열로 return을 했을때 변환이 안되어서 에러가 나타남
//	@RequestMapping("/hello")
//	public String hello(){
//		return "hello world"; 
//	}
	
	//responsebody 어노테이션으로 메시지컨터버테의해서 데이터가 반환되어 브라우저에 뿌려진다.
	@ResponseBody
	@RequestMapping("/hello")
	public String hello(){
		return "<h1>hello world</h1>"; 
	}
}

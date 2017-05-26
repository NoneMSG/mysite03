package com.jx372.mysite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DomainController {
	
	@RequestMapping({"/", "/main"})
	public String index(){
		//view resolver bean을 설정해주었기 때문에 URL을 간략하게 사용할 수 있다.
		return "main/index";
	}
}

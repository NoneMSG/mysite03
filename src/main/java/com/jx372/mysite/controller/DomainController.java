package com.jx372.mysite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jx372.mysite.service.AdminService;
import com.jx372.mysite.vo.AdminVo;

@Controller
public class DomainController {
	
	@Autowired
	private AdminService adminService;
	
	@RequestMapping({"/", "/main"})
	public String index(Model model){
		//view resolver bean을 설정해주었기 때문에 URL을 간략하게 사용할 수 있다.
		AdminVo adminVo = adminService.getInfo();
		model.addAttribute("mainInfo", adminVo);
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

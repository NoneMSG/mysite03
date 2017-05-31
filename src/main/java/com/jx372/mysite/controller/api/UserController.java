package com.jx372.mysite.controller.api;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jx372.mysite.service.UserService;

@Controller("userApiController")
@RequestMapping("/user/api")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@ResponseBody
	@RequestMapping("/checkemail")
	public Map<String, Object> checkEmail(
			@RequestParam(value="email", required=true, defaultValue="")String email){
		
		boolean exist = userService.existEmail(email);
		
		//map을 이용해서 json형식처럼 키값에 값을 매핑 
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", "success");
		map.put("data", exist);
		//map.put("email", email); 
		return map;//브라우저가 반환된 값을 읽으면 그냥 html의 string이지만 javascript로 읽으면 다르다.
	}
}

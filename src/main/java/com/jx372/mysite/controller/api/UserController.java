package com.jx372.mysite.controller.api;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jx372.mysite.service.UserService;

//동일한 이름의 클래스일때 컨트롤러의 이름이 달라야 다른 클래스로 인식 하지만 공통된 컨트롤 부분이기에 같은 이름
@Controller("userApiController")
@RequestMapping("/user/api")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	//데이터를 파싱해 오기 위해 ResponseBody를 사용
	@ResponseBody
	@RequestMapping("/checkemail") //eamil 체크 페이지
	public Map<String, Object> checkEmail(//파라미터로 email값을 가져온다
			@RequestParam(value="email", required=true, defaultValue="")String email){
		//userService객체를 통해서 email값이 있는 값인지 확인하는 쿼리문 실행
		boolean exist = userService.existEmail(email);
		
		//map을 이용해서 json형식처럼 키값에 값을 매핑 
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", "success");
		map.put("data", exist);
		//map.put("email", email); 
		return map;//브라우저가 반환된 값을 읽으면 그냥 html의 string이지만 javascript로 읽으면 다르다.
	}
}

package com.jx372.mysite.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jx372.mysite.service.UserService;
import com.jx372.mysite.vo.UserVo;

@Controller
@RequestMapping("/user")
public class UserController {
	
	//root context로 부터 service클래스 DI설정
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/join", method=RequestMethod.GET)
	public String join(){	
		return "user/join";
	}
	
//	@RequestMapping(value="/join", method=RequestMethod.POST)
//	public String join(@ModelAttribute UserVo userVo){
//		System.out.println(userVo);
//		//join 로직은 service에서 처리
//		userService.join(userVo);
//		return "redirect:/user/joinsuccess";
//	}
	
	//MsgCovt test
	@ResponseBody
	@RequestMapping(value="/join", method=RequestMethod.POST)
	public String join2(@RequestBody String requestBody){
		return requestBody;
	}
	
	@RequestMapping(value="/joinsuccess", method=RequestMethod.GET)
	public String joinsucess(){
		return "user/joinsuccess";
	}
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String login(){
		return "user/login";
	}
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String login(
			HttpSession session, //기술의 침투
			Model model,
			@RequestParam(value="email", required=true, defaultValue="")String email,
			@RequestParam(value="password", required=true, defaultValue="")String password
			){
        UserVo userVo=userService.getUser(email,password);
        
        if(userVo==null){
        	model.addAttribute("result","fail");
        	return "user/login"; //forwarding
        }
        //인증
        session.setAttribute("authUser", userVo);
		return "redirect:/main";
	}

	//@RequestMapping(value="/login", method=RequestMethod.POST)
	//public String login(){
	//	return "user/login";
	//}

	@RequestMapping("/logout")
	public String logout(HttpSession session){
		session.removeAttribute("authUser");
		session.invalidate();
		return "redirect:/main";
	}
	
	@RequestMapping(value="/modify", method=RequestMethod.GET)
	public String modify(
			HttpSession session
			){
		//인증여부 체크(접근제한)
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if(authUser==null){
			return "redirect:/user/login";
		}
		authUser = userService.getUser(authUser.getNo());
		session.setAttribute("authUser", authUser);
		return "user/modify";
	}
	@RequestMapping(value="/modify", method=RequestMethod.POST)
	public String modify(
			HttpSession session,
			@ModelAttribute UserVo vo
			){
		//인증여부 체크(접근제한)
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if(authUser==null){
			return "redirect:/user/login";
		}
		vo.setNo(authUser.getNo());
		//System.out.println(vo);
		
		userService.update(vo); 
		session.setAttribute("result", "success");
		return "redirect:/user/logout";
	}
//	@ExceptionHandler(UserDaoException.class)
//	public String handleUserDaoException(){
//		//1.로깅
//		//2.사과페이지 안내
//		return "error/exception";
//	}

}

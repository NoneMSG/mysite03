package com.jx372.mysite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.jx372.mysite.service.AdminService;
import com.jx372.mysite.vo.AdminVo;
import com.jx372.security.Auth;

//@Auth("ADMIN")
//@Auth(role="ADMIN")
//@Auth(value={"USER","ADMIN","SYS"},test=1)
//@Auth({"USER","ADMIN","SYS"})

@Auth(value=Auth.Role.ADMIN)
@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private AdminService adminService;
	
	@RequestMapping(value="/modify",method=RequestMethod.POST)
	public String modify(@ModelAttribute AdminVo adminVo,
			@RequestParam(value="file") MultipartFile file){
		
		String profileUrl = adminService.restore(file);
		AdminVo admininfo =adminVo;
		
		admininfo.setProfilepath(profileUrl);
		//System.out.println("before"+admininfo);
		adminService.updateInfo(admininfo);
		//System.out.println("after"+admininfo);
		return "redirect:/admin/main";
	}
	
	@RequestMapping({"","/main"})
	public String main(Model model){
		AdminVo adminVo = adminService.getInfo();
		model.addAttribute("pageInfo",adminVo);
		return "admin/main";
	}
	
	@RequestMapping("/board")
	public String board(){
		
		return "admin/board";
	}
	
	@RequestMapping("/guestbook")
	public String guestbook(){
		
		return "admin/guestbook";
	}
	
	@RequestMapping("/user")
	public String user(){
		
		return "admin/user";
	}
}

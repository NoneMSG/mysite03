package com.jx372.mysite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/gallery")
public class GalleryController {

	@RequestMapping("")
	public String list(){
		
		return "gallery/index";
	}
	
	public String upload(){
		return "redirect:gallery";
	}
	
	public String delete(){
		return "redirect:gallery";
	}
}

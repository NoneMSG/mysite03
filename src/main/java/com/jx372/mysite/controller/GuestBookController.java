package com.jx372.mysite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jx372.mysite.service.GuestBookService;
import com.jx372.mysite.vo.GuestbookVo;
import com.jx372.security.Auth;

@Controller
@RequestMapping("/guestbook")
public class GuestBookController {

	@Autowired
	private GuestBookService gbService;
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Model model) {
		List<GuestbookVo> list = gbService.getList();
		model.addAttribute("list", list);
		return "guestbook/list";
	}

	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public String insert(@ModelAttribute GuestbookVo gbVo) {
		gbService.getInsert(gbVo);
		return "redirect:/guestbook/list";
	}

	@RequestMapping("/delete/{no}")
	public String delete(@PathVariable("no") Long no, Model model) {
		model.addAttribute("no", no);
		return "guestbook/delete";
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String delete(@ModelAttribute GuestbookVo gbVo) {
		gbService.delete(gbVo);
		return "redirect:/guestbook/list";
	}

}

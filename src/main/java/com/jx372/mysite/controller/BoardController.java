package com.jx372.mysite.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.jx372.mysite.service.BoardService;
import com.jx372.mysite.vo.BoardVo;
import com.jx372.mysite.vo.UserVo;


@Controller
@RequestMapping("/board")
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	@RequestMapping( "/list" )
	public String index(
		@RequestParam( value="p", required=true, defaultValue="1") Integer page,
		Model model ) {
		
		Map<String, Object> map = boardService.getMessageList( page );
		model.addAttribute( "map", map );

		System.out.println( map );
		
		return "/board/list2";
	}
	
//	@RequestMapping("/list")
//	public String list(Model model){
//		List<BoardVo> list = boardService.getList();
//		model.addAttribute("blist",list);
//		return "/board/list";
//	}
	
	@RequestMapping(value="/write", method=RequestMethod.GET)
	public String write(){
		return "/board/write";
	}
	
	@RequestMapping(value="/write", method=RequestMethod.POST)
	public String write(
			@ModelAttribute BoardVo boardvo,
			HttpSession session,
			@RequestParam(value="gno", required=true, defaultValue="")Integer gno,
			@RequestParam(value="ono", required=true, defaultValue="")Integer ono,
			@RequestParam(value="depth", required=true, defaultValue="")Integer dep
			){
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if(authUser==null){
			return "redirect:/board/list";
		}
		boardvo.setGroupNo(gno);
		boardvo.setOrderNo(ono);
		boardvo.setDepth(dep);
		boardvo.setUserNo(authUser.getNo());
		System.out.println(boardvo);
		boardService.getWrite(boardvo);
		return "redirect:/board/list";
	}
	@RequestMapping("/view/{no}")
	public String getContent(
			@PathVariable("no")Long no,
			Model model
			){
		BoardVo vo = (BoardVo)boardService.getContent(no);
		model.addAttribute("bvo",vo);
		boardService.increaseHit(no);
		return "/board/view";
	}
	
	@RequestMapping(value="/modify/{no}",method=RequestMethod.GET)
	public String getModify(@PathVariable("no")Long no, Model model){
		BoardVo vo = (BoardVo)boardService.getContent(no);
		model.addAttribute("bvo",vo);
		return "/board/modify";
	}
	
	@RequestMapping(value="/modify/{no}",method=RequestMethod.POST)
	public String getModify(
			@PathVariable("no")Long no,
			@ModelAttribute BoardVo boardvo,
			HttpSession session
			){
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		boardvo.setNo(no);
		boardvo.setUserNo(authUser.getNo());
		System.out.println(boardvo);
		boardService.getModify(boardvo);
		return "redirect:/board/list";
	}
	
	@RequestMapping("/reply/{no}")
	public String getReply(
			@PathVariable("no")Long no,
			@ModelAttribute BoardVo boardvo,
			Model model
			){
		boardvo.setNo(no);
		BoardVo replyvo = boardService.getContent(no);
		model.addAttribute("replyVo",replyvo);
		return "/board/reply";
	}
	
//	@RequestMapping(value="/reply/{no}",method=RequestMethod.POST)
//	public String writeReply(
//			@PathVariable("no")Long no,
//			@ModelAttribute BoardVo boardvo,
//			Model model
//			){
//		boardvo.setNo(no);
//		BoardVo replyvo = boardService.getContent(no);
//		model.addAttribute("replyVo",replyvo);
//		return "redirect:/board/reply";
//	}
	
	@RequestMapping("/delete/{bno}" )
	public String delete(
			@PathVariable("bno") Long no,
			HttpSession session
			){
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if(authUser==null){
			return "redirect:/board/list";
		}
		BoardVo boardvo= new BoardVo();
		boardvo.setNo(no);
		boardvo.setUserNo(authUser.getNo());
		boardService.getDelete(boardvo);
		return "redirect:/board/list";
	}
	
}

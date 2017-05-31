package com.jx372.mysite.controller;

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
import com.jx372.security.Auth;


@Controller
@RequestMapping("/board")
public class BoardController {
	
	//VO 객체없이 자동적으로 값을 setter getter를 통해서 설정하기 위한 어노테이션
	@Autowired
	private BoardService boardService;
	
	//board(게시판) 출력페이지
	@RequestMapping( "/list" )
	public String index(
		@RequestParam( value="p", required=true, defaultValue="1") Integer page,
		@RequestParam( value="kwd", required=true, defaultValue="")String kwd,
		Model model ) {
		//map을 이용해 문자열 객체변수에 있는 값을 전달 가능
		Map<String, Object> map = boardService.getMessageList( page, kwd );
		
		model.addAttribute( "map", map ); //페이지로 데이터를 넘김

		//System.out.println( map );
		
		return "/board/list2";
	}
	
//	@RequestMapping("/list")
//	public String list(Model model){
//		List<BoardVo> list = boardService.getList();
//		model.addAttribute("blist",list);
//		return "/board/list";
//	}
	
	//글쓰기 페이지로 이동 세션에 로그인 정보가 없다면 로그인하도록 redirect 
	@RequestMapping(value="/write", method=RequestMethod.GET)
	public String write(HttpSession session){
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if(authUser==null){
			return "redirect:/board/list";
		}
		return "/board/write";
	}
	
	//글쓰기 페이지에서 글쓰기 기능
	@Auth
	@RequestMapping(value="/write", method=RequestMethod.POST)
	public String write(
			@ModelAttribute BoardVo boardvo,
			HttpSession session,
			@RequestParam(value="gno", required=true, defaultValue="")Integer gno,
			@RequestParam(value="ono", required=true, defaultValue="")Integer ono,
			@RequestParam(value="depth", required=true, defaultValue="")Integer dep
			){
		//로그인정보체크
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if(authUser==null){
			return "redirect:/board/list";
		}
		//댓글쓰기와 기능을 같이 쓰기 때문에 필요한 데이터를 받는다.
		boardvo.setGroupNo(gno); //글의 그룹번호
		boardvo.setOrderNo(ono); //글의 순서번호 무엇이 댓글인지 알기위해
		boardvo.setDepth(dep); //글이 몇번째 댓글인지 알기 위함
		boardvo.setUserNo(authUser.getNo()); //유저의 정보
		//System.out.println(boardvo); 
		boardService.getWrite(boardvo); //글쓰기 서비스로 데이터를 넘긴다.
		return "redirect:/board/list"; //다되면 페이지이동
	}
	
	//게시된 글을 눌렀을때 내용을 보기위한 페이지 이동
	@RequestMapping("/view/{no}")
	public String getContent(
			@PathVariable("no")Long no,
			Model model
			){
		//해당게시글의 데이터를 불러오기 위해 서비스에서 데이터를 받아서
		BoardVo vo = (BoardVo)boardService.getContent(no);
		//페이지에 bvo문자로 데이터를 넘긴다.
		model.addAttribute("bvo",vo);
		//게시글을 볼때마다 조회수를 증가시킨다.
		boardService.increaseHit(no);
		//페이지 포워드
		return "/board/view";
	}
	
	//글을 수정하기 위한 포워드 컨트롤
	@Auth
	@RequestMapping(value="/modify/{no}",method=RequestMethod.GET)
	public String getModify(@PathVariable("no")Long no, Model model, HttpSession session){
		//수정권한이 있는지 확인
//		UserVo authUser = (UserVo) session.getAttribute("authUser");
//		if(authUser==null){
//			return "redirect:/user/login";
//		}
		//권한이 있다면 게시글의 내용을 DB로부터 받아와 보여준다.
		BoardVo vo = (BoardVo)boardService.getContent(no);
		//데이터를 보여주기위해 페이지로전달
		model.addAttribute("bvo",vo);
		return "/board/modify";
	}
	
	//수정페이지에서 내용을 바꾸기 위한 컨트롤
	@Auth
	@RequestMapping(value="/modify",method=RequestMethod.POST)
	public String getModify(
			@ModelAttribute BoardVo boardvo,
			HttpSession session
			){
		//권한확인
		UserVo authUser = (UserVo) session.getAttribute("authUser");
//		if(authUser==null){
//			return "redirect:/user/login";
//		}
		//글을 수정하는데 필요한 정보인 글번호 유저번호 저장
		//boardvo.setNo(no);
		boardvo.setUserNo(authUser.getNo());
		System.out.println(boardvo);
		//DB에 데이터를 업데이트 시키기 위한 함수호출
		boardService.getModify(boardvo);
		return "redirect:/board/list";
	}
	
	@Auth
	@RequestMapping("/reply/{no}")
	public String getReply(
			@PathVariable("no")Long no,
			@ModelAttribute BoardVo boardvo,
			Model model,
			HttpSession session
			){
		//UserVo authUser = (UserVo) session.getAttribute("authUser");
//		if(authUser==null){
//			return "redirect:/board/list";
//		}
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
	
	//게시글의 삭제를 위한 컨트롤
	@Auth
	@RequestMapping("/delete/{bno}" )
	public String delete(
			@PathVariable("bno") Long no,
			HttpSession session
			){
		//삭제권한 확인
		UserVo authUser = (UserVo) session.getAttribute("authUser");
//		if(authUser==null){
//			return "redirect:/board/list";
//		}
		//권한이 있다면 DB수정에 필요한 데이터를 VO에 저장
		BoardVo boardvo= new BoardVo();
		boardvo.setNo(no);
		boardvo.setUserNo(authUser.getNo());
		//삭제기능 호출
		boardService.getDelete(boardvo);
		
		//리다이렉트
		return "redirect:/board/list";
	}
	
}

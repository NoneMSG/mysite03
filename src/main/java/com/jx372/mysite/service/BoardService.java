package com.jx372.mysite.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jx372.mysite.repository.BoardDao;
import com.jx372.mysite.vo.BoardVo;

@Service
public class BoardService {
	private static final int LIST_SIZE = 5; //리스팅되는 게시물의 수
	private static final int PAGE_SIZE = 5; //페이지 리스트의 페이지 수
	
	@Autowired
	private BoardDao boardDao;
	
	//게시판 리스트 뷰페이지 기능 로직
	public Map<String, Object> getMessageList(int currentPage, String keyword){
		int totalCount = boardDao.getTotalCount(); 
		int pageCount = (int)Math.ceil( (double)totalCount / LIST_SIZE );
		int blockCount = (int)Math.ceil( (double)pageCount / PAGE_SIZE );
		int currentBlock = (int)Math.ceil( (double)currentPage / PAGE_SIZE );
		
		if( currentPage < 1 ) {
			currentPage = 1;
			currentBlock = 1;
		} else if( currentPage > pageCount ) {
			currentPage = pageCount;
			currentBlock = (int)Math.ceil( (double)currentPage / PAGE_SIZE );
		}
		int beginPage = currentBlock == 0 ? 1 : (currentBlock - 1)*PAGE_SIZE + 1;
		int prevPage = ( currentBlock > 1 ) ? ( currentBlock - 1 ) * PAGE_SIZE : 0;
		int nextPage = ( currentBlock < blockCount ) ? currentBlock * PAGE_SIZE + 1 : 0;
		int endPage = ( nextPage > 0 ) ? ( beginPage - 1 ) + LIST_SIZE : pageCount;
		
		List<BoardVo> list = boardDao.getList(keyword ,currentPage, LIST_SIZE );
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put( "list", list );
		map.put( "totalCount", totalCount );
		map.put( "listSize", LIST_SIZE );
		map.put( "currentPage", currentPage );
		map.put( "beginPage", beginPage );
		map.put( "endPage", endPage );
		map.put( "prevPage", prevPage );
		map.put( "nextPage", nextPage );
		map.put("keyword", keyword);
		return map;
	}
	
	//평범한 데이터 호출
//	public List<BoardVo> getList(){
//		List<BoardVo> list = boardDao.getList();
//		return list;
//	}
	
	//글쓰기 서비스
	public void getWrite(BoardVo boardvo){
		//System.out.println(boardvo);
		
		//컨트롤로부터 전달받은 데이터중 댓글쓰기인지 아닌지에 대한 검증 로직
		if( boardvo.getGroupNo() != null ) { 
		boardDao.increaseGroupOrder(boardvo.getGroupNo(), boardvo.getOrderNo());
			boardvo.setGroupNo(boardvo.getGroupNo());
			boardvo.setOrderNo(boardvo.getOrderNo()+1);
			boardvo.setDepth(boardvo.getDepth()+1);
		}else{ //댓글이 아닌 글쓰기인경우 그룹번호지정
			boardvo.setGroupNo(0); 
		}
		//DAO로부터 쿼리문 호출
		boardDao.insert(boardvo);
	}
	
	//게시글 삭제서비스
	public void getDelete(BoardVo boardvo) {
		boardDao.delete(boardvo);
	}

	//게시글의 내용보기 서비스
	public BoardVo getContent(Long no) {
		return boardDao.get(no);
	}

	//게시글 수정 서비스
	public void getModify(BoardVo boardvo) {
		boardDao.modify(boardvo);
	}
	
	//게시글 조회수 증가 서비스
	public void increaseHit(Long no) {
		boardDao.update(no);
	}
}

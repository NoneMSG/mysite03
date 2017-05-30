package com.jx372.mysite.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.jx372.mysite.repository.BoardDao;
import com.jx372.mysite.vo.BoardVo;

@Service
public class BoardService {
	private static final int LIST_SIZE = 5; //리스팅되는 게시물의 수
	private static final int PAGE_SIZE = 5; //페이지 리스트의 페이지 수
	
	
	@Autowired
	private BoardDao boardDao;
	
	public Map<String, Object> getMessageList(int currentPage){
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
		
		List<BoardVo> list = boardDao.getList(currentPage, LIST_SIZE );
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put( "list", list );
		map.put( "totalCount", totalCount );
		map.put( "listSize", LIST_SIZE );
		map.put( "currentPage", currentPage );
		map.put( "beginPage", beginPage );
		map.put( "endPage", endPage );
		map.put( "prevPage", prevPage );
		map.put( "nextPage", nextPage );
		
		return map;
	}
	
	public List<BoardVo> getList(){
		List<BoardVo> list = boardDao.getList();
		return list;
	}
	
	public void getWrite(BoardVo boardvo){
		System.out.println(boardvo);
		if( boardvo.getGroupNo() != null ) {
			
		boardDao.increaseGroupOrder(boardvo.getGroupNo(), boardvo.getOrderNo());
			
			boardvo.setGroupNo(boardvo.getGroupNo());
			boardvo.setOrderNo(boardvo.getOrderNo()+1);
			boardvo.setDepth(boardvo.getDepth()+1);
		}else{
			boardvo.setGroupNo(0);
		}
		boardDao.insert(boardvo);
	}

	public void getDelete(BoardVo boardvo) {
		boardDao.delete(boardvo);
	}

	public BoardVo getContent(Long no) {
		return boardDao.get(no);
	}

	public void getModify(BoardVo boardvo) {
		boardDao.modify(boardvo);
	}

	public void increaseHit(Long no) {
		boardDao.update(no);
	}
}

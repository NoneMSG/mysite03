package com.jx372.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.jx372.mysite.repository.BoardDao;
import com.jx372.mysite.vo.BoardVo;

@Service
public class BoardService {
	
	@Autowired
	private BoardDao boardDao;
	
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

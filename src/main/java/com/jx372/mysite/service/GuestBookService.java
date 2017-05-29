package com.jx372.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jx372.mysite.repository.GuestbookDao;
import com.jx372.mysite.vo.GuestbookVo;

@Service
public class GuestBookService {
	
	@Autowired
	private GuestbookDao gbDao;
	
	public void getInsert(GuestbookVo gbVo) {
		gbDao.insert(gbVo);
	}
	public List<GuestbookVo> getList() {
		return gbDao.getList();
	}
	public void delete(GuestbookVo gbVo){
		gbDao.delete(gbVo);
	}
	
	public boolean writeMessage(GuestbookVo vo){
		boolean result = gbDao.insert(vo);
		System.out.println(vo);
		return result;
	}

}

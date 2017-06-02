package com.jx372.mysite.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jx372.mysite.vo.AdminVo;

@Repository
public class AdminDao {
	
	@Autowired
	private SqlSession sqlSession;

	public AdminVo getSelectAll() {
	
		AdminVo adminVo = sqlSession.selectOne("admin.get");
		
		System.out.println("getList"+adminVo);
		return adminVo;
	}
	
	public boolean update(AdminVo adminVo){
		int count = sqlSession.update("admin.update",adminVo);
		System.out.println("update:" +adminVo);
		return count==1;
	}
}

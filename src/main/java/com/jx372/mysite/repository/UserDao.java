package com.jx372.mysite.repository;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jx372.mysite.vo.UserVo;

@Repository
public class UserDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	public UserVo get(String email) {
		
		return sqlSession.selectOne("user.getByEmail",email);
	}
	
	public UserVo get( Long no ){
		
		//map을 resultType으로 사용하는 예제
		//Map map = sqlSession.selectOne("user.getByNo2", no);
		//System.out.println(map);
		
		UserVo userVo = sqlSession.selectOne("user.getByNo",no);
		return userVo;
	}
	
	public UserVo get( String email, String password ) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("email", email); 
		map.put("password", password);
		UserVo vo = sqlSession.selectOne("user.getByInfo", map);
		return vo;
	}
	
	public boolean update( UserVo vo ) {
		int count = sqlSession.update("user.update",vo);
		return count==1;
	}
	
	public boolean insert( UserVo vo ) {
		int count = sqlSession.insert("user.insert",vo);
		return count==1;
	}

	
}

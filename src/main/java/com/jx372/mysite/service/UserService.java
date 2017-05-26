package com.jx372.mysite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jx372.mysite.exception.UserDaoException;
import com.jx372.mysite.repository.UserDao;
import com.jx372.mysite.vo.UserVo;


@Service
public class UserService {
	
	//DI 해주는작업 (UserDao에 의존성을 가지는 서비스)
	@Autowired
	private UserDao userDao;
	
	//Dao를 여러개 사용 가능 ! Autowired는 각각 해주어야 한다.
	
	public void join(UserVo userVo){
		//1. DB에 사용자정보 저장
		userDao.insert(userVo);
		
		//2. 인증 Mail 보내기
		//3. 
	}

	public UserVo getUser(String email, String password) {
		return	userDao.get(email,password);
	}
	public UserVo getUser(Long no){
		return userDao.get(no);
	}
	public void update(UserVo userVo){
		userDao.update(userVo);
	}
	
}

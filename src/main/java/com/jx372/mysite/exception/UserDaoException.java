package com.jx372.mysite.exception;

public class UserDaoException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public UserDaoException(){
		super("UserDao Exception Occurs");
	}
	
	public UserDaoException(String message){
		super(message);
	}
}

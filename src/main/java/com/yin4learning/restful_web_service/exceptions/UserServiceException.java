package com.yin4learning.restful_web_service.exceptions;

public class UserServiceException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public UserServiceException(String message)
	{
		super(message);
	}
}

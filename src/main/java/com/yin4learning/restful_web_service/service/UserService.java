package com.yin4learning.restful_web_service.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.yin4learning.restful_web_service.share.dto.UserDTO;

public interface UserService extends UserDetailsService{
	UserDTO createUserFunction(UserDTO userRsDTO);
	UserDTO getUser(String email);
	//UserDto getUserByUserId(String userId);
	//UserDto updateUser(String userId, UserDto user);
	//void deleteUser(String userId);
	//List<UserDto> getUsers(int page, int limit);
	//boolean verifyEmailToken(String token);
	//boolean requestPasswordReset(String email);
	//boolean resetPassword(String token, String password);
}

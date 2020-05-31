package com.yin4learning.restful_web_service.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.yin4learning.restful_web_service.share.dto.UserDTO;

public interface UserService extends UserDetailsService{
	UserDTO createUser(UserDTO userRsDTO);
	UserDTO getUser(String email);
	UserDTO getUserByUserId(String userid);
	UserDTO updateUser(String userId, UserDTO user);
	void deleteUser(String userId);
	List<UserDTO> getUsers(int page, int limit);
	boolean verifyEmailToken(String token);
	boolean requestPasswordReset(String email);
	boolean resetPassword(String token, String password);
	
}

package com.yin4learning.restful_web_service.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.yin4learning.restful_web_service.DTO.UserDTO;

public interface UserService extends UserDetailsService{
	UserDTO createUserFunction(UserDTO userRsDTO);
}

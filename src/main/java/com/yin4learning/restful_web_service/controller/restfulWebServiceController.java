package com.yin4learning.restful_web_service.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yin4learning.restful_web_service.DTO.UserDTO;
import com.yin4learning.restful_web_service.model.UserDetailsRequest;
import com.yin4learning.restful_web_service.model.UserDetailsResponse;
import com.yin4learning.restful_web_service.service.UserService;

@RestController
@RequestMapping("/RestfulController")
public class restfulWebServiceController {
	
	@Autowired
	UserService userService;

	@GetMapping("/Test")
	public String getTest() {
		return "test";
	}
	
	@PostMapping("/createUser")
	public UserDetailsResponse getStringTest(@RequestBody UserDetailsRequest userinfoRquest) {
		//return object
		UserDetailsResponse userinfoResponse = new UserDetailsResponse();
		//create DTO object
		UserDTO userDTO = new UserDTO();
		//copy request data to DTO
		BeanUtils.copyProperties(userinfoRquest, userDTO);
		//service call for business logic
		UserDTO createdUserDTO = userService.createUserFunction(userDTO);
		//copy DTO data to return object
		BeanUtils.copyProperties(createdUserDTO, userinfoResponse);
		//return value
		return userinfoResponse;
	}
}

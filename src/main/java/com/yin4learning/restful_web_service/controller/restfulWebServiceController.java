package com.yin4learning.restful_web_service.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yin4learning.restful_web_service.model.request.UserDetailsRequest;
import com.yin4learning.restful_web_service.model.response.UserDetailsResponse;
import com.yin4learning.restful_web_service.service.UserService;
import com.yin4learning.restful_web_service.share.dto.UserDTO;

@RestController
@RequestMapping("/RestfulController")
public class restfulWebServiceController {

	@Autowired
	UserService userService;
	
	@GetMapping("/Test")
	public String getUser() {
		return "test";
	}
	
	@PostMapping("/createUser")
	public UserDetailsResponse createUser(@RequestBody UserDetailsRequest userinfoRquest) {
		UserDetailsResponse returnResponseObject = new UserDetailsResponse();
		UserDTO receivedDtoObject = new UserDTO();
		BeanUtils.copyProperties(userinfoRquest, receivedDtoObject);
		//Copy user information from request to DTO
		UserDTO createdUserDTO = userService.createUserFunction(receivedDtoObject);
		// Run service for saving user into DB
		BeanUtils.copyProperties(createdUserDTO, returnResponseObject);
		return returnResponseObject;
	}
	
	@PutMapping("/updateUser")
	public String updateUser() {
		return "put test";
	}
	
	@DeleteMapping("/deleteUser")
	public String deleteUser() {
		return "delete test";
	}
}

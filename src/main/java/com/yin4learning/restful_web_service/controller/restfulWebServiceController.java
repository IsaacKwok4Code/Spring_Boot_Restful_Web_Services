package com.yin4learning.restful_web_service.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	
	//@Autowired
	//AddressService addressService;
	
	@GetMapping(path = "/getUser/{id}"
			,produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public UserDetailsResponse getUser(@PathVariable String id) {
		//create return object
		UserDetailsResponse returnResponseObject = new UserDetailsResponse();
		//create DTO object and receive userid / call user service
		UserDTO receivedDtoObject = userService.getUserByUserId(id);
		//create modelMapper
		ModelMapper modelMapper = new ModelMapper();
		// BeanUtils vs. ModelMapper for mapping
		returnResponseObject = modelMapper.map(receivedDtoObject, UserDetailsResponse.class);
		return returnResponseObject;
	}
	
	/**
	 * 
	 * @param UserDetailsRequest - include all information for registration 
	 * @return UserDetailsResponse - include some information and response the userid
	 */
	@PostMapping(path = "/createUser"
			,consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }
			,produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public UserDetailsResponse createUser(@RequestBody UserDetailsRequest userinfoRquest) {
		// create return object
		UserDetailsResponse returnResponseObject = new UserDetailsResponse();
		//create DTO object
		UserDTO receivedDtoObject = new UserDTO();
		//copy input to DTO
		BeanUtils.copyProperties(userinfoRquest, receivedDtoObject);
		//calling user service
		UserDTO createdUserDTO = userService.createUser(receivedDtoObject);
		//copy created data to return object
		BeanUtils.copyProperties(createdUserDTO, returnResponseObject);
		return returnResponseObject;
	}
	
	@PutMapping(path ="/updateUser/{id}"
			,consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }
			,produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public UserDetailsResponse updateUser(@PathVariable String id, @RequestBody UserDetailsRequest userDetails) {
		// create return object
		UserDetailsResponse returnResponseObject = new UserDetailsResponse();
		//create DTO object
		UserDTO receivedDtoObject = new UserDTO();
		receivedDtoObject = new ModelMapper().map(userDetails, UserDTO.class);
		UserDTO updateUser = userService.updateUser(id, receivedDtoObject);
		returnResponseObject = new ModelMapper().map(updateUser, UserDetailsResponse.class);
		return returnResponseObject;
	}
	
	@DeleteMapping("/deleteUser")
	public String deleteUser() {
		return "delete test";
	}
}

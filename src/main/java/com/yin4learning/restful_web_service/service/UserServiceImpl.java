package com.yin4learning.restful_web_service.service;

import java.util.ArrayList;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.yin4learning.restful_web_service.db.entity.UserEntity;
import com.yin4learning.restful_web_service.repository.UserRepository;
import com.yin4learning.restful_web_service.share.dto.UserDTO;
import com.yin4learning.restful_web_service.share.util.Utils;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	Utils utils;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	// find user by email - email and userID are unique. but for security and data issue, should have method for finding email.
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		//create user entity object and 
		UserEntity userObject = userRepository.findByEmail(email);
		// if system can't find the email in database
		if (userObject == null) throw new UsernameNotFoundException(email);
		
		return new User(userObject.getEmail(), userObject.getEncryptedPassword(), new ArrayList<>());
	}
	
	@Override
	public UserDTO createUserFunction(UserDTO userRsDTO) {
		//First checking existing record by email
		UserEntity existingUserDetails = userRepository.findByEmail(userRsDTO.getEmail());
		if(existingUserDetails !=null) {
			throw new RuntimeException("Record is existing");
		}
		UserEntity userEntityObject = new UserEntity();
		UserDTO returnDtoObject = new UserDTO();
		BeanUtils.copyProperties(userRsDTO, userEntityObject);
		// Copy DTO to entity for database
		String publicUserId = utils.generateUserId(30);
		userEntityObject.setUserId(publicUserId);
		//system generate id
		userEntityObject.setEncryptedPassword(bCryptPasswordEncoder.encode(userRsDTO.getPassword()));
		//setting BCryPassword encode
		UserEntity storedUserDetails = userRepository.save(userEntityObject);
		BeanUtils.copyProperties(storedUserDetails, returnDtoObject);
		return returnDtoObject;
	}

	@Override
	public UserDTO getUser(String email) {
		UserEntity userEntity = userRepository.findByEmail(email);
		if (userEntity == null)
			throw new UsernameNotFoundException(email);
		UserDTO returnValue = new UserDTO();
		BeanUtils.copyProperties(userEntity, returnValue);
		return returnValue;
	}

	@Override
	public UserDTO getUserByUserId(String id) {
		UserDTO returnValue = new UserDTO();
		UserEntity userEntity = userRepository.findByUserId(id);
		if(userEntity == null) {
			throw new UsernameNotFoundException("User with ID: " + id + " not found");
		}
		BeanUtils.copyProperties(userEntity, returnValue);
		return returnValue;
	}

}

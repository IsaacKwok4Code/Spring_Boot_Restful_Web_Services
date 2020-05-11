package com.yin4learning.restful_web_service.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
		userEntityObject.setEncryptedPassword("testPW");
		UserEntity storedUserDetails = userRepository.save(userEntityObject);
		BeanUtils.copyProperties(storedUserDetails, returnDtoObject);
		return returnDtoObject;
	}

}
